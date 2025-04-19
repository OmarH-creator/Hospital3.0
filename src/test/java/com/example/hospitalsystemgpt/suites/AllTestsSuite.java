package com.example.hospitalsystemgpt.suites;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    ModelTestSuite.class,
    ServiceTestSuite.class,
    ControllerTestSuite.class
})
public class AllTestsSuite {} 