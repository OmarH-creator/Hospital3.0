package com.example.hospitalsystemgpt.gui;

import com.example.hospitalsystemgpt.*;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HospitalApp extends Application {
    // --- Backend Services ---
    private final PatientService patientService = new PatientServiceImpl();
    private final AppointmentService appointmentService = new AppointmentServiceImpl();
    private final MedicalRecordService medicalRecordService = new MedicalRecordServiceImpl();
    private final BillingService billingService = new BillingServiceImpl();
    private final InventoryService inventoryService = new InventoryServiceImpl();

    // --- Controller ---
    private final HospitalController controller = new HospitalController(
            patientService, appointmentService, medicalRecordService, billingService, inventoryService
    );

    // --- Observable Lists for TableViews ---
    private final ObservableList<Patient> patientList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final ObservableList<MedicalRecord> recordList = FXCollections.observableArrayList();
    private final ObservableList<Bill> billList = FXCollections.observableArrayList();
    private final ObservableList<InventoryItem> inventoryList = FXCollections.observableArrayList();

    // --- Constants ---
    private static final String BUTTON_STYLE_PRIMARY = "-fx-background-color: #222; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 24 8 24; -fx-font-size: 15px; -fx-background-radius: 8;";
    private static final String BUTTON_STYLE_VIEW = "-fx-background-color: #fff; -fx-border-color: #bbb; -fx-border-radius: 4; -fx-background-radius: 4;";
    private static final String BUTTON_STYLE_EDIT = "-fx-background-color: #eef; -fx-border-color: #99f; -fx-border-radius: 4; -fx-background-radius: 4;";
    private static final String BUTTON_STYLE_DELETE = "-fx-background-color: #fee; -fx-border-color: #f99; -fx-border-radius: 4; -fx-background-radius: 4;";
    private static final String BUTTON_STYLE_ACTION1 = "-fx-background-color: #e0ffe0; -fx-border-color: #8f8; -fx-border-radius: 4; -fx-background-radius: 4;"; // Greenish (Admit, Complete)
    private static final String BUTTON_STYLE_ACTION2 = "-fx-background-color: #e0e0ff; -fx-border-color: #99f; -fx-border-radius: 4; -fx-background-radius: 4;"; // Bluish (Discharge, Cancel)
    private static final String BUTTON_STYLE_ACTION3 = "-fx-background-color: #fff0e0; -fx-border-color: #f90; -fx-border-radius: 4; -fx-background-radius: 4;"; // Orangish (Mark Paid)

    //Entry point for the JavaFX application. Shows the role selection screen where the user chooses Doctor or Admin.
    @Override
    public void start(Stage primaryStage) {
        Label prompt = new Label("Are you a Doctor or an Admin?");
        Button doctorBtn = new Button("Doctor");
        Button adminBtn = new Button("Admin");

        doctorBtn.setOnAction(e -> showDashboard(primaryStage, "Doctor"));
        adminBtn.setOnAction(e -> showDashboard(primaryStage, "Admin"));

        VBox root = new VBox(15, prompt, doctorBtn, adminBtn);
        root.setStyle("-fx-alignment: center; -fx-padding: 40;");

        Scene scene = new Scene(root, 300, 180);
        primaryStage.setTitle("Hospital System - Role Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /* Displays the dashboard for the selected role (Doctor or Admin).
     Shows relevant feature buttons and a logout button to return to the start page.*/
    private void showDashboard(Stage stage, String role) {
        Label dashLabel = new Label(role + " Dashboard");
        dashLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Button patientBtn = new Button("Patient Management");
        Button apptBtn = new Button("Appointment Scheduling");
        Button recordBtn = new Button("Medical Records");
        Button billingBtn = new Button("Billing");
        Button inventoryBtn = new Button("Inventory");
        Button logoutBtn = new Button("Logout");

        String dashButtonStyle = "-fx-min-width: 200px; -fx-padding: 10px; -fx-font-size: 14px;";
        patientBtn.setStyle(dashButtonStyle);
        apptBtn.setStyle(dashButtonStyle);
        recordBtn.setStyle(dashButtonStyle);
        billingBtn.setStyle(dashButtonStyle);
        inventoryBtn.setStyle(dashButtonStyle);
        logoutBtn.setStyle("-fx-min-width: 100px; -fx-padding: 8px;");

        patientBtn.setOnAction(e -> showPatientManagement(stage, role));
        apptBtn.setOnAction(e -> showAppointmentScheduling(stage, role));
        recordBtn.setOnAction(e -> showMedicalRecords(stage, role));
        billingBtn.setOnAction(e -> showBilling(stage, role));
        inventoryBtn.setOnAction(e -> showInventory(stage, role));
        logoutBtn.setOnAction(e -> start(stage));

        VBox dash;
        if (role.equals("Admin")) {
            dash = new VBox(12, dashLabel, billingBtn, inventoryBtn, logoutBtn);
        } else {
            dash = new VBox(12, dashLabel, patientBtn, apptBtn, recordBtn, logoutBtn);
        }
        dash.setStyle("-fx-alignment: center; -fx-padding: 40; -fx-background-color: #f0f0f0;");

        stage.setTitle("Hospital System - " + role + " Dashboard");
        stage.setScene(new Scene(dash, 400, 350));
    }

    // --- Common UI Building Blocks ---

    private HBox createTopBar(String title, Stage stage, String role) {
        Button backBtn = new Button("Back");
        Label titleLabel = new Label(title);
        backBtn.setOnAction(e -> showDashboard(stage, role));
        backBtn.setTooltip(new Tooltip("Return to dashboard"));
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        HBox topBar = new HBox(10, backBtn, titleLabel);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(0, 0, 10, 0));
        return topBar;
    }

    private HBox createSearchAddBar(String searchPrompt, String addTooltip, javafx.event.EventHandler<javafx.event.ActionEvent> addAction) {
        TextField searchField = new TextField();
        searchField.setPromptText(searchPrompt);
        searchField.setMinWidth(220);
        HBox searchBox = new HBox(8, new Label("\uD83D\uDD0D"), searchField);
        searchBox.setAlignment(Pos.CENTER_LEFT);

        Button addBtn = new Button("+ Add");
        addBtn.setStyle(BUTTON_STYLE_PRIMARY);
        addBtn.setTooltip(new Tooltip(addTooltip));
        addBtn.setOnAction(addAction);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox searchAddRow = new HBox(0, searchBox, spacer, addBtn);
        searchAddRow.setAlignment(Pos.CENTER_LEFT);
        searchAddRow.setPadding(new Insets(0, 0, 12, 0));

        // Return HBox (contains search field and add button)
        return searchAddRow;
    }

    // Helper to extract the search field from the SearchAddBar HBox
    private TextField getSearchFieldFromBar(HBox searchAddBar) {
        if (searchAddBar != null && searchAddBar.getChildren().get(0) instanceof HBox searchBox && searchBox.getChildren().size() > 1 && searchBox.getChildren().get(1) instanceof TextField) {
            return (TextField) searchBox.getChildren().get(1);
        }
        return null; // Or throw an exception if it's critical
    }

    private <T> VBox createMainScreenLayout(HBox topBar, HBox searchAddBar, TableView<T> table) {
        VBox tableBox = new VBox(0, searchAddBar, table);
        tableBox.setPadding(new Insets(0, 0, 0, 0));
        VBox root = new VBox(18, topBar, tableBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #fcfcfc;");
        VBox.setVgrow(tableBox, Priority.ALWAYS);
        return root;
    }

    // --- Patient Management ---
    private void showPatientManagement(Stage stage, String role) {
        HBox topBar = createTopBar("Patient Management", stage, role);
        HBox searchAddRow = createSearchAddBar("Search patients...", "Add a new patient", e -> showAddPatientDialog());
        TextField searchField = getSearchFieldFromBar(searchAddRow);

        TableView<Patient> table = createPatientTable();
        VBox root = createMainScreenLayout(topBar, searchAddRow, table);

        // Logic
        refreshPatientList();
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> filterPatientList(newVal));
        }

        stage.setTitle("Hospital System - Patient Management");
        stage.setScene(new Scene(root, 1100, 600));
    }

    private TableView<Patient> createPatientTable() {
        TableView<Patient> table = new TableView<>(patientList);
        table.setPrefHeight(420);
        TableColumn<Patient, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        idCol.setPrefWidth(80);
        TableColumn<Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        nameCol.setPrefWidth(180);
        TableColumn<Patient, LocalDate> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dobCol.setPrefWidth(120);
        TableColumn<Patient, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPatientAge()).asObject());
        ageCol.setPrefWidth(60);
        TableColumn<Patient, String> admittedCol = new TableColumn<>("Admitted");
        admittedCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isPatientAdmitted() ? "Yes" : "No"));
        admittedCol.setPrefWidth(80);
        TableColumn<Patient, Void> actionsCol = createPatientActionsColumn();
        actionsCol.setPrefWidth(320);

        table.getColumns().setAll(idCol, nameCol, dobCol, ageCol, admittedCol, actionsCol);
        table.setPlaceholder(new Label("No patients registered yet"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

    private TableColumn<Patient, Void> createPatientActionsColumn() {
        TableColumn<Patient, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("View");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final Button admitBtn = new Button("Admit");
            private final Button dischargeBtn = new Button("Discharge");
            {
                viewBtn.setOnAction(e -> showPatientDetails(getTableView().getItems().get(getIndex())));
                editBtn.setOnAction(e -> showEditPatientDialog(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deletePatientAction(getTableView().getItems().get(getIndex())));
                admitBtn.setOnAction(e -> admitPatientAction(getTableView().getItems().get(getIndex())));
                dischargeBtn.setOnAction(e -> dischargePatientAction(getTableView().getItems().get(getIndex())));
                viewBtn.setStyle(BUTTON_STYLE_VIEW);
                editBtn.setStyle(BUTTON_STYLE_EDIT);
                deleteBtn.setStyle(BUTTON_STYLE_DELETE);
                admitBtn.setStyle(BUTTON_STYLE_ACTION1);
                dischargeBtn.setStyle(BUTTON_STYLE_ACTION2);
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Patient p = getTableView().getItems().get(getIndex());
                    HBox box = new HBox(6, viewBtn, editBtn, deleteBtn);
                    if (p.isPatientAdmitted()) {
                        box.getChildren().add(dischargeBtn);
                    } else {
                        box.getChildren().add(admitBtn);
                    }
                    setGraphic(box);
                }
            }
        });
        return actionsCol;
    }

    private void refreshPatientList() {
        try {
            patientList.setAll(controller.getAllPatients());
        } catch (Exception ex) {
            showError("Failed to load patients: " + ex.getMessage());
            patientList.clear();
        }
    }

    private void filterPatientList(String searchText) {
        try {
            List<Patient> allPatients = controller.getAllPatients();
            if (searchText == null || searchText.isEmpty()) {
                patientList.setAll(allPatients);
            } else {
                String lowerCaseFilter = searchText.toLowerCase();
                patientList.setAll(allPatients.stream()
                        .filter(p -> p.getPatientName().toLowerCase().contains(lowerCaseFilter) ||
                                     p.getPatientId().toLowerCase().contains(lowerCaseFilter))
                        .collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            showError("Failed to filter patients: " + ex.getMessage());
        }
    }

    private void showPatientDetails(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Patient Details");
        alert.setHeaderText("Patient: " + patient.getPatientName());
        alert.setContentText("ID: " + patient.getPatientId() +
                "\nDate of Birth: " + patient.getDateOfBirth() +
                "\nAge: " + patient.getPatientAge() +
                "\nAdmitted: " + (patient.isPatientAdmitted() ? "Yes" : "No"));
        alert.showAndWait();
    }

    private void showAddPatientDialog() {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Add Patient");
        dialog.setHeaderText("Enter New Patient Details");

        // Controls
        TextField nameField = new TextField(); nameField.setPromptText("Name");
        DatePicker dobPicker = new DatePicker(); dobPicker.setPromptText("Date of Birth");

        // Layout
        VBox vbox = new VBox(10, new Label("Name:"), nameField, new Label("Date of Birth:"), dobPicker);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        nameField.textProperty().addListener((obs, oldV, newV) -> okButton.setDisable(newV.trim().isEmpty() || dobPicker.getValue() == null));
        dobPicker.valueProperty().addListener((obs, oldV, newV) -> okButton.setDisable(newV == null || nameField.getText().trim().isEmpty()));

        // Result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String name = nameField.getText().trim();
                    LocalDate dob = dobPicker.getValue();
                    // Basic validation (more in Patient constructor)
                    if (dob.isAfter(LocalDate.now())) {
                         showError("Date of Birth cannot be in the future.");
                         return null;
                    }
                    String id = "P" + (controller.getAllPatients().size() + 101); // Simple ID
                    return new Patient(id, name, dob);
                } catch (Exception e) {
                    showError("Invalid input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Patient> result = dialog.showAndWait();
        result.ifPresent(patient -> {
            try {
                controller.registerPatient(patient);
                refreshPatientList();
            } catch (Exception ex) {
                showError("Failed to add patient: " + ex.getMessage());
            }
        });
    }

    private void showEditPatientDialog(Patient patient) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Patient Name");
        dialog.setHeaderText("Editing Name for Patient ID: " + patient.getPatientId());

        TextField nameField = new TextField(patient.getPatientName());
        VBox vbox = new VBox(10, new Label("New Name:"), nameField);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only if name is not empty
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(nameField.getText().trim().isEmpty());
        nameField.textProperty().addListener((obs, oldV, newV) -> okButton.setDisable(newV.trim().isEmpty()));

        dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? nameField.getText().trim() : null);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newName -> {
            try {
                patient.setPatientName(newName); // Update local object
                controller.updatePatient(patient); // Persist change via controller/service
                refreshPatientList(); // Refresh the table view
            } catch (Exception ex) {
                showError("Failed to update patient name: " + ex.getMessage());
                 refreshPatientList(); // Refresh to show original state
            }
        });
    }

    private void deletePatientAction(Patient patient) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete patient '" + patient.getPatientName() + "' (ID: " + patient.getPatientId() + ")?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = controller.deletePatient(patient.getPatientId());
                    if (deleted) {
                        refreshPatientList();
                    } else {
                        showError("Patient could not be found or deleted.");
                    }
                } catch (Exception ex) {
                    showError("Failed to delete patient: " + ex.getMessage());
                }
            }
        });
    }

    private void admitPatientAction(Patient patient) {
        try {
            patient.admitPatient(); // Update local state
            controller.updatePatient(patient); // Persist change
            refreshPatientList(); // Refresh table
        } catch (Exception ex) {
            showError("Failed to admit patient: " + ex.getMessage());
            refreshPatientList(); // Refresh to show original state
        }
    }

    private void dischargePatientAction(Patient patient) {
         try {
            patient.dischargePatient(); // Update local state
            controller.updatePatient(patient); // Persist change
            refreshPatientList(); // Refresh table
        } catch (Exception ex) {
            showError("Failed to discharge patient: " + ex.getMessage());
            refreshPatientList(); // Refresh to show original state
        }
    }

    // --- Appointment Scheduling ---
    private void showAppointmentScheduling(Stage stage, String role) {
        HBox topBar = createTopBar("Appointment Scheduling", stage, role);
        HBox searchAddRow = createSearchAddBar("Search by patient name or ID...", "Schedule a new appointment", e -> showAddAppointmentDialog());
        TextField searchField = getSearchFieldFromBar(searchAddRow);

        TableView<Appointment> table = createAppointmentTable();
        VBox root = createMainScreenLayout(topBar, searchAddRow, table);

        // Logic
        refreshAppointmentList();
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> filterAppointmentList(newVal));
        }

        stage.setTitle("Hospital System - Appointment Scheduling");
        stage.setScene(new Scene(root, 1100, 600));
    }

    private TableView<Appointment> createAppointmentTable() {
        TableView<Appointment> table = new TableView<>(appointmentList);
        table.setPrefHeight(420);

        TableColumn<Appointment, String> idCol = new TableColumn<>("Appt ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        idCol.setPrefWidth(80);

        TableColumn<Appointment, String> patientNameCol = new TableColumn<>("Patient Name");
        patientNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientName()));
        patientNameCol.setPrefWidth(150);

        TableColumn<Appointment, String> patientIdCol = new TableColumn<>("Patient ID");
        patientIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientId()));
        patientIdCol.setPrefWidth(80);

        TableColumn<Appointment, LocalDateTime> dateTimeCol = new TableColumn<>("Date & Time");
        dateTimeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
        dateTimeCol.setPrefWidth(150);

        TableColumn<Appointment, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(120);

        TableColumn<Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        TableColumn<Appointment, Void> actionsCol = createAppointmentActionsColumn();
        actionsCol.setPrefWidth(300);

        table.getColumns().setAll(idCol, patientNameCol, patientIdCol, dateTimeCol, typeCol, statusCol, actionsCol);
        table.setPlaceholder(new Label("No appointments scheduled yet"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

    private TableColumn<Appointment, Void> createAppointmentActionsColumn() {
        TableColumn<Appointment, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button completeBtn = new Button("Complete");
            private final Button cancelBtn = new Button("Cancel");
            private final Button deleteBtn = new Button("Delete");
            {
                editBtn.setOnAction(e -> showEditAppointmentDialog(getTableView().getItems().get(getIndex())));
                completeBtn.setOnAction(e -> completeAppointmentAction(getTableView().getItems().get(getIndex())));
                cancelBtn.setOnAction(e -> cancelAppointmentAction(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteAppointmentAction(getTableView().getItems().get(getIndex())));
                editBtn.setStyle(BUTTON_STYLE_EDIT);
                completeBtn.setStyle(BUTTON_STYLE_ACTION1);
                cancelBtn.setStyle(BUTTON_STYLE_ACTION2);
                deleteBtn.setStyle(BUTTON_STYLE_DELETE);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Appointment appt = getTableView().getItems().get(getIndex());
                    HBox box = new HBox(6);
                    if (appt.getStatus() == Appointment.Status.SCHEDULED) {
                        box.getChildren().addAll(editBtn, completeBtn, cancelBtn);
                    } else {
                         // Optionally add edit button even for completed/cancelled if desired
                         // box.getChildren().add(editBtn);
                    }
                    box.getChildren().add(deleteBtn);
                    setGraphic(box);

                    // Disable buttons based on status
                    editBtn.setDisable(appt.getStatus() != Appointment.Status.SCHEDULED);
                    completeBtn.setDisable(appt.getStatus() != Appointment.Status.SCHEDULED);
                    cancelBtn.setDisable(appt.getStatus() != Appointment.Status.SCHEDULED);
                }
            }
        });
        actionsCol.setPrefWidth(320); // Adjust width slightly
        return actionsCol;
    }

    private void refreshAppointmentList() {
        try {
            appointmentList.setAll(controller.getAllAppointments());
        } catch (Exception ex) {
            showError("Failed to load appointments: " + ex.getMessage());
            appointmentList.clear();
        }
    }

    private void filterAppointmentList(String searchText) {
        try {
            List<Appointment> allAppointments = controller.getAllAppointments();
            if (searchText == null || searchText.isEmpty()) {
                appointmentList.setAll(allAppointments);
            } else {
                String lowerCaseFilter = searchText.toLowerCase();
                appointmentList.setAll(allAppointments.stream()
                        .filter(a -> a.getPatient().getPatientName().toLowerCase().contains(lowerCaseFilter) ||
                                     a.getPatient().getPatientId().toLowerCase().contains(lowerCaseFilter) ||
                                     a.getAppointmentId().toLowerCase().contains(lowerCaseFilter) ||
                                     a.getType().toLowerCase().contains(lowerCaseFilter))
                        .collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            showError("Failed to filter appointments: " + ex.getMessage());
        }
    }

    private void showAddAppointmentDialog() {
        Dialog<Appointment> dialog = new Dialog<>();
        dialog.setTitle("Schedule Appointment");
        dialog.setHeaderText("Enter Appointment Details");

        // Controls
        ComboBox<Patient> patientCombo = new ComboBox<>(patientList); // Use existing patient list
        patientCombo.setPromptText("Select Patient");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        // Basic Time Picker (Consider a better control for real apps)
        ComboBox<Integer> hourCombo = new ComboBox<>(FXCollections.observableArrayList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17)); hourCombo.setPromptText("Hour");
        ComboBox<Integer> minuteCombo = new ComboBox<>(FXCollections.observableArrayList(0, 15, 30, 45)); minuteCombo.setPromptText("Minute");
        TextField typeField = new TextField(); typeField.setPromptText("Appointment Type (e.g., Checkup)");

        // Layout
        HBox timeBox = new HBox(5, hourCombo, new Label(":"), minuteCombo);
        VBox vbox = new VBox(10, new Label("Patient:"), patientCombo,
                             new Label("Date:"), datePicker,
                             new Label("Time:"), timeBox,
                             new Label("Type:"), typeField);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when all fields are valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        Runnable updateOkButtonState = () -> {
            boolean disabled = patientCombo.getValue() == null ||
                               datePicker.getValue() == null ||
                               hourCombo.getValue() == null ||
                               minuteCombo.getValue() == null ||
                               typeField.getText().trim().isEmpty();
            okButton.setDisable(disabled);
        };
        patientCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        datePicker.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        hourCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        minuteCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        typeField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());


        // Result Converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    Patient selectedPatient = patientCombo.getValue();
                    LocalDate date = datePicker.getValue();
                    int hour = hourCombo.getValue();
                    int minute = minuteCombo.getValue();
                    LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                    String type = typeField.getText().trim();

                    if (dateTime.isBefore(LocalDateTime.now())) {
                        showError("Appointment date/time cannot be in the past.");
                        return null;
                    }

                    String id = "A" + (controller.getAllAppointments().size() + 1001); // Simple ID
                    return new Appointment(id, selectedPatient, dateTime, type);
                } catch (Exception e) {
                    showError("Invalid input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        // Refresh patient list for the combo box before showing
        refreshPatientList();
        if (patientList.isEmpty()) {
             showError("Cannot schedule appointment: No patients registered.");
             return;
        }

        Optional<Appointment> result = dialog.showAndWait();
        result.ifPresent(appointment -> {
            try {
                controller.scheduleAppointment(appointment);
                refreshAppointmentList();
            } catch (Exception ex) {
                showError("Failed to schedule appointment: " + ex.getMessage());
            }
        });
    }

    private void completeAppointmentAction(Appointment appointment) {
        try {
            appointment.complete(); // Update local state
            controller.updateAppointment(appointment); // Persist change
            refreshAppointmentList(); // Refresh table
        } catch (IllegalStateException ise) {
             showError("Cannot complete appointment: " + ise.getMessage());
             refreshAppointmentList(); // Refresh just in case state was inconsistent
        }
         catch (Exception ex) {
            showError("Failed to complete appointment: " + ex.getMessage());
            refreshAppointmentList(); // Refresh to show original state
        }
    }

    private void cancelAppointmentAction(Appointment appointment) {
         try {
            boolean cancelled = controller.cancelAppointment(appointment.getAppointmentId()); // Use controller/service
            if (cancelled) {
                refreshAppointmentList(); // Refresh table
            } else {
                 showError("Could not cancel appointment (already cancelled or completed?).");
                 refreshAppointmentList(); // Ensure view is up-to-date
            }
        } catch (IllegalStateException ise) {
             showError("Cannot cancel appointment: " + ise.getMessage());
             refreshAppointmentList();
        } catch (Exception ex) {
            showError("Failed to cancel appointment: " + ex.getMessage());
            refreshAppointmentList();
        }
    }

    private void deleteAppointmentAction(Appointment appointment) {
         Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete appointment '" + appointment.getAppointmentId() + "' for patient '" + appointment.getPatient().getPatientName() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = controller.deleteAppointment(appointment.getAppointmentId());
                    if (deleted) {
                        refreshAppointmentList();
                    } else {
                        showError("Appointment could not be found or deleted.");
                    }
                } catch (Exception ex) {
                    showError("Failed to delete appointment: " + ex.getMessage());
                }
            }
        });
    }

    private void showEditAppointmentDialog(Appointment appointment) {
        if (appointment.getStatus() != Appointment.Status.SCHEDULED) {
            showError("Only scheduled appointments can be edited.");
            return;
        }

        Dialog<Appointment> dialog = new Dialog<>();
        dialog.setTitle("Edit Appointment");
        dialog.setHeaderText("Editing Appointment ID: " + appointment.getAppointmentId());

        // Controls - pre-fill with existing data
        Label patientLabel = new Label("Patient: " + appointment.getPatient().getPatientName() + " (ID: " + appointment.getPatient().getPatientId() + ")");
        DatePicker datePicker = new DatePicker(appointment.getDateTime().toLocalDate());
        ComboBox<Integer> hourCombo = new ComboBox<>(FXCollections.observableArrayList(8, 9, 10, 11, 12, 13, 14, 15, 16, 17));
        hourCombo.setValue(appointment.getDateTime().getHour());
        ComboBox<Integer> minuteCombo = new ComboBox<>(FXCollections.observableArrayList(0, 15, 30, 45));
        minuteCombo.setValue(appointment.getDateTime().getMinute());
        TextField typeField = new TextField(appointment.getType());

        // Layout
        HBox timeBox = new HBox(5, hourCombo, new Label(":"), minuteCombo);
        VBox vbox = new VBox(10, patientLabel,
                             new Label("Date:"), datePicker,
                             new Label("Time:"), timeBox,
                             new Label("Type:"), typeField);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when all fields are valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(false); // Start enabled as fields are pre-filled
        Runnable updateOkButtonState = () -> {
            boolean disabled = datePicker.getValue() == null ||
                               hourCombo.getValue() == null ||
                               minuteCombo.getValue() == null ||
                               typeField.getText().trim().isEmpty();
            okButton.setDisable(disabled);
        };
        datePicker.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        hourCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        minuteCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        typeField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());

        // Result Converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    LocalDate date = datePicker.getValue();
                    int hour = hourCombo.getValue();
                    int minute = minuteCombo.getValue();
                    LocalDateTime newDateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                    String newType = typeField.getText().trim();

                    if (newDateTime.isBefore(LocalDateTime.now())) {
                        showError("New appointment date/time cannot be in the past.");
                        return null;
                    }

                    // Create a new appointment object with updated fields but same ID and patient
                    // Because the original appointment fields are final
                    return new Appointment(appointment.getAppointmentId(), appointment.getPatient(), newDateTime, newType);
                } catch (IllegalArgumentException e) {
                    showError("Invalid input: " + e.getMessage());
                    return null;
                } catch (Exception e) {
                    showError("Error processing input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<Appointment> result = dialog.showAndWait();
        result.ifPresent(updatedAppointment -> {
            try {
                controller.updateAppointment(updatedAppointment);
                refreshAppointmentList();
            } catch (Exception ex) {
                showError("Failed to update appointment: " + ex.getMessage());
                refreshAppointmentList(); // Refresh to show original state
            }
        });
    }

    // --- Medical Records ---
    private void showMedicalRecords(Stage stage, String role) {
         HBox topBar = createTopBar("Medical Records", stage, role);
         HBox searchAddRow = createSearchAddBar("Search records by patient or diagnosis...", "Add a new medical record", e -> showAddMedicalRecordDialog());
         TextField searchField = getSearchFieldFromBar(searchAddRow);

         TableView<MedicalRecord> table = createMedicalRecordTable();
         VBox root = createMainScreenLayout(topBar, searchAddRow, table);

         // Logic
         refreshMedicalRecordList();
         if (searchField != null) {
             searchField.textProperty().addListener((obs, oldVal, newVal) -> filterMedicalRecordList(newVal));
         }

         stage.setTitle("Hospital System - Medical Records");
         stage.setScene(new Scene(root, 1100, 600));
    }

     private TableView<MedicalRecord> createMedicalRecordTable() {
        TableView<MedicalRecord> table = new TableView<>(recordList);
        table.setPrefHeight(420);

        TableColumn<MedicalRecord, String> idCol = new TableColumn<>("Record ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        idCol.setPrefWidth(100);

        TableColumn<MedicalRecord, String> patientNameCol = new TableColumn<>("Patient Name");
        patientNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientName()));
        patientNameCol.setPrefWidth(150);

        TableColumn<MedicalRecord, String> patientIdCol = new TableColumn<>("Patient ID");
        patientIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientId()));
        patientIdCol.setPrefWidth(100);

        TableColumn<MedicalRecord, String> apptIdCol = new TableColumn<>("Appt ID");
        apptIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAppointment().getAppointmentId()));
        apptIdCol.setPrefWidth(100);

        TableColumn<MedicalRecord, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(120);

        TableColumn<MedicalRecord, String> diagnosisCol = new TableColumn<>("Diagnosis");
        diagnosisCol.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));
        diagnosisCol.setPrefWidth(200);

        TableColumn<MedicalRecord, Void> actionsCol = createMedicalRecordActionsColumn();
        actionsCol.setPrefWidth(230);


        table.getColumns().setAll(idCol, patientNameCol, patientIdCol, apptIdCol, dateCol, diagnosisCol, actionsCol);
        table.setPlaceholder(new Label("No medical records found"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

      private TableColumn<MedicalRecord, Void> createMedicalRecordActionsColumn() {
        TableColumn<MedicalRecord, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("View Details");
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                viewBtn.setOnAction(e -> showMedicalRecordDetails(getTableView().getItems().get(getIndex())));
                editBtn.setOnAction(e -> showEditMedicalRecordDialog(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteMedicalRecordAction(getTableView().getItems().get(getIndex())));
                viewBtn.setStyle(BUTTON_STYLE_VIEW);
                editBtn.setStyle(BUTTON_STYLE_EDIT);
                deleteBtn.setStyle(BUTTON_STYLE_DELETE);
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(6, viewBtn, editBtn, deleteBtn);
                    setGraphic(box);
                }
            }
        });
         actionsCol.setPrefWidth(230);
        return actionsCol;
    }

    private void refreshMedicalRecordList() {
        try {
            recordList.setAll(controller.getAllMedicalRecords());
        } catch (Exception ex) {
            showError("Failed to load medical records: " + ex.getMessage());
            recordList.clear();
        }
    }

    private void filterMedicalRecordList(String searchText) {
        try {
            List<MedicalRecord> allRecords = controller.getAllMedicalRecords();
            if (searchText == null || searchText.isEmpty()) {
                recordList.setAll(allRecords);
            } else {
                String lowerCaseFilter = searchText.toLowerCase();
                recordList.setAll(allRecords.stream()
                        .filter(r -> r.getPatient().getPatientName().toLowerCase().contains(lowerCaseFilter) ||
                                     r.getPatient().getPatientId().toLowerCase().contains(lowerCaseFilter) ||
                                     r.getRecordId().toLowerCase().contains(lowerCaseFilter) ||
                                     r.getDiagnosis().toLowerCase().contains(lowerCaseFilter))
                        .collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            showError("Failed to filter medical records: " + ex.getMessage());
        }
    }

     private void showMedicalRecordDetails(MedicalRecord record) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medical Record Details");
        alert.setHeaderText("Record ID: " + record.getRecordId() + " for Patient: " + record.getPatient().getPatientName());

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setText(
                "Patient ID: " + record.getPatient().getPatientId() +
                "\nAppointment ID: " + record.getAppointment().getAppointmentId() +
                "\nRecord Date: " + record.getDate() +
                "\n\nDiagnosis:\n" + record.getDiagnosis() +
                "\n\nNotes:\n" + (record.getNotes() != null && !record.getNotes().isEmpty() ? record.getNotes() : "N/A")
        );
        textArea.setPrefRowCount(10);
        textArea.setPrefColumnCount(40);


        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.showAndWait();
    }

     private void showEditMedicalRecordDialog(MedicalRecord record) {
        Dialog<MedicalRecord> dialog = new Dialog<>();
        dialog.setTitle("Edit Medical Record");
        dialog.setHeaderText("Editing Record ID: " + record.getRecordId());

        // Controls - Pre-fill with existing data
        TextField diagnosisField = new TextField(record.getDiagnosis());
        TextArea notesArea = new TextArea(record.getNotes());
        notesArea.setPrefRowCount(4);

        // Layout
        VBox vbox = new VBox(10, new Label("Diagnosis:"), diagnosisField,
                             new Label("Notes:"), notesArea);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when diagnosis is not empty
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(diagnosisField.getText().trim().isEmpty());
        diagnosisField.textProperty().addListener((obs, oldV, newV) -> okButton.setDisable(newV.trim().isEmpty()));

        // Result Converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String newDiagnosis = diagnosisField.getText().trim();
                    String newNotes = notesArea.getText();

                    // Create a new record object with updated fields but same ID, patient, appt, date
                    // Because the original record fields are final
                    return new MedicalRecord(record.getRecordId(), record.getPatient(), record.getAppointment(),
                                             newDiagnosis, newNotes, record.getDate());
                } catch (IllegalArgumentException e) {
                     showError("Invalid input: " + e.getMessage());
                     return null;
                } catch (Exception e) {
                    showError("Error processing input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<MedicalRecord> result = dialog.showAndWait();
        result.ifPresent(updatedRecord -> {
            try {
                controller.updateMedicalRecord(updatedRecord);
                refreshMedicalRecordList();
            } catch (Exception ex) {
                showError("Failed to update medical record: " + ex.getMessage());
                refreshMedicalRecordList(); // Refresh to show original state
            }
        });
    }

     private void showAddMedicalRecordDialog() {
         Dialog<MedicalRecord> dialog = new Dialog<>();
        dialog.setTitle("Add Medical Record");
        dialog.setHeaderText("Enter Record Details");

        // Controls
        ComboBox<Patient> patientCombo = new ComboBox<>(patientList); patientCombo.setPromptText("Select Patient");
        ComboBox<Appointment> appointmentCombo = new ComboBox<>(appointmentList); appointmentCombo.setPromptText("Select Related Appointment");
        DatePicker datePicker = new DatePicker(LocalDate.now());
        TextField diagnosisField = new TextField(); diagnosisField.setPromptText("Diagnosis");
        TextArea notesArea = new TextArea(); notesArea.setPromptText("Notes (Optional)");
        notesArea.setPrefRowCount(4);

         // Filter appointments based on selected patient
        patientCombo.valueProperty().addListener((obs, oldPatient, newPatient) -> {
            if (newPatient != null) {
                List<Appointment> patientAppointments = appointmentList.stream()
                        .filter(a -> a.getPatient().equals(newPatient))
                        .collect(Collectors.toList());
                appointmentCombo.setItems(FXCollections.observableArrayList(patientAppointments));
                appointmentCombo.setDisable(patientAppointments.isEmpty());
                 if (patientAppointments.isEmpty()) {
                      appointmentCombo.setPromptText("No appointments for this patient");
                 } else {
                     appointmentCombo.setPromptText("Select Related Appointment");
                 }
            } else {
                appointmentCombo.getItems().clear();
                appointmentCombo.setDisable(true);
                appointmentCombo.setPromptText("Select Patient First");
            }
            appointmentCombo.getSelectionModel().clearSelection(); // Clear selection when patient changes
        });

        // Layout
        VBox vbox = new VBox(10, new Label("Patient:"), patientCombo,
                             new Label("Appointment:"), appointmentCombo,
                             new Label("Record Date:"), datePicker,
                             new Label("Diagnosis:"), diagnosisField,
                             new Label("Notes:"), notesArea);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

         // Enable OK only when valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        Runnable updateOkButtonState = () -> {
             boolean disabled = patientCombo.getValue() == null ||
                                appointmentCombo.getValue() == null || // Must select an appointment
                               datePicker.getValue() == null ||
                               diagnosisField.getText().trim().isEmpty();
             okButton.setDisable(disabled);
        };
         patientCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
         appointmentCombo.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
         datePicker.valueProperty().addListener((obs, o, n) -> updateOkButtonState.run());
         diagnosisField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());


         dialog.setResultConverter(dialogButton -> {
             if (dialogButton == ButtonType.OK) {
                 try {
                     Patient patient = patientCombo.getValue();
                     Appointment appointment = appointmentCombo.getValue();
                     LocalDate date = datePicker.getValue();
                     String diagnosis = diagnosisField.getText().trim();
                     String notes = notesArea.getText();

                     if (date.isAfter(LocalDate.now())) {
                         showError("Record date cannot be in the future.");
                         return null;
                     }

                     String id = "MR" + (controller.getAllMedicalRecords().size() + 10001); // Simple ID
                     return new MedicalRecord(id, patient, appointment, diagnosis, notes, date);
                 } catch (NumberFormatException e) {
                     showError("Invalid number format for quantity or price.");
                     return null;
                 } catch (IllegalArgumentException e) {
                     showError("Invalid input: " + e.getMessage());
                     return null;
                 }
             }
             return null;
         });

         // Refresh lists needed for combo boxes
        refreshPatientList();
        refreshAppointmentList(); // Need appointments to link
        if (patientList.isEmpty() || appointmentList.isEmpty()) {
             showError("Cannot add record: Patients or Appointments are missing.");
             return;
        }
        appointmentCombo.setDisable(true); // Disable until patient selected

        Optional<MedicalRecord> result = dialog.showAndWait();
        result.ifPresent(record -> {
            try {
                controller.addMedicalRecord(record);
                refreshMedicalRecordList();
            } catch (Exception ex) {
                showError("Failed to add medical record: " + ex.getMessage());
            }
        });
    }


    private void deleteMedicalRecordAction(MedicalRecord record) {
         Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete medical record '" + record.getRecordId() + "' for patient '" + record.getPatient().getPatientName() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = controller.deleteMedicalRecord(record.getRecordId());
                    if (deleted) {
                        refreshMedicalRecordList();
                    } else {
                        showError("Medical record could not be found or deleted.");
                    }
                } catch (Exception ex) {
                    // Catch potential dependencies if inventory is linked elsewhere
                    showError("Failed to delete medical record: " + ex.getMessage());
                }
            }
        });
    }

    // --- Billing (Admin Only) ---
    private void showBilling(Stage stage, String role) {
        if (!role.equals("Admin")) {
            showError("Access Denied: Billing is only available for Admins.");
            showDashboard(stage, role); // Go back
            return;
        }

        HBox topBar = createTopBar("Billing Management", stage, role);
        HBox searchAddRow = createSearchAddBar("Search bills by patient or bill ID...", "Create a new bill", e -> showAddBillDialog());
        TextField searchField = getSearchFieldFromBar(searchAddRow);

        TableView<Bill> table = createBillingTable();
        VBox root = createMainScreenLayout(topBar, searchAddRow, table);

        // Logic
        refreshBillList();
        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> filterBillList(newVal));
        }

        stage.setTitle("Hospital System - Billing");
        stage.setScene(new Scene(root, 1100, 600));
    }

    private TableView<Bill> createBillingTable() {
        TableView<Bill> table = new TableView<>(billList);
        table.setPrefHeight(420);

        TableColumn<Bill, String> idCol = new TableColumn<>("Bill ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("billId"));
        idCol.setPrefWidth(100);

        TableColumn<Bill, String> patientNameCol = new TableColumn<>("Patient Name");
        patientNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientName()));
        patientNameCol.setPrefWidth(150);

        TableColumn<Bill, String> patientIdCol = new TableColumn<>("Patient ID");
        patientIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatient().getPatientId()));
        patientIdCol.setPrefWidth(100);

        TableColumn<Bill, Double> amountCol = new TableColumn<>("Total Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        amountCol.setPrefWidth(120);
        // Format as currency
        amountCol.setCellFactory(tc -> new TableCell<Bill, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });


        TableColumn<Bill, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        TableColumn<Bill, Void> actionsCol = createBillingActionsColumn();
        actionsCol.setPrefWidth(350); // Wider for more actions

        table.getColumns().setAll(idCol, patientNameCol, patientIdCol, amountCol, statusCol, actionsCol);
        table.setPlaceholder(new Label("No bills generated yet"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

     private TableColumn<Bill, Void> createBillingActionsColumn() {
        TableColumn<Bill, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button viewBtn = new Button("View Items");
            private final Button addLineItemBtn = new Button("Add Item");
            private final Button markPaidBtn = new Button("Mark Paid");
            private final Button deleteBtn = new Button("Delete");
            {
                viewBtn.setOnAction(e -> showBillDetails(getTableView().getItems().get(getIndex())));
                addLineItemBtn.setOnAction(e -> showAddLineItemDialog(getTableView().getItems().get(getIndex())));
                markPaidBtn.setOnAction(e -> markBillPaidAction(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteBillAction(getTableView().getItems().get(getIndex())));

                viewBtn.setStyle(BUTTON_STYLE_VIEW);
                addLineItemBtn.setStyle(BUTTON_STYLE_EDIT); // Reuse edit style
                markPaidBtn.setStyle(BUTTON_STYLE_ACTION3); // Orangish
                deleteBtn.setStyle(BUTTON_STYLE_DELETE);
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
                    setGraphic(null);
                } else {
                    Bill bill = getTableView().getItems().get(getIndex());
                    HBox box = new HBox(6, viewBtn);
                    if (bill.getStatus() == Bill.Status.UNPAID) {
                        box.getChildren().addAll(addLineItemBtn, markPaidBtn);
                    }
                    box.getChildren().add(deleteBtn);
                    setGraphic(box);

                    // Disable buttons based on status
                    addLineItemBtn.setDisable(bill.getStatus() == Bill.Status.PAID);
                    markPaidBtn.setDisable(bill.getStatus() == Bill.Status.PAID || bill.getTotalAmount() <= 0); // Don't mark empty bill paid
                }
            }
        });
        return actionsCol;
    }

     private void refreshBillList() {
        try {
            billList.setAll(controller.getAllBills());
        } catch (Exception ex) {
            showError("Failed to load bills: " + ex.getMessage());
            billList.clear();
        }
    }

    private void filterBillList(String searchText) {
        try {
            List<Bill> allBills = controller.getAllBills();
            if (searchText == null || searchText.isEmpty()) {
                billList.setAll(allBills);
            } else {
                String lowerCaseFilter = searchText.toLowerCase();
                billList.setAll(allBills.stream()
                        .filter(b -> b.getPatient().getPatientName().toLowerCase().contains(lowerCaseFilter) ||
                                     b.getPatient().getPatientId().toLowerCase().contains(lowerCaseFilter) ||
                                     b.getBillId().toLowerCase().contains(lowerCaseFilter))
                        .collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            showError("Failed to filter bills: " + ex.getMessage());
        }
    }

     private void showBillDetails(Bill bill) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bill Details");
        alert.setHeaderText("Bill ID: " + bill.getBillId() + " for Patient: " + bill.getPatient().getPatientName());

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        StringBuilder content = new StringBuilder();
        content.append("Patient ID: ").append(bill.getPatient().getPatientId()).append("\n");
        content.append("Status: ").append(bill.getStatus()).append("\n");
        if (bill.getStatus() == Bill.Status.PAID) {
             content.append("Payment Reference: ").append(bill.getPaymentReference()).append("\n");
        }
        content.append("\nLine Items:\n");
        if (bill.getLineItems().isEmpty()) {
            content.append("  (No items added yet)\n");
        } else {
            for (Bill.LineItem item : bill.getLineItems()) {
                content.append(String.format("  - %s: $%.2f\n", item.getDescription(), item.getAmount()));
            }
        }
        content.append("\n--------------------\n");
        content.append(String.format("Total Amount: $%.2f\n", bill.getTotalAmount()));

        textArea.setText(content.toString());
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(50);

        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);
        alert.showAndWait();
    }

     private void showAddBillDialog() {
         // A bill needs a patient. Create an unpaid bill for a patient first.
         Dialog<Patient> dialog = new Dialog<>();
         dialog.setTitle("Create New Bill");
         dialog.setHeaderText("Select Patient for New Bill");

         ComboBox<Patient> patientCombo = new ComboBox<>(patientList);
         patientCombo.setPromptText("Select Patient");

         VBox vbox = new VBox(10, new Label("Patient:"), patientCombo);
         vbox.setPadding(new Insets(10));
         dialog.getDialogPane().setContent(vbox);
         dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

         // Enable OK only when patient selected
         Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
         okButton.setDisable(true);
         patientCombo.valueProperty().addListener((obs, o, n) -> okButton.setDisable(n == null));


         dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? patientCombo.getValue() : null);

         // Refresh patient list
         refreshPatientList();
         if (patientList.isEmpty()) {
              showError("Cannot create bill: No patients registered.");
              return;
         }


         Optional<Patient> result = dialog.showAndWait();
         result.ifPresent(patient -> {
             try {
                 String id = "B" + (controller.getAllBills().size() + 101); // Simple ID
                 Bill newBill = new Bill(id, patient); // Creates an empty, unpaid bill
                 controller.createBill(newBill);
                 refreshBillList();
             } catch (Exception ex) {
                 showError("Failed to create bill: " + ex.getMessage());
             }
         });
    }

     private void showAddLineItemDialog(Bill bill) {
         if (bill.getStatus() == Bill.Status.PAID) {
             showError("Cannot add items to a paid bill.");
             return;
         }

         Dialog<Bill.LineItem> dialog = new Dialog<>();
         dialog.setTitle("Add Line Item");
         dialog.setHeaderText("Add Item to Bill ID: " + bill.getBillId());

         TextField descriptionField = new TextField(); descriptionField.setPromptText("Item Description");
         TextField amountField = new TextField(); amountField.setPromptText("Amount (e.g., 12.50)");

         // Basic numeric validation listener
         amountField.textProperty().addListener((observable, oldValue, newValue) -> {
             if (!newValue.matches("\\d*([.]\\d{0,2})?")) {
                 amountField.setText(oldValue);
             }
         });

         VBox vbox = new VBox(10, new Label("Description:"), descriptionField, new Label("Amount ($):"), amountField);
         vbox.setPadding(new Insets(10));
         dialog.getDialogPane().setContent(vbox);
         dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


          // Enable OK only when valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
        Runnable updateOkButtonState = () -> {
             boolean disabled = descriptionField.getText().trim().isEmpty() ||
                                amountField.getText().trim().isEmpty();
             try {
                 if (!disabled) Double.parseDouble(amountField.getText()); // Check if valid double
             } catch (NumberFormatException e) {
                 disabled = true;
             }
             okButton.setDisable(disabled);
        };
        descriptionField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        amountField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());


         dialog.setResultConverter(dialogButton -> {
             if (dialogButton == ButtonType.OK) {
                 try {
                     String description = descriptionField.getText().trim();
                     double amount = Double.parseDouble(amountField.getText());
                     return new Bill.LineItem(description, amount);
                 } catch (NumberFormatException e) {
                     showError("Invalid amount format.");
                     return null;
                 } catch (IllegalArgumentException e) {
                      showError("Invalid input: " + e.getMessage());
                      return null;
                 }
             }
             return null;
         });

         Optional<Bill.LineItem> result = dialog.showAndWait();
         result.ifPresent(lineItem -> {
             try {
                 bill.addLineItem(lineItem); // Add to local object
                 controller.updateBill(bill); // Persist change
                 refreshBillList(); // Refresh table
             } catch (Exception ex) {
                 showError("Failed to add line item: " + ex.getMessage());
                 refreshBillList();
             }
         });
    }

    private void markBillPaidAction(Bill bill) {
         if (bill.getStatus() == Bill.Status.PAID) {
             showError("Bill is already marked as paid.");
             return;
         }
          if (bill.getTotalAmount() <= 0) {
             showError("Cannot mark an empty or zero-amount bill as paid.");
             return;
         }


         TextInputDialog dialog = new TextInputDialog();
         dialog.setTitle("Mark Bill as Paid");
         dialog.setHeaderText("Enter Payment Reference for Bill ID: " + bill.getBillId());
         dialog.setContentText("Payment Reference:");

         Optional<String> result = dialog.showAndWait();
         result.ifPresent(reference -> {
             if (reference.trim().isEmpty()) {
                 showError("Payment reference cannot be empty.");
                 return;
             }
             try {
                 controller.markBillAsPaid(bill.getBillId(), reference.trim()); // Use controller/service
                 refreshBillList(); // Refresh table
             } catch (IllegalArgumentException | IllegalStateException e) {
                 showError("Could not mark bill as paid: " + e.getMessage());
                 refreshBillList();
             } catch (Exception ex) {
                  showError("An unexpected error occurred: " + ex.getMessage());
                  refreshBillList();
             }
         });
    }

     private void deleteBillAction(Bill bill) {
          Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete bill '" + bill.getBillId() + "' for patient '" + bill.getPatient().getPatientName() + "'?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = controller.deleteBill(bill.getBillId());
                    if (deleted) {
                        refreshBillList();
                    } else {
                        showError("Bill could not be found or deleted.");
                    }
                } catch (Exception ex) {
                    showError("Failed to delete bill: " + ex.getMessage());
                }
            }
        });
    }


    // --- Inventory (Admin Only) ---
    private void showInventory(Stage stage, String role) {
         if (!role.equals("Admin")) {
            showError("Access Denied: Inventory is only available for Admins.");
            showDashboard(stage, role); // Go back
            return;
        }

         HBox topBar = createTopBar("Inventory Management", stage, role);
         HBox searchAddRow = createSearchAddBar("Search inventory by name or ID...", "Add a new inventory item", e -> showAddInventoryItemDialog());
         TextField searchField = getSearchFieldFromBar(searchAddRow);

         TableView<InventoryItem> table = createInventoryTable();
         VBox root = createMainScreenLayout(topBar, searchAddRow, table);

         // Logic
         refreshInventoryList();
         if (searchField != null) {
             searchField.textProperty().addListener((obs, oldVal, newVal) -> filterInventoryList(newVal));
         }

         stage.setTitle("Hospital System - Inventory");
         stage.setScene(new Scene(root, 1100, 600));
    }

     private TableView<InventoryItem> createInventoryTable() {
        TableView<InventoryItem> table = new TableView<>(inventoryList);
        table.setPrefHeight(420);

        TableColumn<InventoryItem, String> idCol = new TableColumn<>("Item ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        idCol.setPrefWidth(100);

        TableColumn<InventoryItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(250);

        TableColumn<InventoryItem, Integer> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setPrefWidth(100);

        TableColumn<InventoryItem, Double> priceCol = new TableColumn<>("Unit Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        priceCol.setPrefWidth(120);
         // Format as currency
        priceCol.setCellFactory(tc -> new TableCell<InventoryItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });

        TableColumn<InventoryItem, Void> actionsCol = createInventoryActionsColumn();
        actionsCol.setPrefWidth(250); // Adjust width as needed

        table.getColumns().setAll(idCol, nameCol, quantityCol, priceCol, actionsCol);
        table.setPlaceholder(new Label("No inventory items found"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        table.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        VBox.setVgrow(table, Priority.ALWAYS);
        return table;
    }

     private TableColumn<InventoryItem, Void> createInventoryActionsColumn() {
        TableColumn<InventoryItem, Void> actionsCol = new TableColumn<>("Actions");
        actionsCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final Button addStockBtn = new Button("+ Stock");
            private final Button removeStockBtn = new Button("- Stock");
             {
                editBtn.setOnAction(e -> showEditInventoryItemDialog(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(e -> deleteInventoryItemAction(getTableView().getItems().get(getIndex())));
                addStockBtn.setOnAction(e -> showUpdateStockDialog(getTableView().getItems().get(getIndex()), true)); // true for add
                removeStockBtn.setOnAction(e -> showUpdateStockDialog(getTableView().getItems().get(getIndex()), false)); // false for remove

                editBtn.setStyle(BUTTON_STYLE_EDIT);
                deleteBtn.setStyle(BUTTON_STYLE_DELETE);
                addStockBtn.setStyle(BUTTON_STYLE_ACTION1); // Greenish
                removeStockBtn.setStyle(BUTTON_STYLE_ACTION2); // Bluish
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    InventoryItem invItem = getTableView().getItems().get(getIndex());
                    HBox box = new HBox(6, editBtn, deleteBtn, addStockBtn, removeStockBtn);
                    // Disable remove stock if quantity is 0
                    removeStockBtn.setDisable(invItem.getQuantity() <= 0);
                    setGraphic(box);
                }
            }
        });
        return actionsCol;
    }


    private void refreshInventoryList() {
        try {
            inventoryList.setAll(controller.getAllInventoryItems());
        } catch (Exception ex) {
            showError("Failed to load inventory: " + ex.getMessage());
            inventoryList.clear();
        }
    }

     private void filterInventoryList(String searchText) {
        try {
            List<InventoryItem> allItems = controller.getAllInventoryItems();
            if (searchText == null || searchText.isEmpty()) {
                inventoryList.setAll(allItems);
            } else {
                String lowerCaseFilter = searchText.toLowerCase();
                inventoryList.setAll(allItems.stream()
                        .filter(i -> i.getName().toLowerCase().contains(lowerCaseFilter) ||
                                     i.getItemId().toLowerCase().contains(lowerCaseFilter))
                        .collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            showError("Failed to filter inventory: " + ex.getMessage());
        }
    }

     private void showAddInventoryItemDialog() {
        Dialog<InventoryItem> dialog = new Dialog<>();
        dialog.setTitle("Add Inventory Item");
        dialog.setHeaderText("Enter New Item Details");

        TextField nameField = new TextField(); nameField.setPromptText("Item Name");
        TextField quantityField = new TextField(); quantityField.setPromptText("Initial Quantity");
        TextField priceField = new TextField(); priceField.setPromptText("Unit Price (e.g., 5.99)");

        // Numeric validation
        quantityField.textProperty().addListener((obs, ov, nv) -> { if (!nv.matches("\\d*")) quantityField.setText(ov); });
        priceField.textProperty().addListener((obs, ov, nv) -> { if (!nv.matches("\\d*([.]\\d{0,2})?")) priceField.setText(ov); });

        VBox vbox = new VBox(10, new Label("Name:"), nameField, new Label("Quantity:"), quantityField, new Label("Unit Price ($):"), priceField);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(true);
         Runnable updateOkButtonState = () -> {
             boolean disabled = nameField.getText().trim().isEmpty() ||
                                quantityField.getText().trim().isEmpty() ||
                                priceField.getText().trim().isEmpty();
             try {
                 if (!disabled) {
                     Integer.parseInt(quantityField.getText());
                     Double.parseDouble(priceField.getText());
                 }
             } catch (NumberFormatException e) {
                 disabled = true;
             }
             okButton.setDisable(disabled);
        };
        nameField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        quantityField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        priceField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String name = nameField.getText().trim();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    String id = "INV" + (controller.getAllInventoryItems().size() + 101); // Simple ID
                    return new InventoryItem(id, name, quantity, price);
                } catch (NumberFormatException e) {
                    showError("Invalid number format for quantity or price.");
                    return null;
                } catch (IllegalArgumentException e) {
                    showError("Invalid input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<InventoryItem> result = dialog.showAndWait();
        result.ifPresent(item -> {
            try {
                controller.addInventoryItem(item);
                refreshInventoryList();
            } catch (Exception ex) {
                showError("Failed to add inventory item: " + ex.getMessage());
            }
        });
    }

     private void showEditInventoryItemDialog(InventoryItem item) {
        Dialog<InventoryItem> dialog = new Dialog<>();
        dialog.setTitle("Edit Inventory Item");
        dialog.setHeaderText("Edit Details for Item ID: " + item.getItemId());

        TextField nameField = new TextField(item.getName());
        TextField priceField = new TextField(String.format("%.2f", item.getUnitPrice()));

        // Numeric validation
        priceField.textProperty().addListener((obs, ov, nv) -> { if (!nv.matches("\\d*([.]\\d{0,2})?")) priceField.setText(ov); });

        VBox vbox = new VBox(10, new Label("Name:"), nameField, new Label("Unit Price ($):"), priceField);
        vbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Enable OK only when valid
        Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(false); // Start enabled as fields are pre-filled
         Runnable updateOkButtonState = () -> {
             boolean disabled = nameField.getText().trim().isEmpty() ||
                                priceField.getText().trim().isEmpty();
             try {
                 if (!disabled) {
                     Double.parseDouble(priceField.getText());
                 }
             } catch (NumberFormatException e) {
                 disabled = true;
             }
             okButton.setDisable(disabled);
        };
        nameField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());
        priceField.textProperty().addListener((obs, o, n) -> updateOkButtonState.run());


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    String newName = nameField.getText().trim();
                    double newPrice = Double.parseDouble(priceField.getText());

                    // Apply changes locally first for validation by setters
                    item.setName(newName);
                    item.setUnitPrice(newPrice);

                    return item; // Return the modified item
                } catch (NumberFormatException e) {
                    showError("Invalid number format for price.");
                    return null;
                } catch (IllegalArgumentException e) {
                    showError("Invalid input: " + e.getMessage());
                    return null;
                }
            }
            return null;
        });

        Optional<InventoryItem> result = dialog.showAndWait();
        result.ifPresent(updatedItem -> {
            try {
                controller.updateInventoryItem(updatedItem);
                refreshInventoryList();
            } catch (Exception ex) {
                showError("Failed to update inventory item: " + ex.getMessage());
                refreshInventoryList(); // Refresh to show original state
            }
        });
    }

     private void showUpdateStockDialog(InventoryItem item, boolean isAdding) {
         String action = isAdding ? "Add" : "Remove";
         Dialog<Integer> dialog = new Dialog<>();
         dialog.setTitle(action + " Stock");
         dialog.setHeaderText(action + " Stock for " + item.getName() + " (Current: " + item.getQuantity() + ")");

         TextField amountField = new TextField();
         amountField.setPromptText("Enter amount");
         // Integer validation
         amountField.textProperty().addListener((obs, ov, nv) -> { if (!nv.matches("\\d*")) amountField.setText(ov); });

         VBox content = new VBox(10, dialog.getDialogPane().getContent(), amountField);
         dialog.getDialogPane().setContent(content);
         dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

         // Enable OK only when valid integer > 0 entered
         Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
         okButton.setDisable(true);
         amountField.textProperty().addListener((obs, ov, nv) -> {
             boolean disabled = true;
             try {
                 int amount = Integer.parseInt(nv);
                 if (amount > 0) {
                     if (!isAdding && amount > item.getQuantity()) {
                         // Handled by logic later, but keep button enabled
                          disabled = false;
                     } else {
                          disabled = false;
                     }
                 }
             } catch (NumberFormatException e) {
                 // Keep disabled
             }
             okButton.setDisable(disabled);
         });

         dialog.setResultConverter(dialogButton -> {
             if (dialogButton == ButtonType.OK) {
                 try {
                     return Integer.parseInt(amountField.getText());
                 } catch (NumberFormatException e) {
                     // Should not happen due to validation, but handle defensively
                     showError("Invalid amount entered.");
                     return null;
                 }
             }
             return null;
         });

         Optional<Integer> result = dialog.showAndWait();
         result.ifPresent(amount -> {
             try {
                 if (isAdding) {
                     item.addStock(amount);
                 } else {
                     item.removeStock(amount);
                 }
                 controller.updateInventoryItem(item); // Persist change
                 refreshInventoryList();
             } catch (IllegalArgumentException e) {
                 showError("Failed to update stock: " + e.getMessage());
                 refreshInventoryList(); // Refresh view
             } catch (Exception ex) {
                  showError("An unexpected error occurred: " + ex.getMessage());
                  refreshInventoryList();
             }
         });
     }


    private void deleteInventoryItemAction(InventoryItem item) {
         Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete inventory item '" + item.getName() + "' (ID: " + item.getItemId() + ")?",
                ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    boolean deleted = controller.deleteInventoryItem(item.getItemId());
                    if (deleted) {
                        refreshInventoryList();
                    } else {
                        showError("Inventory item could not be found or deleted.");
                    }
                } catch (Exception ex) {
                    // Catch potential dependencies if inventory is linked elsewhere
                    showError("Failed to delete inventory item: " + ex.getMessage());
                }
            }
        });
    }


    // --- Utility ---
    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        // Ensure dialog is shown on the JavaFX Application Thread
        if (javafx.application.Platform.isFxApplicationThread()) {
             alert.showAndWait();
        } else {
             javafx.application.Platform.runLater(alert::showAndWait);
        }
    }

    // Main method to launch the JavaFX application.
    public static void main(String[] args) {
        launch(args);
    }
} 