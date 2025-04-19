package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillTest {
    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("P001", "John Doe", LocalDate.of(1990, 1, 1));
    }

    @Test
    void validBillIsCreated() { // Tests valid construction and all getter methods
        Bill bill = new Bill("B001", patient);
        assertEquals("B001", bill.getBillId());
        assertEquals(patient, bill.getPatient());
        assertEquals(0.0, bill.getTotalAmount());
        assertEquals(Bill.Status.UNPAID, bill.getStatus());
        assertTrue(bill.getLineItems().isEmpty());
        assertNull(bill.getPaymentReference());
    }

    @Test
    void nullIdThrowsException() { // Tests that null ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill(null, patient));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void blankIdThrowsException() { // Tests that blank ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill("   ", patient));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void nullPatientThrowsException() { // Tests that null patient is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill("B002", null));
        assertTrue(ex.getMessage().contains("Patient"));
    }

    @Test
    void addLineItemUpdatesTotal() { // Tests adding line items and updating total amount
        Bill bill = new Bill("B003", patient);
        Bill.LineItem item1 = new Bill.LineItem("Consultation", 100.0);
        Bill.LineItem item2 = new Bill.LineItem("X-Ray", 200.0);
        bill.addLineItem(item1);
        bill.addLineItem(item2);
        assertEquals(2, bill.getLineItems().size());
        assertEquals(300.0, bill.getTotalAmount());
    }

    @Test
    void addNullLineItemThrowsException() { // Tests that adding a null line item is rejected
        Bill bill = new Bill("B004", patient);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bill.addLineItem(null));
        assertTrue(ex.getMessage().contains("Line item"));
    }

    @Test
    void markAsPaidWorks() { // Tests marking a bill as paid with a valid payment reference
        Bill bill = new Bill("B005", patient);
        bill.markAsPaid("PAY123");
        assertEquals(Bill.Status.PAID, bill.getStatus());
        assertEquals("PAY123", bill.getPaymentReference());
    }

    @Test
    void markAsPaidTwiceThrowsException() { // Tests that marking an already paid bill throws exception
        Bill bill = new Bill("B006", patient);
        bill.markAsPaid("PAY123");
        Exception ex = assertThrows(IllegalStateException.class, () -> bill.markAsPaid("PAY456"));
        assertTrue(ex.getMessage().contains("already paid"));
    }

    @Test
    void markAsPaidWithNullReferenceThrowsException() { // Tests that null payment reference is rejected
        Bill bill = new Bill("B007", patient);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bill.markAsPaid(null));
        assertTrue(ex.getMessage().contains("Payment reference"));
    }

    @Test
    void markAsPaidWithBlankReferenceThrowsException() { // Tests that blank payment reference is rejected
        Bill bill = new Bill("B008", patient);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> bill.markAsPaid("   "));
        assertTrue(ex.getMessage().contains("Payment reference"));
    }

    @Test
    void getLineItemsReturnsUnmodifiableList() { // Tests that getLineItems returns an unmodifiable list
        Bill bill = new Bill("B009", patient);
        Bill.LineItem item = new Bill.LineItem("Test", 50.0);
        bill.addLineItem(item);
        List<Bill.LineItem> items = bill.getLineItems();
        assertThrows(UnsupportedOperationException.class, () -> items.add(new Bill.LineItem("Other", 10.0)));
    }

    @Test
    void equalsAndHashCodeBasedOnId() { // Tests equals and hashCode for Bill objects
        Bill b1 = new Bill("B010", patient);
        Bill b2 = new Bill("B010", patient);
        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void notEqualsForDifferentId() { // Tests that bills with different IDs are not equal
        Bill b1 = new Bill("B011", patient);
        Bill b2 = new Bill("B012", patient);
        assertNotEquals(b1, b2);
    }

    @Test
    void equalsReturnsTrueForSameObject() { // Tests equals returns true when comparing to self
        Bill b = new Bill("B013", patient);
        assertEquals(b, b);
    }

    @Test
    void equalsReturnsFalseForNull() { // Tests equals returns false when comparing to null
        Bill b = new Bill("B014", patient);
        assertNotEquals(b, null);
    }

    @Test
    void equalsReturnsFalseForDifferentClass() { // Tests equals returns false when comparing to different class
        Bill b = new Bill("B015", patient);
        assertNotEquals(b, "not a bill");
    }

    // --- LineItem tests ---

    @Test
    void validLineItemIsCreated() { // Tests valid construction and getters for LineItem
        Bill.LineItem item = new Bill.LineItem("Lab Test", 75.0);
        assertEquals("Lab Test", item.getDescription());
        assertEquals(75.0, item.getAmount());
    }

    @Test
    void nullDescriptionThrowsException() { // Tests that null description is rejected in LineItem
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill.LineItem(null, 10.0));
        assertTrue(ex.getMessage().contains("Description"));
    }

    @Test
    void blankDescriptionThrowsException() { // Tests that blank description is rejected in LineItem
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill.LineItem("   ", 10.0));
        assertTrue(ex.getMessage().contains("Description"));
    }

    @Test
    void zeroAmountThrowsException() { // Tests that zero amount is rejected in LineItem
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill.LineItem("Service", 0.0));
        assertTrue(ex.getMessage().contains("positive"));
    }

    @Test
    void negativeAmountThrowsException() { // Tests that negative amount is rejected in LineItem
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new Bill.LineItem("Service", -5.0));
        assertTrue(ex.getMessage().contains("positive"));
    }
} 