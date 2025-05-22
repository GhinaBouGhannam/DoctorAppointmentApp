# Doctor Appointment Application
<p align="center">
  <img src="https://github.com/user-attachments/assets/6e8982ab-1040-45f7-98b8-a044a535fe79" alt="logo_below" />
</p>

## Overview

This is a Doctor Appointment Management System built using Java technologies. It allows users (patients), doctors, and admins to manage appointments and doctor profiles effectively.

- **Admin** can add, update, and delete doctors.
- **Doctors** can view, accept, or reject appointments, update their personal information.
- **Users (Patients)** can search for doctors by specialty or location, book appointments, and track their appointment status.

The project follows the **MVC architecture** with a **Spring Boot** backend using **Hibernate** for ORM and a **JavaFX** desktop frontend for the user interface.

---

## Technologies Used

- **Backend:**
  - Java 17+
  - Spring Boot
  - Hibernate ORM
  - MySQL Database
  - RESTful APIs
  - Maven (build tool)

- **Frontend:**
  - JavaFX
  - Jackson and org.json for JSON parsing
  - Java 17+

---

## Backend (Spring Boot + Hibernate)

The backend handles business logic and database interactions.

- Provides REST APIs for doctors, users, appointments, and admin operations.
- Uses Hibernate ORM for mapping Java entities (`Doctor`, `User`, `Appointment`) to MySQL tables.
- Validates input and enforces business rules (e.g., unique doctor email).
- Example endpoint: `GET /api/healthub/doctor/top` returns top doctors.

- 4. Backend server will start on `http://localhost:8080`.

---

## Frontend (JavaFX Application)

The frontend is a desktop app built with JavaFX that interacts with the backend APIs.

- Displays lists of doctors, appointments, and enables booking.
- Fetches data using HTTP calls and processes JSON responses.
- User-friendly UI with buttons, list views, and scene navigation.

### Running Frontend

1. Open the frontend project in IntelliJ IDEA or your preferred IDE.
2. Run the `HelloApplication` class to launch the JavaFX application.
3. Ensure the backend server is running at `http://localhost:8080`.
4. The app will load data and allow interaction with appointments and doctors.

---


### Running Backend

1. Ensure MySQL is running and create a database named `appointment_app`.
2. Update database credentials in your configuration (`hibernateUtil` or `application.properties`).
3. Run the Spring Boot application from your IDE or via Maven:

---

## Features

- **Admin**
  - Add, update, delete doctors
  - Manage system data

- **Doctor**
  - View incoming appointments
  - Accept or reject appointments
  - Update personal profile

- **User (Patient)**
  - Search doctors by specialty and location
  - Book appointments
  - View appointment status

---

## How to Contribute

- Fork the repository
- Create a new branch (`git checkout -b feature-name`)
- Commit your changes
- Push to your branch
- Create a Pull Request

--- 

