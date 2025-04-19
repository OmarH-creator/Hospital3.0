# Hospital Management System - Master Checklist

> **Important Note:** Items marked as *(Documentation Only)* indicate that documentation templates or guides exist, but the actual implementation of tests described in those documents is still pending.

## Project Setup

- [x] **Initialize Project Structure**
  - [x] Set up Maven project with JavaFX dependencies
  - [x] Configure directory structure (src/main, src/test)
  - [x] Add JUnit 5 dependencies to pom.xml
  - [ ] Add TestFX dependencies for GUI testing
  - [ ] Add JaCoCo plugin for code coverage

- [x] **Configure Development Environment**
  - [x] Set up IntelliJ IDEA project
  - [x] Configure JDK
  - [x] Install necessary plugins (JavaFX, JUnit)
  - [x] Set up code style and formatting rules

- [x] **Organize Project Documentation**
  - [ ] Create docs folder for all project documentation
  - [ ] Organize test documentation and guides
  - [ ] Create testing summary and error tracking documents
  - [ ] Create project summary and completion report
  - [ ] Add script for test coverage generation

## Implementation Phase 1: Core Models

- [x] **Implement `Patient` Class**
  - [x] Basic attributes (ID, name, DOB, etc.)
  - [x] Validation in constructor
  - [x] Getters and setters
  - [x] Age calculation logic
  - [x] Admit/discharge functionality

- [x] **Implement `Appointment` Class**
  - [x] Core attributes (ID, patient, datetime, type)
  - [x] Validation logic
  - [x] Status management
  - [x] Relationship with Patient

- [x] **Implement `MedicalRecord` Class**
  - [x] Basic attributes (ID, patient, diagnosis, notes)
  - [x] Relationship to Appointment
  - [x] Date tracking
  - [x] Validation logic

- [x] **Implement `Bill` Class**
  - [x] Basic attributes (ID, patient, total amount)
  - [x] Line item handling
  - [x] Status tracking (paid/unpaid)
  - [x] Payment association

- [x] **Implement `InventoryItem` Class**
  - [x] Basic attributes (ID, name, quantity, unit price)
  - [x] Validation logic
  - [x] Stock management methods

## Implementation Phase 2: Service Layer

- [x] **Create Service Interfaces**
  - [x] Define `PatientService` interface
  - [x] Define `AppointmentService` interface
  - [x] Define `MedicalRecordService` interface
  - [x] Define `BillingService` interface
  - [x] Define `InventoryService` interface

- [x] **Implement Service Classes**
  - [x] Implement `PatientServiceImpl`
  - [x] Implement `AppointmentServiceImpl`
  - [x] Implement `MedicalRecordServiceImpl`
  - [x] Implement `BillingServiceImpl`
  - [x] Implement `InventoryServiceImpl`

- [ ] **Bug Fixes and Enhancements**
  - [ ] Fix model validation issues
  - [ ] Fix test isolation issues in service tests
  - [ ] Implement improved ID generation for BillingService
  - [ ] Fix Mockito unnecessary stubbing issues
  - [ ] Ensure all 119 tests are passing

## Implementation Phase 3: Controller Layer

- [x] **Implement `HospitalController`**
  - [x] Service dependency injection
  - [x] Patient registration methods
  - [x] Appointment scheduling methods
  - [x] Medical record creation methods
  - [x] Billing methods
  - [x] Inventory management methods
  - [ ] Cross-service workflow methods
  - [ ] Error handling and validation

## Implementation Phase 4: JavaFX UI

- [ ] **Design UI Screens**
  - [ ] Create Main Menu screen
  - [ ] Create Patient Registration screen
  - [ ] Create Appointment Scheduling screen
  - [ ] Create Medical Records view
  - [ ] Create Billing screen
  - [ ] Create Inventory Management screen

- [ ] **Implement UI & Controller Logic** _(Combined approach used)_
  - [ ] Implement MainMenuScreen with navigation controls
  - [ ] Implement PatientRegistrationScreen with patient management
  - [ ] Implement AppointmentSchedulingScreen with scheduling features
  - [ ] Implement MedicalRecordsScreen with record management
  - [ ] Implement BillingScreen with billing functions
  - [ ] Implement InventoryScreen with inventory management

- [ ] **Connect UI to Backend**
  - [ ] Connect UI screens directly to service layer
  - [ ] Implement validation and error handling in UI
  - [ ] Create navigation between screens
  - [ ] Implement data binding with ObservableLists

## Testing Phase 1: Unit Testing

- [x] **Set Up JUnit Test Suites**
  - [x] Configure Maven Surefire plugin for test reports
  - [x] Create test package structure
  - [x] Create test suite classes (outlined in junit5_test_setup.md)

- [x] **Create Model Tests**
  - [x] Create `PatientTest` class
  - [x] Create `AppointmentTest` class
  - [x] Create `MedicalRecordTest` class
  - [x] Create `BillTest` class
  - [x] Create `InventoryItemTest` class

- [x] **Create Service Tests**
  - [x] Create `PatientServiceTest` with mocked dependencies
  - [x] Create `AppointmentServiceTest` with mocked dependencies
  - [x] Create `MedicalRecordServiceTest` with mocked dependencies
  - [x] Create `BillingServiceTest` with mocked dependencies
  - [x] Create `InventoryServiceTest` with mocked dependencies

