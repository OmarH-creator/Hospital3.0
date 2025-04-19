# Hospital System

A comprehensive hospital management system built in Java, featuring patient management, appointments, billing, medical records, and inventory. The system is modular, well-tested, and ready for extension with a GUI (JavaFX) or web interface.

## Features
- **Patient Management:** Register, update, and delete patients; track admission status.
- **Appointment Scheduling:** Schedule, update, cancel, and delete appointments.
- **Medical Records:** Add, update, and manage medical records linked to patients and appointments.
- **Billing:** Create, update, and mark bills as paid; manage line items and payment references.
- **Inventory Management:** Add, update, and track inventory items; manage stock levels.
- **Comprehensive Testing:** 200+ JUnit tests for all core logic and edge cases.

## Technologies Used
- Java 17+
- JavaFX (for planned GUI)
- JUnit 5 (for testing)
- Maven (build & dependency management)

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build the Project
```sh
mvn clean install
```

### Run Tests
```sh
mvn test
```

### Run the Application (Planned GUI)
Once the JavaFX GUI is implemented, you will be able to run:
```sh
mvn javafx:run
```

## Project Structure
- `src/main/java/com/example/hospitalsystemgpt/` — Main source code
- `src/test/java/com/example/hospitalsystemgpt/` — JUnit tests
- `src/main/java/module-info.java` — Java module configuration

## How to Contribute
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License.

## Contact
For questions or suggestions, please open an issue or contact the maintainer. 