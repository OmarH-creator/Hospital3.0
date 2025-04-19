package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of MedicalRecordService.
 */
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final Map<String, MedicalRecord> recordMap = new HashMap<>();

    /**
     * Adds a new medical record. Throws if record is null or already exists.
     */
    @Override
    public void addMedicalRecord(MedicalRecord record) {
        if (record == null) throw new IllegalArgumentException("Medical record cannot be null");
        if (recordMap.containsKey(record.getRecordId())) throw new IllegalArgumentException("Medical record already exists");
        recordMap.put(record.getRecordId(), record);
    }

    /**
     * Finds a medical record by its unique ID.
     */
    @Override
    public MedicalRecord findMedicalRecordById(String id) {
        return recordMap.get(id);
    }

    /**
     * Returns a list of all medical records.
     */
    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return new ArrayList<>(recordMap.values());
    }

    /**
     * Updates an existing medical record. Throws if record is null or does not exist.
     */
    @Override
    public void updateMedicalRecord(MedicalRecord record) {
        if (record == null) throw new IllegalArgumentException("Medical record cannot be null");
        if (!recordMap.containsKey(record.getRecordId())) throw new IllegalArgumentException("Medical record does not exist");
        recordMap.put(record.getRecordId(), record);
    }

    /**
     * Deletes a medical record by its ID. Returns true if deleted, false if not found.
     */
    @Override
    public boolean deleteMedicalRecord(String id) {
        return recordMap.remove(id) != null;
    }
} 