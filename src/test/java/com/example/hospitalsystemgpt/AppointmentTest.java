package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {
    private Patient patient;
    private LocalDateTime futureDateTime;

    @BeforeEach
    void setUp() {
        patient = new Patient("P001", "John Doe", LocalDate.of(1990, 1, 1));
        futureDateTime = LocalDateTime.now().plusDays(1);
    }

    
    @Test 
    void validAppointmentIsCreated() { // Tests that a valid appointment is created with correct fields and default status
        Appointment appt = new Appointment("A001", patient, futureDateTime, "Checkup");
        assertEquals("A001", appt.getAppointmentId());
        assertEquals(patient, appt.getPatient());
        assertEquals(futureDateTime, appt.getDateTime());
        assertEquals("Checkup", appt.getType());
        assertEquals(Appointment.Status.SCHEDULED, appt.getStatus());
    }

    @Test
    void nullIdThrowsException() { // Tests that constructing with a null ID throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment(null, patient, futureDateTime, "Checkup"));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void blankIdThrowsException() { // Tests that constructing with a blank ID throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("   ", patient, futureDateTime, "Checkup"));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void nullPatientThrowsException() { // Tests that constructing with a null patient throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A002", null, futureDateTime, "Checkup"));
        assertTrue(ex.getMessage().contains("Patient"));
    }

    @Test
    void nullDateTimeThrowsException() { // Tests that constructing with a null date/time throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A003", patient, null, "Checkup"));
        assertTrue(ex.getMessage().contains("date/time"));
    }

    @Test
    void pastDateTimeThrowsException() { // Tests that constructing with a past date/time throws an exception
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A004", patient, past, "Checkup"));
        assertTrue(ex.getMessage().contains("date/time"));
    }

    @Test
    void nullTypeThrowsException() { // Tests that constructing with a null type throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A005", patient, futureDateTime, null));
        assertTrue(ex.getMessage().contains("Type"));
    }

    @Test
    void blankTypeThrowsException() { // Tests that constructing with a blank type throws an exception
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
            new Appointment("A006", patient, futureDateTime, "   "));
        assertTrue(ex.getMessage().contains("Type"));
    }

    @Test
    void completeScheduledAppointment() { // Tests that a scheduled appointment can be completed
        Appointment appt = new Appointment("A007", patient, futureDateTime, "Consultation");
        appt.complete();
        assertEquals(Appointment.Status.COMPLETED, appt.getStatus());
    }

    @Test
    void completeNonScheduledThrowsException() { // Tests that completing a non-scheduled appointment throws an exception
        Appointment appt = new Appointment("A008", patient, futureDateTime, "Consultation");
        appt.cancel();
        Exception ex = assertThrows(IllegalStateException.class, appt::complete);
        assertTrue(ex.getMessage().contains("scheduled"));
    }

    @Test
    void cancelScheduledAppointment() { // Tests that a scheduled appointment can be cancelled
        Appointment appt = new Appointment("A009", patient, futureDateTime, "Consultation");
        appt.cancel();
        assertEquals(Appointment.Status.CANCELLED, appt.getStatus());
    }

    @Test
    void cancelNonScheduledThrowsException() { // Tests that cancelling a non-scheduled appointment throws an exception
        Appointment appt = new Appointment("A010", patient, futureDateTime, "Consultation");
        appt.complete();
        Exception ex = assertThrows(IllegalStateException.class, appt::cancel);
        assertTrue(ex.getMessage().contains("scheduled"));
    }

    @Test
    void equalsAndHashCodeBasedOnId() { // Tests that two appointments with the same ID are equal and have the same hash code
        Appointment appt1 = new Appointment("A011", patient, futureDateTime, "Checkup");
        Appointment appt2 = new Appointment("A011", patient, futureDateTime, "Checkup");
        assertEquals(appt1, appt2);
        assertEquals(appt1.hashCode(), appt2.hashCode());
    }

    @Test
    void notEqualsForDifferentId() { // Tests that two appointments with different IDs are not equal
        Appointment appt1 = new Appointment("A012", patient, futureDateTime, "Checkup");
        Appointment appt2 = new Appointment("A013", patient, futureDateTime, "Checkup");
        assertNotEquals(appt1, appt2);
    }

    @Test
    void equalsReturnsTrueForSameObject() { // Tests that equals returns true when comparing the same object (this == o)
        Appointment appt = new Appointment("A014", patient, futureDateTime, "Checkup");
        assertEquals(appt, appt);
    }

    @Test
    void equalsReturnsFalseForNull() { // Tests that equals returns false when comparing to null (o == null)
        Appointment appt = new Appointment("A015", patient, futureDateTime, "Checkup");
        assertFalse(appt.equals(null));
    }

    @Test
    void equalsReturnsFalseForDifferentClass() { // Tests that equals returns false when comparing to an object of a different class
        Appointment appt = new Appointment("A016", patient, futureDateTime, "Checkup");
        assertFalse(appt.equals("not an appointment"));
    }
} 