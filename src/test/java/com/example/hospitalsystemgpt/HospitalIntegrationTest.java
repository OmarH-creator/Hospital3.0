package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HospitalIntegrationTest {
    private PatientService patientService;
    private AppointmentService appointmentService;
    private MedicalRecordService medicalRecordService;
    private BillingService billingService;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        patientService = new PatientServiceImpl();
        appointmentService = new AppointmentServiceImpl();
        medicalRecordService = new MedicalRecordServiceImpl();
        billingService = new BillingServiceImpl();
        inventoryService = new InventoryServiceImpl();
    }

    @Test
    void testPatientRegistrationAndAppointmentScheduling() {
        // Register a new patient
        Patient patient = new Patient("p1", "John Doe", LocalDate.now().minusYears(30));
        patientService.registerPatient(patient);
        assertNotNull(patientService.findPatientById(patient.getPatientId()));

        // Schedule an appointment for the patient
        Appointment appointment = new Appointment("a1", patient, LocalDateTime.now().plusDays(1), "Checkup");
        appointmentService.scheduleAppointment(appointment);
        assertNotNull(appointmentService.findAppointmentById(appointment.getAppointmentId()));
        assertEquals(patient, appointmentService.findAppointmentById(appointment.getAppointmentId()).getPatient());
    }

    @Test
    void testMedicalRecordCreation() {
        Patient patient = new Patient("p2", "Jane Smith", LocalDate.now().minusYears(25));
        patientService.registerPatient(patient);
        Appointment appointment = new Appointment("a2", patient, LocalDateTime.now().plusDays(2), "Consultation");
        appointmentService.scheduleAppointment(appointment);
        MedicalRecord record = new MedicalRecord("mr1", patient, appointment, "Flu", "Rest and hydration", LocalDate.now());
        medicalRecordService.addMedicalRecord(record);
        assertNotNull(medicalRecordService.findMedicalRecordById("mr1"));
        assertEquals(patient, medicalRecordService.findMedicalRecordById("mr1").getPatient());
        assertEquals(appointment, medicalRecordService.findMedicalRecordById("mr1").getAppointment());
    }

    @Test
    void testBillingWorkflow() {
        Patient patient = new Patient("p3", "Alice Brown", LocalDate.now().minusYears(40));
        patientService.registerPatient(patient);
        Bill bill = new Bill("b1", patient);
        Bill.LineItem item1 = new Bill.LineItem("Consultation", 100.0);
        Bill.LineItem item2 = new Bill.LineItem("Medication", 50.0);
        bill.addLineItem(item1);
        bill.addLineItem(item2);
        billingService.createBill(bill);
        assertNotNull(billingService.findBillById("b1"));
        assertEquals(150.0, billingService.findBillById("b1").getTotalAmount());
        billingService.markBillAsPaid("b1", "PAY123");
        assertEquals(Bill.Status.PAID, billingService.findBillById("b1").getStatus());
        assertEquals("PAY123", billingService.findBillById("b1").getPaymentReference());
    }

    @Test
    void testInventoryManagementWorkflow() {
        InventoryItem item = new InventoryItem("i1", "Bandages", 100, 2.5);
        inventoryService.addInventoryItem(item);
        assertNotNull(inventoryService.findInventoryItemById("i1"));
        item.addStock(50);
        inventoryService.updateInventoryItem(item);
        assertEquals(150, inventoryService.findInventoryItemById("i1").getQuantity());
        item.removeStock(30);
        inventoryService.updateInventoryItem(item);
        assertEquals(120, inventoryService.findInventoryItemById("i1").getQuantity());
        assertTrue(inventoryService.deleteInventoryItem("i1"));
        assertNull(inventoryService.findInventoryItemById("i1"));
    }
}
