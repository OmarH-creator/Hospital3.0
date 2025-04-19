package com.example.hospitalsystemgpt;

import java.util.List;

/**
 * Service interface for managing medical records.
 */
public interface MedicalRecordService {
    /**
     * Adds a new medical record.
     * @param record the medical record to add
     * @throws IllegalArgumentException if the record is null or already exists
     */
    void addMedicalRecord(MedicalRecord record);

    /**
     * Finds a medical record by its unique ID.
     * @param id the record ID
     * @return the MedicalRecord if found, or null if not found
     */
    MedicalRecord findMedicalRecordById(String id);

    /**
     * Returns a list of all medical records.
     * @return list of medical records
     */
    List<MedicalRecord> getAllMedicalRecords();

    /**
     * Updates an existing medical record.
     * @param record the medical record with updated information
     * @throws IllegalArgumentException if the record is null or does not exist
     */
    void updateMedicalRecord(MedicalRecord record);

    /**
     * Deletes a medical record by its ID.
     * @param id the record ID
     * @return true if the record was deleted, false if not found
     */
    boolean deleteMedicalRecord(String id);
} 