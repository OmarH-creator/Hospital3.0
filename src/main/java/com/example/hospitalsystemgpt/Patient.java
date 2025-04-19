package com.example.hospitalsystemgpt;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Patient {
    private final String id;
    private String name;
    private final LocalDate dateOfBirth;
    private boolean admitted;

    /**
     * Constructs a Patient with the given id, name, and date of birth.
     * Validates that id and name are not null/blank and dateOfBirth is not null or in the future.
     * Sets admitted to false by default.
     */
    public Patient(String id, String name, LocalDate dateOfBirth) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid date of birth");
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.admitted = false;
    }

    /**
     * Returns the patient's unique ID.
     */
    public String getPatientId() { return id; }

    /**
     * Returns the patient's name.
     *
     *
     */
    public String getPatientName() { return name; }

    /**
     * Sets the patient's name after validating it is not null or blank.
     */
    public void setPatientName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name;
    }

    /**
     * Returns the patient's date of birth.
     */
    public LocalDate getDateOfBirth() { return dateOfBirth; }

    /**
     * Returns true if the patient is currently admitted, false otherwise.
     */
    public boolean isPatientAdmitted() { return admitted; }

    /**
     * Calculates and returns the patient's age in years based on the date of birth.
     */
    public int getPatientAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /**
     * Marks the patient as admitted (sets admitted to true).
     */
    public void admitPatient() { admitted = true; }

    /**
     * Marks the patient as discharged (sets admitted to false).
     */
    public void dischargePatient() { admitted = false; }

    /**
     * Checks equality based on patient ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id);
    }

    /**
     * Returns the hash code for the patient, based on ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 