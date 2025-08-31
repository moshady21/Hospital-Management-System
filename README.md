# 🏥 Hospital Management System (Back-End)
**Banque Misr Graduation Project**

**A secure, modular, and testable RESTful API built with Spring Boot, designed to manage patient-doctor interactions, appointments, prescriptions, medicines, pharmacies, and administrative tasks in a hospital environment.**

**The system uses JWT authentication, role-based access control, and supports unit/integration testing, Docker deployment, and MySQL persistence.**

---
### 📌 Project Overview
The Hospital System is a robust back-end application designed to support a digital healthcare ecosystem. It enables seamless communication between patients, doctors, pharmacies, and administrators through a secure, JWT-protected REST API.

- This system supports:

  - User registration and authentication (Admin, Doctor, Patient, Pharmacy)
  - Appointment booking and scheduling
  - Doctor-patient follow-up and medical history tracking
  - Secure messaging (non-real-time)
  - Medicine inventory management (CRUD)
  - Prescription handling
  - Role-based access control (RBAC) via JWT
  - Admin account management
  - Built with Spring Boot, Spring Security, JPA/Hibernate, and JWT, this system emphasizes security, modularity, testability, and maintainability.

---

## 🚀 Features

### 👥 User Management
- Roles: Admin, Doctor, Patient, Pharmacy
- Secure authentication & authorization with JWT
- CRUD operations for user accounts (admin only)

---
    
### 📅Appointment Management
- Patients can book appointments with doctors
- Doctors can view, confirm, and follow-up on appointments

---
### 💬Messaging System
- Doctors and patients can send/receive messages (non real-time, message history saved)
  
---

### 💊Prescriptions & Medicines
- Doctors assign prescriptions to patients
- Patients purchase medicines from pharmacies
- Pharmacies manage medicine inventory with full CRUD operations

---

### 🛠️Admin Controls
- Manage all accounts
- Delete any user account for system integrity

---

### Security
- Spring Security + JWT
- Role-based access control

---
### 🧪Testing
- Unit tests (JUnit5 + Mockito)
- Integration tests with H2 / TestEntityManager

---
### 🐳Deployment
- Dockerfile (multi-stage build with Maven & OpenJDK 21-slim)
- Docker Compose setup with app + database
- .dockerignore

---
## 🛠️ Technologies & Tools Used

| Category       | Technology |
|----------------|----------|
| Framework      | Spring Boot (Java 17+) |
| Build Tool     | Maven |
| Database       | H2 (Dev), MySQL (Production) |
| Security       | Spring Security + JWT |
| Validation     | Jakarta Bean Validation (Hibernate Validator) |
| ORM            | Spring Data JPA / Hibernate |
| API Testing    | Postman ,Swagger UI  |
| Logging        | SLF4J + Logback  |
| Dev Tools      | Lombok, Spring Initializr  |
| Testing        | JUnit 5, Mockito, MockMvc,Spring Boot Test  |
| Containerization    | Docker, Docker Compose, .dockerignore |

---
## 📁 Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com.hospital.system/
│   │       ├── config/               # Security, JWT, Web configs
│   │       ├── controller/           # REST controllers
│   │       ├── dto/                  # Data Transfer Objects
│   │       ├── entity/               # JPA Entities
│   │       ├── exception/            # Custom exceptions & handler
│   │       ├── repository/           # JPA Repositories
│   │       ├── service/              # Business logic
│   │       ├── security/             # JWT filters, utils
│   │       ├── modelmapper/          # map from entity to Dto and from entity to response Dto
│   │       └── HospitalApplication.java
│   └── resources/
│       ├── application.properties    # Configs
│       ├── logback-spring.xml        # Logging config
└── test/
    ├── java/                         # Unit & integration tests
    └── resources/                    # Test configs
