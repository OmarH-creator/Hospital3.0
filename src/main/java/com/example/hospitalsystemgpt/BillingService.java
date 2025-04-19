package com.example.hospitalsystemgpt;

import java.util.List;

/**
 * Service interface for managing bills.
 */
public interface BillingService {
    /**
     * Creates a new bill.
     * @param bill the bill to create
     * @throws IllegalArgumentException if the bill is null or already exists
     */
    void createBill(Bill bill);

    /**
     * Finds a bill by its unique ID.
     * @param id the bill ID
     * @return the Bill if found, or null if not found
     */
    Bill findBillById(String id);

    /**
     * Returns a list of all bills.
     * @return list of bills
     */
    List<Bill> getAllBills();

    /**
     * Updates an existing bill.
     * @param bill the bill with updated information
     * @throws IllegalArgumentException if the bill is null or does not exist
     */
    void updateBill(Bill bill);

    /**
     * Marks a bill as paid with a payment reference.
     * @param id the bill ID
     * @param paymentReference the payment reference
     * @throws IllegalArgumentException if the bill does not exist or is already paid
     */
    void markBillAsPaid(String id, String paymentReference);

    /**
     * Deletes a bill by its ID.
     * @param id the bill ID
     * @return true if the bill was deleted, false if not found
     */
    boolean deleteBill(String id);
} 