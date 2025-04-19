package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Bill {
    public enum Status { PAID, UNPAID }

    private final String id;
    private final Patient patient;
    private final List<LineItem> lineItems;
    private Status status;
    private double totalAmount;
    private String paymentReference;

    /**
     * Constructs a Bill with the given id and patient. Initializes as UNPAID with empty line items.
     * Validates that id and patient are not null/blank.
     */
    public Bill(String id, Patient patient) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank");
        if (patient == null) throw new IllegalArgumentException("Patient cannot be null");
        this.id = id;
        this.patient = patient;
        this.lineItems = new ArrayList<>();
        this.status = Status.UNPAID;
        this.totalAmount = 0.0;
        this.paymentReference = null;
    }

    /**
     * Returns the bill's unique ID.
     */
    public String getBillId() { return id; }

    /**
     * Returns the patient associated with this bill.
     */
    public Patient getPatient() { return patient; }

    /**
     * Returns an unmodifiable list of line items.
     */
    public List<LineItem> getLineItems() { return Collections.unmodifiableList(lineItems); }

    /**
     * Returns the total amount for this bill.
     */
    public double getTotalAmount() { return totalAmount; }

    /**
     * Returns the status of the bill (PAID or UNPAID).
     */
    public Status getStatus() { return status; }

    /**
     * Returns the payment reference if paid, or null otherwise.
     */
    public String getPaymentReference() { return paymentReference; }

    /**
     * Adds a line item to the bill and updates the total amount.
     */
    public void addLineItem(LineItem item) {
        if (item == null) throw new IllegalArgumentException("Line item cannot be null");
        lineItems.add(item);
        totalAmount += item.getAmount();
    }

    /**
     * Marks the bill as paid and sets the payment reference.
     */
    public void markAsPaid(String paymentReference) {
        if (status == Status.PAID) throw new IllegalStateException("Bill is already paid");
        if (paymentReference == null || paymentReference.isBlank()) throw new IllegalArgumentException("Payment reference cannot be null or blank");
        this.status = Status.PAID;
        this.paymentReference = paymentReference;
    }

    /**
     * Checks equality based on bill ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bill bill = (Bill) o;
        return id.equals(bill.id);
    }

    /**
     * Returns the hash code for the bill, based on ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Represents a line item in a bill (description and amount).
     */
    public static class LineItem {
        private final String description;
        private final double amount;

        /**
         * Constructs a LineItem with the given description and amount.
         * Validates that description is not null/blank and amount is positive.
         */
        public LineItem(String description, double amount) {
            if (description == null || description.isBlank()) throw new IllegalArgumentException("Description cannot be null or blank");
            if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
            this.description = description;
            this.amount = amount;
        }

        /**
         * Returns the description of the line item.
         */
        public String getDescription() { return description; }

        /**
         * Returns the amount of the line item.
         */
        public double getAmount() { return amount; }
    }
} 