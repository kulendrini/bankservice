# Bank Service

A Spring Boot-based REST API for managing core banking operations such as user registration, authentication, account lookup, loan lookup, transaction processing, and transaction history retrieval.

## Overview

Bank Service is a backend banking application built with Spring Boot. It exposes secured REST endpoints for authenticated users and uses JWT-based authentication for stateless API access.

The application supports:

- User registration
- User authentication with JWT
- Account details retrieval
- Account transaction history retrieval
- Money transfer / transaction processing
- Transaction details lookup
- Loan details retrieval
- Health check endpoint

## Tech Stack

- Java 21
- Spring Boot 3.1.1
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Validation
- JWT Authentication
- MySQL
- Maven
- JUnit / Mockito for testing

## Project Structure

```text
bankservice
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/app/bankservice
│   │   │       ├── controller
│   │   │       ├── entity
│   │   │       ├── exception
│   │   │       ├── model
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       ├── service
│   │   │       └── BankserviceApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```



## Main Modules

### Controller Layer

Handles incoming HTTP requests and exposes REST API endpoints.

- `AuthController` - User registration, authentication, and user lookup
- `AccountController` - Account details and transaction history
- `TransactionController` - Transaction processing and transaction lookup
- `LoanController` - Loan details retrieval
- `HealthCheckController` - Service health check

### Service Layer

Contains business logic for banking operations.

- `JwtUserDetailsService`
- `AccountService`
- `TransactionService`
- `LoanService`

### Repository Layer

Provides database access using Spring Data JPA repositories.

### Security Layer

Implements JWT-based stateless authentication using Spring Security.

Key components:

- `SecurityConfig`
- `JwtRequestFilter`
- `JwtTokenUtil`
- `JwtAuthenticationEntryPoint`

### Entity Layer

Contains JPA entities representing the banking domain, including users, accounts, loans, cards, transactions, roles, and reference types.

## Prerequisites

Before running the application, ensure the following are installed:

- Java 21
- Maven 3.8+
- MySQL 8+
- Git

## Database Setup


**Create a MySQL database:**

sql CREATE DATABASE bank_service;


**Update the database connection properties in:**

text src/main/resources/application.properties


**Default configuration:**

properties spring.datasource.url=jdbc:mysql://localhost:3306/bank_service

spring.datasource.username=root spring.datasource.password=mysql spring.jpa.hibernate.ddl-auto=update


> Note: For production environments, avoid storing database credentials and JWT secrets directly in source-controlled property files. Use environment variables or a secret manager.

## Configuration

The application runs on port `8080` by default.

properties server.port=8080


JWT authentication is configured using the `jwt.secret` property.

For safer environment-based configuration, you can externalize sensitive values:

properties spring.datasource.url={DB_URL} spring.datasource.username={DB_USERNAME} spring.datasource.password={DB_PASSWORD} jwt.secret={JWT_SECRET}



## Build the Project

**Using Maven wrapper:**

bash ./mvnw clean install

**On Windows:**

bash mvnw.cmd clean install

**Or using installed Maven:**

bash mvn clean install


## Run the Application

**Using Maven wrapper:**

bash ./mvnw spring-boot:run

**On Windows:**

bash mvnw.cmd spring-boot:run

**Or run the generated JAR:**

bash java -jar target/bankservice-0.0.1-SNAPSHOT.jar


**The application will start at:**

