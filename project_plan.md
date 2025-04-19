# Hospital Management System Project Plan

This document outlines the step-by-step plan for building a Hospital Management System with doctor and admin roles, using JavaFX for the GUI, JUnit for all logic/integration/white box testing, FSM/manual GUI testing, and comprehensive documentation.

---

## 1. Core Model Implementation (with Testing)
- For each core model (`Patient`, `Appointment`, `MedicalRecord`, `Bill`, `InventoryItem`):
  1. Implement the class with only essential fields and methods.
  2. Immediately write a JUnit test class for it, covering all logic and branches (white box).
  3. Ensure 100% code and branch coverage for at least 3 classes (for the white box requirement).
  4. Design models to support both doctor and admin workflows (e.g., shared patient/appointment lists).

---

## 2. Service and Controller Layer
- Implement service classes (e.g., `PatientService`, `AppointmentService`) to handle business logic and data management.
- Implement a controller class (e.g., `HospitalController`) to coordinate between services and the GUI.
- Write JUnit tests for all service and controller logic, including role-based access and coherence between doctor and admin actions.

---

## 3. JavaFX GUI (Role-Based)
- Build a simple, functional JavaFX GUI for your main workflows:
  - Doctor: Register patients, schedule appointments, manage medical records
  - Admin: View/manage all patients and appointments, (optionally) billing and inventory
- Add a role selection screen at startup (Doctor/Admin) and show/hide features based on the selected role.
- Keep the GUI code separate from your logic.
- No need for automated GUI tests—manual testing and FSM documentation is sufficient.

---

## 4. GUI Testing with FSM
- Draw FSM (Finite State Machine) diagrams for your main GUI scenarios (e.g., doctor registers patient and schedules appointment, admin reviews data).
- Manually test the GUI for these scenarios and document the results (screenshots, test cases).
- Ensure coherence: changes by one role are visible to the other.

---

## 5. Integration Testing
- Write JUnit integration tests that check how your classes and services work together (e.g., doctor registers patient, admin views patient; doctor schedules appointment, admin sees it).
- Document your integration test cases and results.

---

## 6. Documentation
- Maintain a clear record of:
  - All test cases (unit, white box, integration, GUI/manual)
  - FSM diagrams for GUI scenarios
  - Test suites and architecture
  - Coverage reports for white box testing
  - Role-based workflow descriptions
- Use your `lab_summary.md` and checklist as guides.

---

## 7. Final Review and Polish
- Ensure all requirements are met (unit, white box, integration, GUI/FSM, role-based workflows).
- Organize your code and documentation for easy review.
- Run all tests and check coverage.

---

## Summary Table

| Step                        | What You'll Do                                      |
|-----------------------------|-----------------------------------------------------|
| 1. Core Models              | Implement + test each model class (doctor/admin)    |
| 2. Services/Controllers     | Implement + test business logic, role coherence     |
| 3. JavaFX GUI               | Build role-based screens, simple navigation         |
| 4. GUI Testing (FSM)        | Draw FSMs, do manual GUI testing, document results  |
| 5. Integration Testing      | Write and document integration tests                |
| 6. Documentation            | Organize all test cases, diagrams, and reports      |
| 7. Final Review             | Ensure all requirements are met and code is clean   |

---

**Follow this plan to ensure your project meets all requirements, supports both doctor and admin roles, and is easy to understand, test, and maintain.** 