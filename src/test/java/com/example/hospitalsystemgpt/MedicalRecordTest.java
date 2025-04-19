package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecordTest {
    private Patient patient;
    private Appointment appointment;
    private LocalDate today;

    @BeforeEach
    void setUp() {
        patient = new Patient("P001", "John Doe", LocalDate.of(1990, 1, 1));
        appointment = new Appointment("A001", patient, LocalDateTime.now().plusDays(1), "Checkup");
        today = LocalDate.now();
    }

    @Test
    void validMedicalRecordIsCreated() { // Tests valid construction and all getter methods
        MedicalRecord record = new MedicalRecord("MR001", patient, appointment, "Flu", "Rest and hydration", today);
        assertEquals("MR001", record.getRecordId());
        assertEquals(patient, record.getPatient());
        assertEquals(appointment, record.getAppointment());
        assertEquals("Flu", record.getDiagnosis());
        assertEquals("Rest and hydration", record.getNotes());
        assertEquals(today, record.getDate());
    }

    @Test
    void nullIdThrowsException() { // Tests that null ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord(null, patient, appointment, "Flu", "Notes", today));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void blankIdThrowsException() { // Tests that blank ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("   ", patient, appointment, "Flu", "Notes", today));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void nullPatientThrowsException() { // Tests that null patient is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR002", null, appointment, "Flu", "Notes", today));
        assertTrue(ex.getMessage().contains("Patient"));
    }

    @Test
    void nullAppointmentThrowsException() { // Tests that null appointment is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR003", patient, null, "Flu", "Notes", today));
        assertTrue(ex.getMessage().contains("Appointment"));
    }

    @Test
    void nullDiagnosisThrowsException() { // Tests that null diagnosis is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR004", patient, appointment, null, "Notes", today));
        assertTrue(ex.getMessage().contains("Diagnosis"));
    }

    @Test
    void blankDiagnosisThrowsException() { // Tests that blank diagnosis is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR005", patient, appointment, "   ", "Notes", today));
        assertTrue(ex.getMessage().contains("Diagnosis"));
    }

    @Test
    void nullDateThrowsException() { // Tests that null date is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR006", patient, appointment, "Flu", "Notes", null));
        assertTrue(ex.getMessage().contains("date"));
    }

    @Test
    void futureDateThrowsException() { // Tests that a future date is rejected
        LocalDate future = LocalDate.now().plusDays(1);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new MedicalRecord("MR007", patient, appointment, "Flu", "Notes", future));
        assertTrue(ex.getMessage().contains("date"));
    }

    @Test
    void nullNotesDefaultsToEmptyString() { // Tests that null notes defaults to empty string
        MedicalRecord record = new MedicalRecord("MR008", patient, appointment, "Flu", null, today);
        assertEquals("", record.getNotes());
    }

    @Test
    void equalsAndHashCodeBasedOnId() { // Tests equals and hashCode for MedicalRecord objects
        MedicalRecord r1 = new MedicalRecord("MR009", patient, appointment, "Flu", "Notes", today);
        MedicalRecord r2 = new MedicalRecord("MR009", patient, appointment, "Other", "Other", today);
        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void notEqualsForDifferentId() { // Tests that records with different IDs are not equal
        MedicalRecord r1 = new MedicalRecord("MR010", patient, appointment, "Flu", "Notes", today);
        MedicalRecord r2 = new MedicalRecord("MR011", patient, appointment, "Flu", "Notes", today);
        assertNotEquals(r1, r2);
    }

    @Test
    void equalsReturnsTrueForSameObject() { // Tests equals returns true when comparing to self
        MedicalRecord r = new MedicalRecord("MR012", patient, appointment, "Flu", "Notes", today);
        assertEquals(r, r);
    }

    @Test
    void equalsReturnsFalseForNull() { // Tests equals returns false when comparing to null
        MedicalRecord r = new MedicalRecord("MR013", patient, appointment, "Flu", "Notes", today);
        assertNotEquals(r, null);
    }

    @Test
    void equalsReturnsFalseForDifferentClass() { // Tests equals returns false when comparing to different class
        MedicalRecord r = new MedicalRecord("MR014", patient, appointment, "Flu", "Notes", today);
        assertNotEquals(r, "not a record");
    }
} 