[http://localhost:8080](http://localhost:8080)


## API Base URL
[http://localhost:8080/v1/dbservice/app](http://localhost:8080/v1/dbservice/app)


## Authentication

The application uses JWT Bearer token authentication.

Public endpoints:

- `POST /authenticate`
- `POST /register`
- `GET /healthCheck`

All other endpoints require a valid JWT token.

Include the token in the `Authorization` header:
**http Authorization: Bearer <jwt-token>**


## API Endpoints

### Health Check

Checks whether the service is running.

http GET /v1/dbservice/app/healthCheck


**Example response:**

json { "status": "Running bank-service....!", "timestamp": "2026-07-28T10:15:30.000+00:00" }



## User Registration

**Registers a new user.**

http POST /v1/dbservice/app/register


**Example request:**

json { "username": "john_doe", "password": "password123", "firstName": "John", "lastName": "Doe", "contactNo": "+94771234567", "email": "john.doe@example.com", "address": "123 Main Street", "roles": ["USER"] }


## User Authentication

Authenticates a user and returns a JWT token.

http POST /v1/dbservice/app/authenticate

**Example request:**

json { "username": "john_doe", "password": "password123" }

**Example response:**

json { "token": "eyJhbGciOiJIUzI1NiJ9...", "expiresIn": 3600 }


## Get User by Username

Retrieves user details by username.

http GET /v1/dbservice/app/user/{username}


Requires authentication.

**Example:**

http GET /v1/dbservice/app/user/john_doe


## Get Account Details

Retrieves account details for a given username.


http GET /v1/dbservice/app/account/{username}

Requires authentication.

**Example:**


http GET /v1/dbservice/app/account/john_doe

## Get Account Transactions

Retrieves transaction history for a specific account number.


http GET /v1/dbservice/app/account/{accountNumber}/transactions?from=0&to=10

Requires authentication.

**Example:**


http GET /v1/dbservice/app/account/1002003001/transactions?from=0&to=10

Query parameters:

| Parameter | Description | Default |
|---|---|---|
| `from` | Starting index | `0` |
| `to` | Ending index | `10` |

## Make Transaction

Processes a transaction between accounts.


http POST /v1/dbservice/app/transaction

Requires authentication.

**Example request:**


json { "originAccountNo": "1002003001", "destinationAccountNo": "1002003002", "bank": "Bank Service", "amount": 2500.00, "comment": "Monthly transfer", "transactionDate": "2026-07-28T10:15:30.000+00:00", "transactionTypeId": 1 } 

## Get Transaction by ID

Retrieves details of a specific transaction.


http GET /v1/dbservice/app/transaction/{transactionId}

Requires authentication.

**Example:**


http GET /v1/dbservice/app/transaction/1

## Get Loan Details

Retrieves loan details for a given username.


http GET /v1/dbservice/app/loan/{username}

Requires authentication.

**Example:**


http GET /v1/dbservice/app/loan/john_doe

## Validation

The application uses Jakarta Bean Validation for request validation.

Examples of validation rules:

- Username cannot be empty
- Password must be at least 6 characters
- Email must be valid
- Contact number must match a valid phone number pattern
- Transaction amount must be positive
- Required transaction fields cannot be null

Validation errors are handled by the global exception handler.

## Security

The application uses Spring Security with stateless JWT authentication.

Security behavior:

- CSRF is disabled for REST API usage
- Sessions are stateless
- JWT tokens are validated for protected endpoints
- Public access is allowed only for authentication, registration, and health check endpoints

Public endpoints:


POST /v1/dbservice/app/authenticate POST /v1/dbservice/app/register GET /v1/dbservice/app/healthCheck

Protected endpoints require:


http Authorization: Bearer <jwt-token>

## Testing

**Run tests using:**


bash ./mvnw test

**On Windows:**


bash mvnw.cmd test

**Or using Maven:**


bash mvn test

## Common Commands

### Clean and build


bash ./mvnw clean install

### Run application


bash ./mvnw spring-boot:run

### Run tests


bash ./mvnw test

### Package application


bash ./mvnw clean package

## Environment Variables

Recommended environment variables for deployment:


bash export DB_URL=jdbc:mysql://localhost:3306/bank_service export DB_USERNAME=root export DB_PASSWORD=mysql export JWT_SECRET=your-secure-jwt-secret

Then configure `application.properties` to use them:


properties spring.datasource.url={DB_URL} spring.datasource.username={DB_USERNAME} 

spring.datasource.password={DB_PASSWORD} jwt.secret={JWT_SECRET}

## Deployment Notes

For production deployment:

- Use environment variables for sensitive configuration
- Use a strong JWT secret
- Avoid root database credentials
- Configure HTTPS at gateway or load balancer level
- Use database migration tooling such as Flyway or Liquibase
- Set a production-specific Spring profile
- Add centralized logging and monitoring
- Review CORS configuration before exposing the API publicly

## Suggested Future Improvements

- Add OpenAPI / Swagger documentation
- Add database migration scripts
- Add Docker and Docker Compose support
- Add integration tests for secured endpoints
- Add role-based authorization for admin operations
- Improve observability with Spring Boot Actuator
- Add CI/CD pipeline configuration
- Externalize all secrets and environment-specific properties

