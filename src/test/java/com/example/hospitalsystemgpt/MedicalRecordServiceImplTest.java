package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordServiceImplTest {
    private MedicalRecordService service;
    private Patient patient;
    private Appointment appointment;
    private MedicalRecord record1;
    private MedicalRecord record2;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        service = new MedicalRecordServiceImpl();
        patient = new Patient("P001", "Alice", LocalDate.of(1990, 1, 1));
        appointment = new Appointment("A001", patient, LocalDateTime.now().plusDays(1), "Checkup");
        today = LocalDate.now();
        record1 = new MedicalRecord("MR001", patient, appointment, "Flu", "Rest", today);
        record2 = new MedicalRecord("MR002", patient, appointment, "Cold", "Hydration", today);
    }

    @Test
    void addMedicalRecordWorks() { // Tests adding a new medical record
        service.addMedicalRecord(record1);
        assertEquals(record1, service.findMedicalRecordById("MR001"));
    }

    @Test
    void addNullMedicalRecordThrowsException() { // Tests that adding null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.addMedicalRecord(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void addDuplicateMedicalRecordThrowsException() { // Tests that adding duplicate record throws exception
        service.addMedicalRecord(record1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.addMedicalRecord(record1));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void findMedicalRecordByIdReturnsCorrectRecord() { // Tests finding a record by ID
        service.addMedicalRecord(record1);
        service.addMedicalRecord(record2);
        assertEquals(record2, service.findMedicalRecordById("MR002"));
    }

    @Test
    void findMedicalRecordByIdReturnsNullIfNotFound() { // Tests finding a non-existent record returns null
        assertNull(service.findMedicalRecordById("MR999"));
    }

    @Test
    void getAllMedicalRecordsReturnsAllAdded() { // Tests getting all added records
        service.addMedicalRecord(record1);
        service.addMedicalRecord(record2);
        List<MedicalRecord> records = service.getAllMedicalRecords();
        assertEquals(2, records.size());
        assertTrue(records.contains(record1));
        assertTrue(records.contains(record2));
    }

    @Test
    void updateMedicalRecordWorks() { // Tests updating an existing record
        service.addMedicalRecord(record1);
        MedicalRecord updated = new MedicalRecord("MR001", patient, appointment, "Updated", "Updated notes", today);
        service.updateMedicalRecord(updated);
        assertEquals("Updated", service.findMedicalRecordById("MR001").getDiagnosis());
    }

    @Test
    void updateNullMedicalRecordThrowsException() { // Tests that updating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateMedicalRecord(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void updateNonExistentMedicalRecordThrowsException() { // Tests that updating a non-existent record throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateMedicalRecord(record1));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void deleteMedicalRecordWorks() { // Tests deleting an existing record
        service.addMedicalRecord(record1);
        assertTrue(service.deleteMedicalRecord("MR001"));
        assertNull(service.findMedicalRecordById("MR001"));
    }

    @Test
    void deleteNonExistentMedicalRecordReturnsFalse() { // Tests deleting a non-existent record returns false
        assertFalse(service.deleteMedicalRecord("MR999"));
    }
} 