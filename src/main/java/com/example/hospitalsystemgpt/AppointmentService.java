package com.example.hospitalsystemgpt;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing appointments.
 */
public interface AppointmentService {
    /**
     * Schedules a new appointment.
     * @param appointment the appointment to schedule
     * @throws IllegalArgumentException if the appointment is null or already exists
     */
    void scheduleAppointment(Appointment appointment);

    /**
     * Finds an appointment by its unique ID.
     * @param id the appointment ID
     * @return the Appointment if found, or null if not found
     */
    Appointment findAppointmentById(String id);

    /**
     * Returns a list of all scheduled appointments.
     * @return list of appointments
     */
    List<Appointment> getAllAppointments();

    /**
     * Updates an existing appointment.
     * @param appointment the appointment with updated information
     * @throws IllegalArgumentException if the appointment is null or does not exist
     */
    void updateAppointment(Appointment appointment);

    /**
     * Cancels an appointment by its ID.
     * @param id the appointment ID
     * @return true if the appointment was cancelled, false if not found
     */
    boolean cancelAppointment(String id);

    /**
     * Deletes an appointment by its ID.
     * @param id the appointment ID
     * @return true if the appointment was deleted, false if not found
     */
    boolean deleteAppointment(String id);
} 