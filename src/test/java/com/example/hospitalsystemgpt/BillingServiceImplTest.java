package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BillingServiceImplTest {
    private BillingService service;
    private Patient patient;
    private Bill bill1;
    private Bill bill2;

    @BeforeEach
    void setUp() {
        service = new BillingServiceImpl();
        patient = new Patient("P001", "Alice", LocalDate.of(1990, 1, 1));
        bill1 = new Bill("B001", patient);
        bill2 = new Bill("B002", patient);
    }

    @Test
    void createBillWorks() { // Tests creating a new bill
        service.createBill(bill1);
        assertEquals(bill1, service.findBillById("B001"));
    }

    @Test
    void createNullBillThrowsException() { // Tests that creating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.createBill(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void createDuplicateBillThrowsException() { // Tests that creating duplicate bill throws exception
        service.createBill(bill1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.createBill(bill1));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void findBillByIdReturnsCorrectBill() { // Tests finding a bill by ID
        service.createBill(bill1);
        service.createBill(bill2);
        assertEquals(bill2, service.findBillById("B002"));
    }

    @Test
    void findBillByIdReturnsNullIfNotFound() { // Tests finding a non-existent bill returns null
        assertNull(service.findBillById("B999"));
    }

    @Test
    void getAllBillsReturnsAllCreated() { // Tests getting all created bills
        service.createBill(bill1);
        service.createBill(bill2);
        List<Bill> bills = service.getAllBills();
        assertEquals(2, bills.size());
        assertTrue(bills.contains(bill1));
        assertTrue(bills.contains(bill2));
    }

    @Test
    void updateBillWorks() { // Tests updating an existing bill
        service.createBill(bill1);
        Bill updated = new Bill("B001", patient);
        updated.addLineItem(new Bill.LineItem("Service", 100.0));
        service.updateBill(updated);
        assertEquals(100.0, service.findBillById("B001").getTotalAmount());
    }

    @Test
    void updateNullBillThrowsException() { // Tests that updating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateBill(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void updateNonExistentBillThrowsException() { // Tests that updating a non-existent bill throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateBill(bill1));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void markBillAsPaidWorks() { // Tests marking a bill as paid
        service.createBill(bill1);
        service.markBillAsPaid("B001", "PAY123");
        assertEquals(Bill.Status.PAID, service.findBillById("B001").getStatus());
        assertEquals("PAY123", service.findBillById("B001").getPaymentReference());
    }

    @Test
    void markBillAsPaidWithNullReferenceThrowsException() { // Tests that null payment reference throws exception
        service.createBill(bill1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.markBillAsPaid("B001", null));
        assertTrue(ex.getMessage().contains("Payment reference"));
    }

    @Test
    void markBillAsPaidWithBlankReferenceThrowsException() { // Tests that blank payment reference throws exception
        service.createBill(bill1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.markBillAsPaid("B001", "   "));
        assertTrue(ex.getMessage().contains("Payment reference"));
    }

    @Test
    void markBillAsPaidOnNonExistentBillThrowsException() { // Tests that marking a non-existent bill as paid throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.markBillAsPaid("B999", "PAY123"));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void markBillAsPaidOnAlreadyPaidBillThrowsException() { // Tests that marking an already paid bill throws exception
        service.createBill(bill1);
        service.markBillAsPaid("B001", "PAY123");
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.markBillAsPaid("B001", "PAY456"));
        assertTrue(ex.getMessage().contains("already paid"));
    }

    @Test
    void deleteBillWorks() { // Tests deleting an existing bill
        service.createBill(bill1);
        assertTrue(service.deleteBill("B001"));
        assertNull(service.findBillById("B001"));
    }

    @Test
    void deleteNonExistentBillReturnsFalse() { // Tests deleting a non-existent bill returns false
        assertFalse(service.deleteBill("B999"));
    }
} 