```
---

## 🚀 Project Setup

### 1. Prerequisites
- Java 17 or higher
- Maven 
- IDE (IntelliJ IDEA, Eclipse, or VS Code)
- MySQL

### 2. Clone the Project
```bash
git clone https://github.com/moshady21/Hospital-Management-System.git
cd Hospital-Management-System
```
### 2. Configure Database
Edit src/main/resources/application.properties (for MySQL):
```
DB_URL=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=password
```
### 4.🧪 Running the Application
Using Maven (Command Line)
```
mvn spring-boot:run
```

---

## API Endpoints

- POST `/messages` Create new message.
- GET` /messages/conversation/senderId/receiverId` Get full conversation between doctor & patient
- GET `/inbox/{receiverId}` Get inbox (all messages received by a user)
- GET `/outbox/{senderId}` Get outbox (all messages sent by a user)

---
## 📅 Appointment API Endpoints

### 1. Create an Appointment
**POST** `/appointments`  
Creates a new appointment between a doctor and a patient.

- **Request Body (JSON):**
```json
{
  "doctorId": 1,
  "patientId": 2,
  "appointmentTime": "2025-09-01T10:00:00",
  "durationMinutes": 30,
  "notes": "Follow-up visit"
}
```

- **Responses:**
  - `201 Created` → Appointment created successfully (returns AppointmentResponseDto)
  - `409 Conflict` → Doctor/Patient already booked at that time
  - `500 Internal Server Error` → Unexpected error

---

### 2. Cancel an Appointment
**DELETE** `/appointments/{id}`  
Cancels an existing appointment by ID.

- **Path Variable:**  
  - `id` → Appointment ID

- **Responses:**
  - `200 OK` → Appointment successfully cancelled
  - `404 Not Found` → Appointment not found

---

### 3. Get Appointments by Doctor
**GET** `/appointments/doctor/{doctorId}`  
Fetches all appointments assigned to a specific doctor.

- **Path Variable:**  
  - `doctorId` → Doctor's ID

- **Response:**  
  - `200 OK` → List of AppointmentResponseDto objects

---

### 4. Get Appointments by Patient
**GET** `/appointments/patient/{patientId}`  
Fetches all appointments booked by a specific patient.

- **Path Variable:**  
  - `patientId` → Patient's ID

- **Response:**  
  - `200 OK` → List of AppointmentResponseDto objects

---

#  Medicine API Endpoints

## Create Medicine

**`POST /api/medicine`**

* **Role:** `PHARMACY`
* **Body:**

```json
{
  "name": "Paracetamol",
  "price": 25.5,
  "stockQuantity": 100,
  "description": "Pain reliever and fever reducer"
}
```

* **Response:**

```json
{
  "name": "Paracetamol",
  "price": 25.5,
  "stockQuantity": 100,
  "description": "Pain reliever and fever reducer",
  "pharmacy_id": 3
}
```

---

## Get Medicine by ID

**`GET /api/medicine/{id}`**

* **Role:** `PHARMACY`
* **Path Param:** `id` → Medicine ID
* **Response:**

```json
{
  "name": "Paracetamol",
  "price": 25.5,
  "stockQuantity": 100,
  "description": "Pain reliever and fever reducer",
  "pharmacy_id": 3
}
```

---

## Get All Medicines

**`GET /api/medicine`**

* **Role:** `PHARMACY`
* **Response:**

```json
[
  {
    "name": "Paracetamol",
    "price": 25.5,
    "stockQuantity": 100,
    "description": "Pain reliever and fever reducer",
    "pharmacy_id": 3
  },
  {
    "name": "Ibuprofen",
    "price": 40,
    "stockQuantity": 50,
    "description": "Anti-inflammatory and pain reliever",
    "pharmacy_id": 3
  }
]
```

---

## Update Medicine

**`PUT /api/medicine/{id}`**

* **Role:** `PHARMACY`
* **Path Param:** `id` → Medicine ID
* **Body:**

```json
{
  "name": "Paracetamol 500mg",
  "price": 30,
  "stockQuantity": 200,
  "description": "Updated description"
}
```

* **Response:**

```json
{
  "name": "Paracetamol 500mg",
  "price": 30,
  "stockQuantity": 200,
  "description": "Updated description",
  "pharmacy_id": 3
}
```

---

## Delete Medicine

**`DELETE /api/medicine/{id}`**

* **Role:** `PHARMACY`
* **Path Param:** `id` → Medicine ID
* **Response:**

```http
204 No Content
```

---

## Get All Available Medicines

**`GET /api/medicine/available`**

* **Role:** `PHARMACY`
* **Response:**

```json
[
  {
    "name": "Paracetamol",
    "price": 25.5,
    "stockQuantity": 100,
    "description": "Pain reliever and fever reducer",
    "pharmacy_id": 3
  }
]
```

---

# Prescription API Endpoints

## Create Prescription

**`POST /api/prescriptions`**

* **Role:** `DOCTOR`
* **Body:**

```json
{
  "patientId": 12,
  "medicineDetails": [
    {
      "medicineId": 101,
      "dosage": 2,
      "instructions": "Take after meals"
    }
  ]
}
```

* **Response:**

```json
{
  "issuedAt": "2025-08-31T13:00:00",
  "doctorEmail": "doctor@example.com",
  "patientEmail": "patient@example.com",
  "medicineDetails": [
    {
      "medicineId": 101,
      "dosage": 2,
      "instructions": "Take after meals"
    }
  ]
}
```

---

## Get Prescription by ID

**`GET /api/prescriptions/{id}`**

* **Role:** `PATIENT`
* **Path Param:** `id` → Prescription ID
* **Response:**

```json
{
  "issuedAt": "2025-08-31T13:00:00",
  "doctorEmail": "doctor@example.com",
  "patientEmail": "patient@example.com",
  "medicineDetails": [
    {
      "medicineId": 101,
      "dosage": 2,
      "instructions": "Take after meals"
    }
  ]
}
```

---

## Get All Prescriptions

**`GET /api/prescriptions`**

* **Role:** `PHARMACY`, `ADMIN`
* **Response:**

```json
[
  {
    "issuedAt": "2025-08-31T13:00:00",
    "doctorEmail": "doctor@example.com",
    "patientEmail": "patient@example.com",
    "medicineDetails": [
      {
        "medicineId": 101,
        "dosage": 2,
        "instructions": "Take after meals"
      }
    ]
  }
]
```

---

## Get Patient Prescriptions

**`GET /api/prescriptions/patient`**

* **Role:** `PATIENT`
* **Response:**

```json
[
  {
    "issuedAt": "2025-08-31T13:00:00",
    "doctorEmail": "doctor@example.com",
    "patientEmail": "patient@example.com",
    "medicineDetails": [
      {
        "medicineId": 101,
        "dosage": 2,
        "instructions": "Take after meals"
      }
    ]
  }
]
```

---

## Update Prescription

**`PUT /api/prescriptions/{id}`**

* **Role:** `DOCTOR`
* **Path Param:** `id` → Prescription ID
* **Body:**

```json
{
  "patientId": 12,
  "medicineDetails": [
    {
      "medicineId": 205,
      "dosage": 1,
      "instructions": "Take before sleep"
    }
  ]
}
```

* **Response:**

```json
{
  "issuedAt": "2025-08-31T14:00:00",
  "doctorEmail": "doctor@example.com",
  "patientEmail": "patient@example.com",
  "medicineDetails": [
    {
      "medicineId": 205,
      "dosage": 1,
      "instructions": "Take before sleep"
    }
  ]
}
```

---

## 🧹 Global Exception Handling
- Handled via @ControllerAdvice:
    - ResourceNotFoundException → 404 Not Found
    - AccessDeniedException → 403 Forbidden
    - MethodArgumentNotValidException → 400 Bad Request
    - DataIntegrityViolationException → 409 Conflict
    - Custom business exceptions

---

## 🐳 Docker & Deployment

- Dockerfile Highlights
    - Multi-stage build: Maven build → JRE slim runtime
- Docker Compose
    - Services:
          - app → Hospital backend
          - db → MySQL
---

## Logging

- Configured with SLF4J + Logback
- Log settings can be adjusted in application.properties.

---

## ERD 

https://drive.google.com/file/d/1HnhY9cLwwAf5tS2y4mTtjgNUzPjl4lOa/view?usp=sharing

---

## Documentation on postman

https://documenter.getpostman.com/view/38330442/2sB3Hhsh4M
