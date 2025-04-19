package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceImplTest {
    private AppointmentService service;
    private Patient patient;
    private Appointment appt1;
    private Appointment appt2;

    @BeforeEach
    void setUp() {
        service = new AppointmentServiceImpl();
        patient = new Patient("P001", "Alice", LocalDate.of(1990, 1, 1));
        appt1 = new Appointment("A001", patient, LocalDateTime.now().plusDays(1), "Checkup");
        appt2 = new Appointment("A002", patient, LocalDateTime.now().plusDays(2), "Consultation");
    }

    @Test
    void scheduleAppointmentWorks() { // Tests scheduling a new appointment
        service.scheduleAppointment(appt1);
        assertEquals(appt1, service.findAppointmentById("A001"));
    }

    @Test
    void scheduleNullAppointmentThrowsException() { // Tests that scheduling null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.scheduleAppointment(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void scheduleDuplicateAppointmentThrowsException() { // Tests that scheduling duplicate appointment throws exception
        service.scheduleAppointment(appt1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.scheduleAppointment(appt1));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void findAppointmentByIdReturnsCorrectAppointment() { // Tests finding an appointment by ID
        service.scheduleAppointment(appt1);
        service.scheduleAppointment(appt2);
        assertEquals(appt2, service.findAppointmentById("A002"));
    }

    @Test
    void findAppointmentByIdReturnsNullIfNotFound() { // Tests finding a non-existent appointment returns null
        assertNull(service.findAppointmentById("A999"));
    }

    @Test
    void getAllAppointmentsReturnsAllScheduled() { // Tests getting all scheduled appointments
        service.scheduleAppointment(appt1);
        service.scheduleAppointment(appt2);
        List<Appointment> appts = service.getAllAppointments();
        assertEquals(2, appts.size());
        assertTrue(appts.contains(appt1));
        assertTrue(appts.contains(appt2));
    }

    @Test
    void updateAppointmentWorks() { // Tests updating an existing appointment
        service.scheduleAppointment(appt1);
        Appointment updated = new Appointment("A001", patient, LocalDateTime.now().plusDays(3), "Updated");
        service.updateAppointment(updated);
        assertEquals("Updated", service.findAppointmentById("A001").getType());
    }

    @Test
    void updateNullAppointmentThrowsException() { // Tests that updating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateAppointment(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void updateNonExistentAppointmentThrowsException() { // Tests that updating a non-existent appointment throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateAppointment(appt1));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void cancelAppointmentWorks() { // Tests cancelling an existing appointment
        service.scheduleAppointment(appt1);
        assertTrue(service.cancelAppointment("A001"));
        assertEquals(Appointment.Status.CANCELLED, service.findAppointmentById("A001").getStatus());
    }

    @Test
    void cancelNonExistentAppointmentReturnsFalse() { // Tests cancelling a non-existent appointment returns false
        assertFalse(service.cancelAppointment("A999"));
    }

    @Test
    void cancelAlreadyCancelledAppointmentReturnsFalse() { // Tests cancelling an already cancelled appointment returns false
        service.scheduleAppointment(appt1);
        service.cancelAppointment("A001");
        assertFalse(service.cancelAppointment("A001"));
    }

    @Test
    void deleteAppointmentWorks() { // Tests deleting an existing appointment
        service.scheduleAppointment(appt1);
        assertTrue(service.deleteAppointment("A001"));
        assertNull(service.findAppointmentById("A001"));
    }

    @Test
    void deleteNonExistentAppointmentReturnsFalse() { // Tests deleting a non-existent appointment returns false
        assertFalse(service.deleteAppointment("A999"));
    }
} 