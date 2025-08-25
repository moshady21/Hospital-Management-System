# Hospital-System
Banque Misr Graduation Project

A secure, modular, and testable RESTful API built with Spring Boot, designed to manage patient-doctor interactions, appointments, prescriptions, medicines, pharmacies, and administrative tasks in a hospital environment.

The system uses JWT authentication, role-based access control, and supports unit/integration testing, Docker deployment, and MySQL persistence.

## Features

- User Management
    - Roles: Admin, Doctor, Patient, Pharmacy
    - Secure authentication & authorization with JWT
    - CRUD operations for user accounts (admin only)
- Appointments
    - Patients can book appointments with doctors
    - Doctors can view, confirm, and follow-up on appointments
- Messaging
    - Doctor ↔ Patient chat (non real-time, message history saved)
- Prescriptions & Medicines
    - Doctors assign prescriptions to patients
    - Patients purchase medicines from pharmacies
    - Pharmacies manage medicine inventory with full CRUD operations
- Admin Controls
    - Manage all accounts
    - Delete any user account for system integrity
- Security
    - Spring Security + JWT
    - Role-based access control
- Testing
    - Unit tests (JUnit5 + Mockito)
    - Integration tests with H2 / TestEntityManager
- Deployment
    - Dockerfile (multi-stage build with Maven & OpenJDK 21-slim)
    - Docker Compose setup with app + database
    - .dockerignore

## Tech Stack

- Backend: Spring Boot (Web, Data JPA, Validation, Security)
- Database: MySQL (H2 for tests)
- Security: Spring Security, JWT
- Build Tool: Maven
- Testing: JUnit 5, Mockito, Spring Boot Test
- Containerization: Docker, Docker Compose, .dockerignore
- Logging: SLF4J + Logback
- Lombok

## API Endpoints

`you will add yours here`


## Docker & Deployment

- Dockerfile Highlights
    - Multi-stage build: Maven build → JRE slim runtime
- Docker Compose
    - Services:
          - app → Hospital backend
          - db → MySQL

## Logging

- Configured with SLF4J + Logback
- Log settings can be adjusted in application.properties.

## ERD 

https://drive.google.com/file/d/1HnhY9cLwwAf5tS2y4mTtjgNUzPjl4lOa/view?usp=sharing
