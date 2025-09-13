# Efficient Library Management System

[![Java Version](https://img.shields.io/badge/Java-11%2B-orange.svg)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
[![Build Status](https://img.shields.io/badge/build-pending-yellow.svg)](https://github.com/M-AdanAli/Smart-Library-Management-System)

***

## Project Description

A modular and scalable console-based Java application for managing library operations, designed to demonstrate solid Object-Oriented Programming principles while providing core library features like book and user management, borrowing and returning functionality, and fine calculation.

***

## Key Features

- **User Management:** Registration and management of different user roles including students and librarians with secure authentication.
- **Book Catalog:** Addition, removal, update, and search of books with detailed metadata.
- **Borrowing System:** Users can borrow and return books with automatic tracking of due dates, status, and overdue fine calculation.
- **Fine Management:** Automatic fine calculation based on overdue duration with pending fine tracking for borrowers.
- **Robust Input Validation:** Strong validation on user inputs including emails, passwords, and ISBNs.
- **Extensible Design:** Adheres to core OOP concepts using abstraction, inheritance, polymorphism, encapsulation, interfaces, and enums.
- **Separation of Concerns:** Well-organized package structure separating model, service, repository (planned), exceptions, utilities, and application layers.
- **Immutable Collections:** Uses unmodifiable lists to ensure safety of internal state.
- **Prepared for Advanced Features:** Planned integration of generics, streams, custom exceptions, database persistence, multi-threading, logging, and testing.

***

## Technologies Used

- **Java 11+**
- **Maven** (Recommended for dependency management and build)
- **JUnit** (For unit testing - planned)
- **SQLite** (Planned for persistence layer integration)
- **SLF4J / Log4j** (Planned logging framework)
- **Git** (Version control)

***

## Getting Started

### Prerequisites

- JDK 11 or higher installed and configured on your machine.
- Maven installed (optional but recommended).

### Installation

1. **Clone the repository:**

```bash
git clone https://github.com/M-AdanAli/Smart-Library-Management-System.git
cd Smart-Library-Management-System
```

2. **Build the project using Maven (if Maven is used):**

```bash
mvn clean install
```

3. **Import in your favorite IDE** such as IntelliJ IDEA or Eclipse as a Maven project or plain Java project.

***

## Usage Instructions

- Run the **`LibraryApp`** main class located in the `com.adanali.library.app` package to start the console-based application.
- The application will prompt you with a menu for user login, book management, borrowing operations, and fine payments.
- Use valid credentials to login or register to create new users.
- Search books by title, author, or genre using flexible search options.
- Borrow and return books following due dates to avoid fines.
- Keep track of pending fines and pay them as per prompts.
- The system validates all inputs strictly to ensure data integrity.

> **Note:** Currently, the system uses in-memory data structures and simple console I/O. Planned enhancements will enable database persistence and GUI/web interfaces.

***

## Project Structure

```
com.adanali.library
│
├── app            # Main app entry point and UI
├── model          # Domain entities like Book, User, BorrowingRecord
├── service        # Business logic layer (UserService, BookService, BorrowingService)
├── repository     # DAO and database interaction (planned)
├── exceptions     # Custom exception classes
├── util           # Utilities and validators
```

This clean modular structure facilitates maintainability and scalability.

***

## Contact / Support

For questions, feature requests, or bug reports, please use the [GitHub Issues](https://github.com/M-AdanAli/Smart-Library-Management-System/issues) section of this repository.

Alternatively, you can reach out via email: **imadanirfan@gmail.com**

***

## Screenshots / Demo

Currently, this project is a console-based application. Screenshots or demo videos are not yet available. Future GUI or web interface versions are planned.

***

## Future Enhancements

- **Persistence Layer:** Transition from in-memory collections to database-backed storage using SQLite and JDBC, through repository pattern.
- **Logging:** Integrate SLF4J/Log4j for standardized logging, replacing System.out/err prints.
- **Security Improvements:** Password hashing, salting, and improved authentication mechanisms.
- **Concurrency:** Thread-safe operations to support multi-user interactions.
- **Unit and Integration Testing:** JUnit test coverage to improve reliability.
- **Graphical/User Interface:** Swing/JavaFX or Spring Boot front-end to improve UX.
- **Annotations & Validation:** Leverage Java annotations for declarative validation and auditing.

***

## Acknowledgments

Inspired by best practices in Java development, focusing on clean code, OOP principles, and industry standards.

***

Thank you for exploring the Smart Library Management System! Contributions and feedback are highly welcome.

***

[Visit the GitHub Repository »](https://github.com/M-AdanAli/Smart-Library-Management-System)
