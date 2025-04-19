package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceImplTest {
    private PatientService service;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        service = new PatientServiceImpl();
        patient1 = new Patient("P001", "Alice", LocalDate.of(1990, 1, 1));
        patient2 = new Patient("P002", "Bob", LocalDate.of(1985, 5, 5));
    }

    @Test
    void registerPatientWorks() { // Tests registering a new patient
        service.registerPatient(patient1);
        assertEquals(patient1, service.findPatientById("P001"));
    }

    @Test
    void registerNullPatientThrowsException() { // Tests that registering null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.registerPatient(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void registerDuplicatePatientThrowsException() { // Tests that registering duplicate patient throws exception
        service.registerPatient(patient1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.registerPatient(patient1));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void findPatientByIdReturnsCorrectPatient() { // Tests finding a patient by ID
        service.registerPatient(patient1);
        service.registerPatient(patient2);
        assertEquals(patient2, service.findPatientById("P002"));
    }

    @Test
    void findPatientByIdReturnsNullIfNotFound() { // Tests finding a non-existent patient returns null
        assertNull(service.findPatientById("P999"));
    }

    @Test
    void getAllPatientsReturnsAllRegistered() { // Tests getting all registered patients
        service.registerPatient(patient1);
        service.registerPatient(patient2);
        List<Patient> patients = service.getAllPatients();
        assertEquals(2, patients.size());
        assertTrue(patients.contains(patient1));
        assertTrue(patients.contains(patient2));
    }

    @Test
    void updatePatientWorks() { // Tests updating an existing patient
        service.registerPatient(patient1);
        Patient updated = new Patient("P001", "Alice Updated", LocalDate.of(1990, 1, 1));
        service.updatePatient(updated);
        assertEquals("Alice Updated", service.findPatientById("P001").getPatientName());
    }

    @Test
    void updateNullPatientThrowsException() { // Tests that updating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updatePatient(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void updateNonExistentPatientThrowsException() { // Tests that updating a non-existent patient throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updatePatient(patient1));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void deletePatientWorks() { // Tests deleting an existing patient
        service.registerPatient(patient1);
        assertTrue(service.deletePatient("P001"));
        assertNull(service.findPatientById("P001"));
    }

    @Test
    void deleteNonExistentPatientReturnsFalse() { // Tests deleting a non-existent patient returns false
        assertFalse(service.deletePatient("P999"));
    }
} 