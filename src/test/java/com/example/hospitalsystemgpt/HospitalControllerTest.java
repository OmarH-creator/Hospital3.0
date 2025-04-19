package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class HospitalControllerTest {
    private PatientService patientService;
    private AppointmentService appointmentService;
    private MedicalRecordService medicalRecordService;
    private BillingService billingService;
    private InventoryService inventoryService;
    private HospitalController controller;

    @BeforeEach
    void setUp() {
        patientService = mock(PatientService.class);
        appointmentService = mock(AppointmentService.class);
        medicalRecordService = mock(MedicalRecordService.class);
        billingService = mock(BillingService.class);
        inventoryService = mock(InventoryService.class);
        controller = new HospitalController(patientService, appointmentService, medicalRecordService, billingService, inventoryService);
    }

    @Test
    void registerPatientDelegatesToService() { // Tests registerPatient delegates to PatientService
        Patient patient = mock(Patient.class);
        controller.registerPatient(patient);
        verify(patientService).registerPatient(patient);
    }

    @Test
    void scheduleAppointmentDelegatesToService() { // Tests scheduleAppointment delegates to AppointmentService
        Appointment appt = mock(Appointment.class);
        controller.scheduleAppointment(appt);
        verify(appointmentService).scheduleAppointment(appt);
    }

    @Test
    void addMedicalRecordDelegatesToService() { // Tests addMedicalRecord delegates to MedicalRecordService
        MedicalRecord record = mock(MedicalRecord.class);
        controller.addMedicalRecord(record);
        verify(medicalRecordService).addMedicalRecord(record);
    }

    @Test
    void createBillDelegatesToService() { // Tests createBill delegates to BillingService
        Bill bill = mock(Bill.class);
        controller.createBill(bill);
        verify(billingService).createBill(bill);
    }

    @Test
    void markBillAsPaidDelegatesToService() { // Tests markBillAsPaid delegates to BillingService
        controller.markBillAsPaid("B001", "PAY123");
        verify(billingService).markBillAsPaid("B001", "PAY123");
    }

    @Test
    void addInventoryItemDelegatesToService() { // Tests addInventoryItem delegates to InventoryService
        InventoryItem item = mock(InventoryItem.class);
        controller.addInventoryItem(item);
        verify(inventoryService).addInventoryItem(item);
    }

    @Test
    void updateInventoryItemDelegatesToService() { // Tests updateInventoryItem delegates to InventoryService
        InventoryItem item = mock(InventoryItem.class);
        controller.updateInventoryItem(item);
        verify(inventoryService).updateInventoryItem(item);
    }

    @Test
    void deleteInventoryItemDelegatesToService() { // Tests deleteInventoryItem delegates to InventoryService
        when(inventoryService.deleteInventoryItem("I001")).thenReturn(true);
        boolean result = controller.deleteInventoryItem("I001");
        verify(inventoryService).deleteInventoryItem("I001");
        assert(result);
    }
} 