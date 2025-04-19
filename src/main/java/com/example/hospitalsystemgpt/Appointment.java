package com.example.hospitalsystemgpt;

import java.time.LocalDateTime;
import java.util.Objects;

public class Appointment {
    public enum Status { SCHEDULED, COMPLETED, CANCELLED }

    private final String id;
    private final Patient patient;
    private final LocalDateTime dateTime;
    private final String type;
    private Status status;

    /**
     * Constructs an Appointment with the given id, patient, datetime, and type.
     * Validates that id, patient, datetime, and type are not null/blank, and datetime is not in the past.
     * Sets status to SCHEDULED by default.
     */
    public Appointment(String id, Patient patient, LocalDateTime dateTime, String type) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank");
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (dateTime == null || dateTime.isBefore(LocalDateTime.now())) throw new IllegalArgumentException("Invalid appointment date/time");
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Type cannot be null or blank");
        this.id = id;
        this.patient = patient;
        this.dateTime = dateTime;
        this.type = type;
        this.status = Status.SCHEDULED;
    }

    public String getAppointmentId() { return id; }
    public Patient getPatient() { return patient; }
    public LocalDateTime getDateTime() { return dateTime; }
    public String getType() { return type; }
    public Status getStatus() { return status; }

    /**
     * Marks the appointment as completed.
     */
    public void complete() {
        if (status != Status.SCHEDULED) throw new IllegalStateException("Only scheduled appointments can be completed");
        status = Status.COMPLETED;
    }

    /**
     * Cancels the appointment.
     */
    public void cancel() {
        if (status != Status.SCHEDULED) throw new IllegalStateException("Only scheduled appointments can be cancelled");
        status = Status.CANCELLED;
    }

    /**
     * Checks equality based on appointment ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 