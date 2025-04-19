package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of AppointmentService.
 */
public class AppointmentServiceImpl implements AppointmentService {
    private final Map<String, Appointment> appointmentMap = new HashMap<>();

    /**
     * Schedules a new appointment. Throws if appointment is null or already exists.
     */
    @Override
    public void scheduleAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");
        if (appointmentMap.containsKey(appointment.getAppointmentId())) throw new IllegalArgumentException("Appointment already exists");
        appointmentMap.put(appointment.getAppointmentId(), appointment);
    }

    /**
     * Finds an appointment by its unique ID.
     */
    @Override
    public Appointment findAppointmentById(String id) {
        return appointmentMap.get(id);
    }

    /**
     * Returns a list of all scheduled appointments.
     */
    @Override
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentMap.values());
    }

    /**
     * Updates an existing appointment. Throws if appointment is null or does not exist.
     */
    @Override
    public void updateAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");
        if (!appointmentMap.containsKey(appointment.getAppointmentId())) throw new IllegalArgumentException("Appointment does not exist");
        appointmentMap.put(appointment.getAppointmentId(), appointment);
    }

    /**
     * Cancels an appointment by its ID. Returns true if cancelled, false if not found.
     */
    @Override
    public boolean cancelAppointment(String id) {
        Appointment appt = appointmentMap.get(id);
        if (appt == null) return false;
        if (appt.getStatus() == Appointment.Status.CANCELLED) return false;
        appt.cancel();
        return true;
    }

    /**
     * Deletes an appointment by its ID. Returns true if deleted, false if not found.
     */
    @Override
    public boolean deleteAppointment(String id) {
        return appointmentMap.remove(id) != null;
    }
} 