package com.example.hospitalsystemgpt;

/**
 * Controller for coordinating hospital workflows between services and the GUI.
 */
public class HospitalController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final MedicalRecordService medicalRecordService;
    private final BillingService billingService;
    private final InventoryService inventoryService;

    /**
     * Constructs a HospitalController with all service dependencies.
     */
    public HospitalController(PatientService patientService,
                             AppointmentService appointmentService,
                             MedicalRecordService medicalRecordService,
                             BillingService billingService,
                             InventoryService inventoryService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.medicalRecordService = medicalRecordService;
        this.billingService = billingService;
        this.inventoryService = inventoryService;
    }

    /** Registers a new patient. */
    public void registerPatient(Patient patient) {
        if (patientService == null) throw new IllegalStateException("PatientService not initialized");
        patientService.registerPatient(patient);
    }

    /** Finds a patient by ID. */
    public Patient findPatientById(String id) {
        if (patientService == null) throw new IllegalStateException("PatientService not initialized");
        return patientService.findPatientById(id);
    }

    /** Gets all patients. */
    public java.util.List<Patient> getAllPatients() {
        if (patientService == null) throw new IllegalStateException("PatientService not initialized");
        return patientService.getAllPatients();
    }

    /** Updates a patient. */
    public void updatePatient(Patient patient) {
        if (patientService == null) throw new IllegalStateException("PatientService not initialized");
        patientService.updatePatient(patient);
    }

    /** Deletes a patient by ID. */
    public boolean deletePatient(String id) {
        if (patientService == null) throw new IllegalStateException("PatientService not initialized");
        return patientService.deletePatient(id);
    }

    /** Schedules a new appointment. */
    public void scheduleAppointment(Appointment appointment) {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        appointmentService.scheduleAppointment(appointment);
    }

    /** Finds an appointment by ID. */
    public Appointment findAppointmentById(String id) {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        return appointmentService.findAppointmentById(id);
    }

    /** Gets all appointments. */
    public java.util.List<Appointment> getAllAppointments() {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        return appointmentService.getAllAppointments();
    }

    /** Updates an appointment. */
    public void updateAppointment(Appointment appointment) {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        appointmentService.updateAppointment(appointment);
    }

    /** Cancels an appointment by ID. */
    public boolean cancelAppointment(String id) {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        return appointmentService.cancelAppointment(id);
    }

    /** Deletes an appointment by ID. */
    public boolean deleteAppointment(String id) {
        if (appointmentService == null) throw new IllegalStateException("AppointmentService not initialized");
        return appointmentService.deleteAppointment(id);
    }

    /** Adds a new medical record. */
    public void addMedicalRecord(MedicalRecord record) {
        if (medicalRecordService == null) throw new IllegalStateException("MedicalRecordService not initialized");
        medicalRecordService.addMedicalRecord(record);
    }

    /** Finds a medical record by ID. */
    public MedicalRecord findMedicalRecordById(String id) {
        if (medicalRecordService == null) throw new IllegalStateException("MedicalRecordService not initialized");
        return medicalRecordService.findMedicalRecordById(id);
    }

    /** Gets all medical records. */
    public java.util.List<MedicalRecord> getAllMedicalRecords() {
        if (medicalRecordService == null) throw new IllegalStateException("MedicalRecordService not initialized");
        return medicalRecordService.getAllMedicalRecords();
    }

    /** Updates a medical record. */
    public void updateMedicalRecord(MedicalRecord record) {
        if (medicalRecordService == null) throw new IllegalStateException("MedicalRecordService not initialized");
        medicalRecordService.updateMedicalRecord(record);
    }

    /** Deletes a medical record by ID. */
    public boolean deleteMedicalRecord(String id) {
        if (medicalRecordService == null) throw new IllegalStateException("MedicalRecordService not initialized");
        return medicalRecordService.deleteMedicalRecord(id);
    }

    /** Creates a new bill. */
    public void createBill(Bill bill) {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        billingService.createBill(bill);
    }

    /** Finds a bill by ID. */
    public Bill findBillById(String id) {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        return billingService.findBillById(id);
    }

    /** Gets all bills. */
    public java.util.List<Bill> getAllBills() {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        return billingService.getAllBills();
    }

    /** Updates a bill. */
    public void updateBill(Bill bill) {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        billingService.updateBill(bill);
    }

    /** Marks a bill as paid. */
    public void markBillAsPaid(String billId, String paymentReference) {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        billingService.markBillAsPaid(billId, paymentReference);
    }

    /** Deletes a bill by ID. */
    public boolean deleteBill(String id) {
        if (billingService == null) throw new IllegalStateException("BillingService not initialized");
        return billingService.deleteBill(id);
    }

    /** Adds a new inventory item. */
    public void addInventoryItem(InventoryItem item) {
        if (inventoryService == null) throw new IllegalStateException("InventoryService not initialized");
        inventoryService.addInventoryItem(item);
    }

    /** Finds an inventory item by ID. */
    public InventoryItem findInventoryItemById(String id) {
        if (inventoryService == null) throw new IllegalStateException("InventoryService not initialized");
        return inventoryService.findInventoryItemById(id);
    }

    /** Gets all inventory items. */
    public java.util.List<InventoryItem> getAllInventoryItems() {
        if (inventoryService == null) throw new IllegalStateException("InventoryService not initialized");
        return inventoryService.getAllInventoryItems();
    }

    /** Updates an inventory item. */
    public void updateInventoryItem(InventoryItem item) {
        if (inventoryService == null) throw new IllegalStateException("InventoryService not initialized");
        inventoryService.updateInventoryItem(item);
    }

    /** Deletes an inventory item by ID. */
    public boolean deleteInventoryItem(String id) {
        if (inventoryService == null) throw new IllegalStateException("InventoryService not initialized");
        return inventoryService.deleteInventoryItem(id);
    }

    // Add more methods as needed for viewing, updating, deleting, and cross-service workflows.
} 