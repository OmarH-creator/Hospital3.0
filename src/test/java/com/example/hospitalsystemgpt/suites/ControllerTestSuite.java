package com.example.hospitalsystemgpt.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    com.example.hospitalsystemgpt.HospitalControllerTest.class
})
public class ControllerTestSuite {} 