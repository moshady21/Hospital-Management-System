# 🏥 Hospital System (Back-End)
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
│       └── data.sql                  # Optional seed data
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
git clone https://github.com/moshady21/hospital-system-backend.git
cd hospital-system-backend
```
### 2. Configure Database
Edit src/main/resources/application.properties (for MySQL):
```
DB_URL=jdbc:mysql://localhost:3306/hospital_db
spring.datasource.username=root
spring.datasource.password=password
JWT_SECRET=yourStrongSecretKeyHere
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

