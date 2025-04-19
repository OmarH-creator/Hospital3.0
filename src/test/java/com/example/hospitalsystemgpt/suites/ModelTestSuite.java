package com.example.hospitalsystemgpt.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    com.example.hospitalsystemgpt.PatientTest.class,
    com.example.hospitalsystemgpt.AppointmentTest.class,
    com.example.hospitalsystemgpt.MedicalRecordTest.class,
    com.example.hospitalsystemgpt.BillTest.class,
    com.example.hospitalsystemgpt.InventoryItemTest.class
})
public class ModelTestSuite {} 