- [x] **Create Controller Tests**
  - [x] Create `HospitalControllerTest` with mocked services
  - [ ] Test controller validation logic
  - [ ] Test cross-service workflows

- [ ] **Implement Parameterized Tests**
  - [ ] Add parameterized tests for Patient validation
  - [ ] Add parameterized tests for Appointment scheduling
  - [ ] Add parameterized tests for billing operations

## Testing Documentation & Enhancement

- [ ] **Create JUnit Testing Documentation** *(Documentation Complete)*
  - [ ] Document test approaches in junit_testing_details.md
  - [ ] Provide examples of parameterized tests
  - [ ] Document test coverage measurement

- [ ] **Create White Box Testing Documentation** *(Documentation Complete)*
  - [ ] Document testing approach in white_box_testing_guide.md
  - [ ] Provide examples for critical code paths
  - [ ] Document coverage measurement techniques
  - [ ] Include boundary testing techniques

- [ ] **Create GUI Testing Documentation** *(Documentation Complete)*
  - [ ] Create FSM documentation in gui_fsm_plan.md
  - [ ] Document enhanced testing in gui_testing_enhanced.md
  - [ ] Include asynchronous testing techniques
  - [ ] Document test result capture and reporting

- [ ] **Create Integration Testing Documentation** *(Documentation Complete)*
  - [ ] Document testing approach in integration_testing_guide.md
  - [ ] Provide examples of multi-layer tests
  - [ ] Document error scenario testing
  - [ ] Include end-to-end workflow testing

- [ ] **Implement Error Tracking and Resolution**
  - [ ] Create errors_detected_and_fixed.md document
  - [ ] Document all identified and fixed issues
  - [ ] Categorize issues by component (Model, Service, etc.)
  - [ ] Document lessons learned from each fix

## Testing Phase 2: Integration Testing *(Partially Implemented)*

- [ ] **Set Up Integration Test Structure**
  - [ ] Create integration test package
  - [ ] Configure Maven for integration tests
  - [ ] Create example integration test

- [ ] **Implement Example Integration Test**
  - [ ] Create AppointmentBillingIntegrationTest
  - [ ] Demonstrate cross-service workflow testing
  - [ ] Complete implementation with actual service classes

- [ ] **Create Component Integration Tests**
  - [ ] Test Patient-Service integration
  - [ ] Test Service-Controller integration
  - [ ] Test error propagation between components

- [ ] **Create End-to-End Workflow Test**
  - [ ] Implement patient registration workflow test
  - [ ] Verify data consistency across components

## Testing Phase 3: White Box Testing *(Implemented for Model Classes)*

- [ ] **Set Up Code Coverage Tools**
  - [ ] Configure JaCoCo Maven plugin
  - [ ] Create test coverage script (run_tests_with_coverage.bat)
  - [ ] Set up IntelliJ code coverage runner

- [x] **White Box Testing for Model Classes**
  - [x] **Patient Class**
    - [x] Analyze code paths and branches
    - [x] Create tests for all branches in validation logic
    - [x] Implement boundary tests for age calculation
    - [x] Test admit/discharge state transitions
    - [x] Document in white_box_testing_guide.md
  
  - [x] **Appointment Class**
    - [x] Analyze code paths and branches
    - [x] Create tests for all validation branches
    - [x] Test status transition logic
    - [x] Test reschedule method boundary cases
    - [x] Document in white_box_testing_guide.md
  
  - [x] **InventoryItem Class**
    - [x] Analyze code paths and branches
    - [x] Test constructor validation branches
    - [x] Test stock management logic
    - [x] Test low stock detection logic
    - [x] Document in white_box_testing_guide.md

- [ ] **Generate White Box Testing Reports**
  - [ ] Create statement coverage report
  - [ ] Create branch coverage report
  - [ ] Document uncovered areas with justification

## Testing Phase 4: GUI Testing with FSM *(Not Yet Implemented)*

- [ ] **Set Up TestFX Environment**
  - [ ] Configure TestFX dependencies
  - [ ] Create base test class for GUI tests
  - [ ] Set up screenshot capture functionality

- [ ] **Implement FSM-Based Tests**
  - [ ] Test navigation between all states
  - [ ] Test form input validation
  - [ ] Test multi-screen workflows
  - [ ] Document test results with screenshots

## Project Completion

- [ ] **Documentation Organization**
  - [ ] Organize all documentation in docs folder
  - [ ] Create project summary document
  - [ ] Create project completion report
  - [ ] Update test coverage summary

- [ ] **Final Testing Report**
  - [ ] Run JaCoCo coverage analysis
  - [ ] Summarize test coverage metrics
  - [ ] Document remaining issues
  - [ ] Provide recommendations for further testing

- [ ] **Project Demo Preparation**
  - [ ] Prepare test execution examples
  - [ ] Document key testing insights

## Project Closure

- [ ] **Final Code Review**
  - [ ] Ensure code meets quality standards
  - [ ] Verify all tests are passing
  - [ ] Check test coverage meets targets

- [ ] **System Demo Preparation**
  - [ ] Prepare demo script
  - [ ] Test demo scenarios
  - [ ] Prepare for common questions

- [ ] **Project Handover**
  - [ ] Finalize all documentation
  - [ ] Ensure environment setup instructions are clear
  - [ ] Provide troubleshooting guide 