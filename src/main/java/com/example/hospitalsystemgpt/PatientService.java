package com.example.hospitalsystemgpt;

import java.util.List;

/**
 * Service interface for managing patients.
 */
public interface PatientService {
    /**
     * Registers a new patient.
     * @param patient the patient to register
     * @throws IllegalArgumentException if the patient is null or already exists
     */
    void registerPatient(Patient patient);

    /**
     * Finds a patient by their unique ID.
     * @param id the patient ID
     * @return the Patient if found, or null if not found
     */
    Patient findPatientById(String id);

    /**
     * Returns a list of all registered patients.
     * @return list of patients
     */
    List<Patient> getAllPatients();

    /**
     * Updates an existing patient.
     * @param patient the patient with updated information
     * @throws IllegalArgumentException if the patient is null or does not exist
     */
    void updatePatient(Patient patient);

    /**
     * Deletes a patient by their ID.
     * @param id the patient ID
     * @return true if the patient was deleted, false if not found
     */
    boolean deletePatient(String id);
} 