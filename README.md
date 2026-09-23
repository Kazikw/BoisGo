# Orlik 2.0 - Sports Facility Reservation System ⚽

A robust, RESTful backend application designed for managing sports facilities and user reservations. This project serves as a complete overhaul of a legacy system, built from scratch with a strong emphasis on **Clean Architecture**, **Domain-Driven Design (DDD) principles**, and enterprise-grade design patterns.

## 🚀 Tech Stack

*   **Core:** Java 21, Spring Boot 4.0
*   **Database:** PostgreSQL (Dockerized)
*   **Data Access:** Spring Data JPA, Hibernate
*   **Mapping:** MapStruct
*   **Validation:** Spring Boot Validation
*   **Security:** Spring Security (Stateless / JWT) - *In Progress*
*   **Infrastructure:** Docker, Docker Compose

## 🧠 Architectural Highlights

To ensure high maintainability and security, the project implements several key software engineering practices:

*   **DTO Pattern & MapStruct:** Complete decoupling of database entities from the API presentation layer to prevent data leaks and infinite recursion.
*   **Rich Domain Model:** Business logic (like time validation and ownership checks) is encapsulated directly within Entities rather than just Services.
*   **Global Exception Handling:** A centralized `@RestControllerAdvice` intercepts domain-specific exceptions (e.g., `ReservationConflict`, `ResourceNotFoundException`) and returns standardized, secure JSON error responses.
*   **Advanced JPQL & Pagination:** Heavily optimized database queries for complex business rules (e.g., joining public matches) utilizing Spring Data `Pageable` to ensure scalability.

## ⚙️ Key Features

*   **Facility Management:** Adding and querying available sports facilities.
*   **Reservation System:** Booking facilities for strict time slots with overlapping prevention.
*   **Public Matches:** Joining existing public reservations up to a specific capacity limit.
*   **Secure Unbooking:** Graceful cascading deletion of reservations and specific participant leaves.

## 🛠️ How to run locally

1. Ensure you have Docker and Java 21 installed.
2. Clone the repository.
3. The project includes Spring Boot Docker Compose Support. Simply run the application from your IDE, and the PostgreSQL database container will start automatically.

---
*Developed as a showcase of modern backend engineering practices.*
