package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of BillingService.
 */
public class BillingServiceImpl implements BillingService {
    private final Map<String, Bill> billMap = new HashMap<>();

    /**
     * Creates a new bill. Throws if bill is null or already exists.
     */
    @Override
    public void createBill(Bill bill) {
        if (bill == null) throw new IllegalArgumentException("Bill cannot be null");
        if (billMap.containsKey(bill.getBillId())) throw new IllegalArgumentException("Bill already exists");
        billMap.put(bill.getBillId(), bill);
    }

    /**
     * Finds a bill by its unique ID.
     */
    @Override
    public Bill findBillById(String id) {
        return billMap.get(id);
    }

    /**
     * Returns a list of all bills.
     */
    @Override
    public List<Bill> getAllBills() {
        return new ArrayList<>(billMap.values());
    }

    /**
     * Updates an existing bill. Throws if bill is null or does not exist.
     */
    @Override
    public void updateBill(Bill bill) {
        if (bill == null) throw new IllegalArgumentException("Bill cannot be null");
        if (!billMap.containsKey(bill.getBillId())) throw new IllegalArgumentException("Bill does not exist");
        billMap.put(bill.getBillId(), bill);
    }

    /**
     * Marks a bill as paid with a payment reference. Throws if bill does not exist or is already paid.
     */
    @Override
    public void markBillAsPaid(String id, String paymentReference) {
        Bill bill = billMap.get(id);
        if (bill == null) throw new IllegalArgumentException("Bill does not exist");
        if (bill.getStatus() == Bill.Status.PAID) throw new IllegalArgumentException("Bill is already paid");
        bill.markAsPaid(paymentReference);
    }

    /**
     * Deletes a bill by its ID. Returns true if deleted, false if not found.
     */
    @Override
    public boolean deleteBill(String id) {
        return billMap.remove(id) != null;
    }
} 