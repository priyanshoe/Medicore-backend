# 🏥 MediCore – Healthcare Management System (Backend)

MediCore Backend is a RESTful API application developed using **Java and Spring Boot** to power a healthcare management system. It provides secure APIs for managing users, doctors, patients, appointments, and prescriptions.

The application implements JWT-based authentication, role-based authorization, and relational database management using Spring Data JPA and MySQL.

## ✨ Features

### 🔐 Authentication & Authorization
- User registration and login.
- JWT-based authentication.
- Spring Security integration.
- Role-based access control.
- Secure REST API endpoints.
- Password encryption and validation.

### 👨‍💼 User Management
- Manage application users.
- Support for ADMIN, DOCTOR, and PATIENT roles.
- Separate doctor and patient profiles.
- User and profile management.
- Role-specific access permissions.

### 👨‍⚕️ Doctor Management
- Register doctor profiles.
- Manage doctor information.
- Store specialization, qualification, and experience.
- Manage consultation fees and availability.
- Retrieve doctor details.

### 🧑‍🤝‍🧑 Patient Management
- Register patient profiles.
- Manage patient information.
- Retrieve patient details.
- Maintain relationships between users and patient profiles.

### 📅 Appointment Management
- Book appointments between doctors and patients.
- Manage appointment dates and time slots.
- Retrieve appointments based on user roles.
- Update appointment statuses.
- Prevent duplicate appointments using database unique constraints.

### 💊 Prescription Management
- Create and manage prescriptions.
- Associate prescriptions with appointments.
- Maintain doctor-patient relationships.
- Store prescribed medicine details.

### ⚠️ Exception Handling
- Custom application exceptions.
- Centralized exception handling.
- Consistent API response structure.
- Meaningful error messages and HTTP status codes.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming |
| Spring Boot | Backend framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA | Database operations |
| Hibernate | ORM |
| JWT | Token-based authentication |
| MySQL | Relational database |
| Maven | Dependency management |
| Postman | API testing |
| Git & GitHub | Version control |


## 🔗 Related Projects & Live Demo

This backend is part of a full-stack Medicare application built using React.js and Spring Boot.

- **Frontend Repository:** <a href="https://github.com/priyanshoe/MediCore" target="_blank" rel="noopener noreferrer">MediCare</a>
- **Live Application:** <a href="https://medi-core-vert.vercel.app/" target="_blank" rel="noopener noreferrer">MediCare – Live Demo</a>


## 🏗️ Project Architecture

The backend follows a layered architecture to maintain clean code organization and separation of responsibilities.

```text
src/main/java/com/medicore
│
├── config
│   └── Application configurations
│
├── controller
│   └── REST API controllers
│
├── service
│   └── Business logic
│
├── repository
│   └── Spring Data JPA repositories
│
├── entity
│   └── JPA entities
│
├── dto
│   ├── request
│   └── response
│
├── security
│   ├── JWT authentication
│   ├── JWT filter
│   └── Security configuration
│
├── exception
│   └── Custom exceptions and handlers
│
└── MedicoreApplication.java
```

*Note: Adjust the folder names according to your actual project structure.*

## 🗄️ Database Design

MediCore uses MySQL as its relational database and Hibernate/JPA for object-relational mapping.

### Main Entities

| Entity | Description |
|---|---|
| User | Stores authentication details and user roles |
| DoctorProfile | Stores doctor-specific information |
| PatientProfile | Stores patient-specific information |
| Appointment | Stores appointment details |
| Prescription | Stores prescription information |

### Entity Relationships

- User and DoctorProfile – One-to-One.
- User and PatientProfile – One-to-One.
- DoctorProfile and Appointment – One-to-Many.
- PatientProfile and Appointment – One-to-Many.
- Appointment and Prescription – Based on the appointment-prescription association.

JPA annotations such as `@OneToOne`, `@OneToMany`, and `@ManyToOne` are used to establish entity relationships.

## 🔒 Security Implementation

The application uses Spring Security with JWT authentication to secure backend APIs.

- Stateless authentication.
- JWT token generation and validation.
- Password hashing.
- Role-based authorization.
- Protected API endpoints.
- Authentication using Spring Security's SecurityContext.
- Separate access permissions for ADMIN, DOCTOR, and PATIENT.

## 📡 API Modules

The backend provides REST APIs for the following modules:

| Module | Operations |
|---|---|
| Authentication | Register, Login |
| Users | User management |
| Doctors | Create, Read, Update, Manage |
| Patients | Create, Read, Update, Manage |
| Appointments | Book, Retrieve, Update Status |
| Prescriptions | Create, Retrieve, Manage |

*Refer to the API controllers or Postman collection for exact endpoint mappings and request formats.*

## 🚀 Getting Started

Follow these steps to run the backend locally.

### Prerequisites

- Java 17 or later (as configured in the project).
- Maven.
- MySQL Server.
- Git.
- Postman (optional, for API testing).

### 1. Clone the Repository

```bash
git clone https://github.com/priyanshoe/Medicore-backend
```

Navigate to the project directory:

```bash
cd Medicore-backend
```

### 2. Create MySQL Database

Open MySQL and execute:

```sql
CREATE DATABASE medicore;
```

### 3. Configure Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medicore
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

jwt.secret=JWT_SECRET

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

Configure the JWT secret and any additional environment-specific properties required by the application.

**Security Note:** Never commit database credentials or JWT secrets to a public repository.

### 4. Install Dependencies

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The backend will start at:

```text
http://localhost:8080
```

If your application uses a context path of `/api`, the API base URL will be:

```text
http://localhost:8080/api
```

## 🧪 API Testing

You can test the REST APIs using Postman.

1. Register a user.
2. Login to obtain a JWT token.
3. Include the token in the Authorization header.
4. Use the appropriate endpoints based on the user's role.

Example Authorization header:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

## 🔗 Related Projects & Live Demo

- **Frontend Repository:** <a href="YOUR_MEDICORE_FRONTEND_REPOSITORY_URL" target="_blank" rel="noopener noreferrer">MediCore Frontend</a>
- **Live Application:** <a href="https://medi-core-vert.vercel.app/" target="_blank" rel="noopener noreferrer">MediCore – Live Demo</a>

## 📚 Key Learning Outcomes

Through developing this backend, I gained practical experience in:

- Building RESTful APIs using Spring Boot.
- Implementing JWT authentication and Spring Security.
- Designing role-based authorization.
- Developing layered backend architecture.
- Managing relational database relationships using Hibernate/JPA.
- Implementing DTO-based request and response handling.
- Creating custom exceptions and centralized exception handling.
- Managing transactional operations.
- Implementing business logic for appointments and prescriptions.
- Integrating backend APIs with a React frontend.

## 👨‍💻 Developer

**Priyanshu Rahate**

Full Stack Java Developer

- **GitHub:** [@priyanshoe](https://github.com/priyanshoe)
- **LinkedIn:** [im-priyanshu-rahate](https://www.linkedin.com/in/im-priyanshu-rahate/)
- **Portfolio:** [Priyanshu Rahate](https://priyanshoe.vercel.app/)

---

⭐ If you find this project useful, consider giving it a star!

**Built with ❤⌚ using Java, Spring Boot, Spring Security, Hibernate, and MySQL.**