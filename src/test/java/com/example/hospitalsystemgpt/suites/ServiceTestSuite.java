package com.example.hospitalsystemgpt.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    com.example.hospitalsystemgpt.PatientServiceImplTest.class,
    com.example.hospitalsystemgpt.AppointmentServiceImplTest.class,
    com.example.hospitalsystemgpt.MedicalRecordServiceImplTest.class,
    com.example.hospitalsystemgpt.BillingServiceImplTest.class,
    com.example.hospitalsystemgpt.InventoryServiceImplTest.class
})
public class ServiceTestSuite {} 