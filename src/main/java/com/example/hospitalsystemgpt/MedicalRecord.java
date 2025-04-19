package com.example.hospitalsystemgpt;

import java.time.LocalDate;
import java.util.Objects;

public class MedicalRecord {
    private final String id;
    private final Patient patient;
    private final Appointment appointment;
    private final String diagnosis;
    private final String notes;
    private final LocalDate date;

    /**
     * Constructs a MedicalRecord with the given id, patient, appointment, diagnosis, notes, and date.
     * Validates that id, patient, appointment, diagnosis, and date are not null/blank, and date is not in the future.
     */
    public MedicalRecord(String id, Patient patient, Appointment appointment, String diagnosis, String notes, LocalDate date) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank");
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (appointment == null) throw new IllegalArgumentException("Appointment cannot be null");
        if (diagnosis == null || diagnosis.isBlank()) throw new IllegalArgumentException("Diagnosis cannot be null or blank");
        if (date == null || date.isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid record date");
        this.id = id;
        this.patient = patient;
        this.appointment = appointment;
        this.diagnosis = diagnosis;
        this.notes = notes == null ? "" : notes;
        this.date = date;
    }

    public String getRecordId() { return id; }
    public Patient getPatient() { return patient; }
    public Appointment getAppointment() { return appointment; }
    public String getDiagnosis() { return diagnosis; }
    public String getNotes() { return notes; }
    public LocalDate getDate() { return date; }

    /**
     * Checks equality based on record ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 