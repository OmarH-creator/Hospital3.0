package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of PatientService.
 */
public class PatientServiceImpl implements PatientService {
    private final Map<String, Patient> patientMap = new HashMap<>();

    /**
     * Registers a new patient. Throws if patient is null or already exists.
     */
    @Override
    public void registerPatient(Patient patient) {
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (patientMap.containsKey(patient.getPatientId())) throw new IllegalArgumentException("Patient already exists");
        patientMap.put(patient.getPatientId(), patient);
    }

    /**
     * Finds a patient by their unique ID.
     */
    @Override
    public Patient findPatientById(String id) {
        return patientMap.get(id);
    }

    /**
     * Returns a list of all registered patients.
     */
    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    /**
     * Updates an existing patient. Throws if patient is null or does not exist.
     */
    @Override
    public void updatePatient(Patient patient) {
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        if (!patientMap.containsKey(patient.getPatientId())) throw new IllegalArgumentException("Patient does not exist");
        patientMap.put(patient.getPatientId(), patient);
    }

    /**
     * Deletes a patient by their ID. Returns true if deleted, false if not found.
     */
    @Override
    public boolean deletePatient(String id) {
        return patientMap.remove(id) != null;
    }
} 