# Software Testing Labs Summary

This document summarizes the key concepts, tools, and techniques covered in the software testing labs, with a focus on applying them to a hospital management system project with multiple roles (doctor, admin) using JavaFX.

---

## Lab 1: Introduction to Software Testing & JUnit
- **Topics:**
  - Software testing, validation, and verification
  - Agile vs. Waterfall methodologies
  - Why automation testing is important
  - The role of testing frameworks
  - Introduction to JUnit (Java testing framework)
- **Key Points:**
  - Automated tests help catch bugs early and ensure code quality over time.
  - JUnit is used for writing and running repeatable tests for Java code.
  - JUnit integrates with IDEs and build tools (Maven, Gradle).
- **Application:**
  - Use JUnit to test all logic in your hospital management system (models, services, controllers) for all roles (doctor, admin).

---

## Lab 2: JUnit Annotations & Assertions
- **Topics:**
  - JUnit annotations: `@Test`, `@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`
  - Writing test methods
  - Using assertions: `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`, etc.
- **Key Points:**
  - Annotations control test setup/teardown and mark test methods.
  - Assertions check that your code behaves as expected.
- **Application:**
  - Use these annotations and assertions in all your JUnit test classes for the hospital system, ensuring all functionalities for both doctor and admin roles are tested.

---

## Lab 3: Advanced JUnit Features
- **Topics:**
  - Test order: `@Order`, `@TestMethodOrder`
  - Repeated tests: `@RepeatedTest`
  - Nested tests: `@Nested`
  - Parameterized tests
- **Key Points:**
  - Test order is rarely needed, but can be used for dependent tests.
  - Repeated and parameterized tests help check code with multiple inputs or for stability.
  - Nested tests help organize related tests within a class.
- **Application:**
  - Use nested classes for grouping related tests (e.g., validation vs. state changes).
  - Use parameterized tests for input validation or edge cases, especially for workflows involving both doctor and admin.

---

## Lab 4: Test Suites & Best Practices
- **Topics:**
  - Creating and running test suites
  - Organizing tests for efficiency and maintainability
  - Best practices: keep suites small, isolate tests, run tests regularly
- **Key Points:**
  - Test suites group related tests for easier execution and management.
  - Isolate tests to avoid interference and make debugging easier.
- **Application:**
  - Create test suites for your hospital system (e.g., all model tests, all service tests, integration tests for doctor/admin workflows).

---

## Selenium Labs: Web Automation (For Web Apps Only)
- **Topics:**
  - Selenium for automating browser actions (clicks, form filling, navigation)
  - Locating web elements (ID, name, class, XPath, CSS selectors)
  - Handling alerts, pop-ups, and multiple windows
- **Key Points:**
  - Selenium is only for web applications (not JavaFX or console apps).
  - Use Selenium only if you build a web-based hospital system.
- **Application:**
  - For a JavaFX project, Selenium is not needed. Focus on JUnit and manual GUI testing.

---

## What to Focus on for Your Project
- **Use JUnit for all logic testing** (models, services, controllers, integration between doctor/admin workflows).
- **Organize tests using suites and nested classes** as shown in the labs.
- **Manual GUI testing and FSM diagrams are sufficient for JavaFX.**
- **Only use Selenium if you build a web app (not required here).**

---

**This summary ensures you (or anyone else) can continue the project using the same testing principles taught in your labs, with a focus on multi-role hospital management in JavaFX.** 