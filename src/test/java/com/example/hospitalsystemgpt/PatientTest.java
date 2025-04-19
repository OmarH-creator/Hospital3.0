package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PatientTest {

    @Test
    void admitAndDischargePatientWork() { // Tests admit/discharge state transitions
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        assertFalse(p.isPatientAdmitted());
        p.admitPatient();
        assertTrue(p.isPatientAdmitted());
        p.dischargePatient();
        assertFalse(p.isPatientAdmitted());
    }

    @Test
    void equalsWithSelfReturnsTrue() { // Tests equals returns true when comparing to self
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        assertEquals(p, p);
    }

    @Test
    void equalsWithNullReturnsFalse() { // Tests equals returns false when comparing to null
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        assertNotEquals(p, null);
    }

    @Test
    void equalsWithDifferentClassReturnsFalse() { // Tests equals returns false when comparing to different class
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        assertNotEquals(p, "not a patient");
    }

    @Test
    void setPatientNameNullOrBlankThrowsException() { // Tests setPatientName with null and blank
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        assertThrows(IllegalArgumentException.class, () -> p.setPatientName(null));
        assertThrows(IllegalArgumentException.class, () -> p.setPatientName("   "));
    }

    @Test
    void getPatientAgeEdgeCase() { // Tests getPatientAge when birthday is today
        LocalDate today = LocalDate.now();
        Patient p = new Patient("1", "Test", today);
        assertEquals(0, p.getPatientAge());
    }

    @Test
    void getDateOfBirthReturnsCorrectValue() { // Tests getDateOfBirth method
        LocalDate dob = LocalDate.of(2000, 1, 1);
        Patient p = new Patient("1", "Test", dob);
        assertEquals(dob, p.getDateOfBirth());
    }

    @Test
    void constructorRejectsNullId() { // Tests constructor with null ID
        LocalDate dob = LocalDate.of(2000, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Patient(null, "Test", dob));
    }

    @Test
    void constructorRejectsBlankId() { // Tests constructor with blank ID
        LocalDate dob = LocalDate.of(2000, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Patient("   ", "Test", dob));
    }

    @Test
    void constructorRejectsNullName() { // Tests constructor with null name
        LocalDate dob = LocalDate.of(2000, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Patient("1", null, dob));
    }

    @Test
    void constructorRejectsBlankName() { // Tests constructor with blank name
        LocalDate dob = LocalDate.of(2000, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> new Patient("1", "   ", dob));
    }

    @Test
    void constructorRejectsNullDateOfBirth() { // Tests constructor with null dateOfBirth
        assertThrows(IllegalArgumentException.class, () -> new Patient("1", "Test", null));
    }

    @Test
    void constructorRejectsFutureDateOfBirth() { // Tests constructor with future dateOfBirth
        LocalDate future = LocalDate.now().plusDays(1);
        assertThrows(IllegalArgumentException.class, () -> new Patient("1", "Test", future));
    }

    @Test
    void setPatientNameWorksForValidName() { // Tests setPatientName with a valid value
        Patient p = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        p.setPatientName("New Name");
        assertEquals("New Name", p.getPatientName());
    }

    @Test
    void hashCodeIsConsistentWithId() { // Tests hashCode method
        Patient p1 = new Patient("1", "Test", LocalDate.of(2000, 1, 1));
        Patient p2 = new Patient("1", "Other", LocalDate.of(1999, 1, 1));
        assertEquals(p1.hashCode(), p2.hashCode());
    }
} 