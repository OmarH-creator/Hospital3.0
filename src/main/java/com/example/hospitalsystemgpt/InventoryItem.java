package com.example.hospitalsystemgpt;

import java.util.Objects;

public class InventoryItem {
    private final String id;
    private String name;
    private int quantity;
    private double unitPrice;

    /**
     * Constructs an InventoryItem with the given id, name, quantity, and unit price.
     * Validates that id and name are not null/blank, quantity is non-negative, and unit price is positive.
     */
    public InventoryItem(String id, String name, int quantity, double unitPrice) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        if (unitPrice <= 0) throw new IllegalArgumentException("Unit price must be positive");
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    /**
     * Returns the inventory item's unique ID.
     */
    public String getItemId() { return id; }

    /**
     * Returns the name of the inventory item.
     */
    public String getName() { return name; }

    /**
     * Sets the name of the inventory item after validating it is not null or blank.
     */
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name;
    }

    /**
     * Returns the current quantity in stock.
     */
    public int getQuantity() { return quantity; }

    /**
     * Returns the unit price of the item.
     */
    public double getUnitPrice() { return unitPrice; }

    /**
     * Sets the unit price after validating it is positive.
     */
    public void setUnitPrice(double unitPrice) {
        if (unitPrice <= 0) throw new IllegalArgumentException("Unit price must be positive");
        this.unitPrice = unitPrice;
    }

    /**
     * Adds the specified amount to the quantity in stock.
     */
    public void addStock(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount to add must be positive");
        quantity += amount;
    }

    /**
     * Removes the specified amount from the quantity in stock.
     */
    public void removeStock(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount to remove must be positive");
        if (amount > quantity) throw new IllegalArgumentException("Not enough stock to remove");
        quantity -= amount;
    }

    /**
     * Returns true if the quantity is less than or equal to the specified threshold.
     */
    public boolean isLowStock(int threshold) {
        return quantity <= threshold;
    }

    /**
     * Checks equality based on item ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItem that = (InventoryItem) o;
        return id.equals(that.id);
    }

    /**
     * Returns the hash code for the inventory item, based on ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 