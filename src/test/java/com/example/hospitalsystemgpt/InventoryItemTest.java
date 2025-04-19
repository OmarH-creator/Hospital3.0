package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryItemTest {
    @Test
    void validInventoryItemIsCreated() { // Tests valid construction and all getter methods
        InventoryItem item = new InventoryItem("I001", "Bandage", 10, 2.5);
        assertEquals("I001", item.getItemId());
        assertEquals("Bandage", item.getName());
        assertEquals(10, item.getQuantity());
        assertEquals(2.5, item.getUnitPrice());
    }

    @Test
    void nullIdThrowsException() { // Tests that null ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem(null, "Bandage", 10, 2.5));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void blankIdThrowsException() { // Tests that blank ID is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("   ", "Bandage", 10, 2.5));
        assertTrue(ex.getMessage().contains("ID"));
    }

    @Test
    void nullNameThrowsException() { // Tests that null name is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("I002", null, 10, 2.5));
        assertTrue(ex.getMessage().contains("Name"));
    }

    @Test
    void blankNameThrowsException() { // Tests that blank name is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("I003", "   ", 10, 2.5));
        assertTrue(ex.getMessage().contains("Name"));
    }

    @Test
    void negativeQuantityThrowsException() { // Tests that negative quantity is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("I004", "Bandage", -1, 2.5));
        assertTrue(ex.getMessage().contains("Quantity"));
    }

    @Test
    void zeroUnitPriceThrowsException() { // Tests that zero unit price is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("I005", "Bandage", 10, 0.0));
        assertTrue(ex.getMessage().contains("Unit price"));
    }

    @Test
    void negativeUnitPriceThrowsException() { // Tests that negative unit price is rejected
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new InventoryItem("I006", "Bandage", 10, -1.0));
        assertTrue(ex.getMessage().contains("Unit price"));
    }

    @Test
    void setNameWorksAndValidates() { // Tests setName with valid and invalid values
        InventoryItem item = new InventoryItem("I007", "Bandage", 10, 2.5);
        item.setName("Gauze");
        assertEquals("Gauze", item.getName());
        assertThrows(IllegalArgumentException.class, () -> item.setName(null));
        assertThrows(IllegalArgumentException.class, () -> item.setName("   "));
    }

    @Test
    void setUnitPriceWorksAndValidates() { // Tests setUnitPrice with valid and invalid values
        InventoryItem item = new InventoryItem("I008", "Bandage", 10, 2.5);
        item.setUnitPrice(3.0);
        assertEquals(3.0, item.getUnitPrice());
        assertThrows(IllegalArgumentException.class, () -> item.setUnitPrice(0.0));
        assertThrows(IllegalArgumentException.class, () -> item.setUnitPrice(-1.0));
    }

    @Test
    void addStockWorksAndValidates() { // Tests addStock with valid and invalid values
        InventoryItem item = new InventoryItem("I009", "Bandage", 10, 2.5);
        item.addStock(5);
        assertEquals(15, item.getQuantity());
        assertThrows(IllegalArgumentException.class, () -> item.addStock(0));
        assertThrows(IllegalArgumentException.class, () -> item.addStock(-3));
    }

    @Test
    void removeStockWorksAndValidates() { // Tests removeStock with valid and invalid values
        InventoryItem item = new InventoryItem("I010", "Bandage", 10, 2.5);
        item.removeStock(5);
        assertEquals(5, item.getQuantity());
        assertThrows(IllegalArgumentException.class, () -> item.removeStock(0));
        assertThrows(IllegalArgumentException.class, () -> item.removeStock(-2));
        assertThrows(IllegalArgumentException.class, () -> item.removeStock(20));
    }

    @Test
    void isLowStockWorks() { // Tests isLowStock method
        InventoryItem item = new InventoryItem("I011", "Bandage", 3, 2.5);
        assertTrue(item.isLowStock(5));
        assertFalse(item.isLowStock(2));
    }

    @Test
    void equalsAndHashCodeBasedOnId() { // Tests equals and hashCode for InventoryItem objects
        InventoryItem i1 = new InventoryItem("I012", "Bandage", 10, 2.5);
        InventoryItem i2 = new InventoryItem("I012", "Other", 5, 1.0);
        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void notEqualsForDifferentId() { // Tests that items with different IDs are not equal
        InventoryItem i1 = new InventoryItem("I013", "Bandage", 10, 2.5);
        InventoryItem i2 = new InventoryItem("I014", "Bandage", 10, 2.5);
        assertNotEquals(i1, i2);
    }

    @Test
    void equalsReturnsTrueForSameObject() { // Tests equals returns true when comparing to self
        InventoryItem i = new InventoryItem("I015", "Bandage", 10, 2.5);
        assertEquals(i, i);
    }

    @Test
    void equalsReturnsFalseForNull() { // Tests equals returns false when comparing to null
        InventoryItem i = new InventoryItem("I016", "Bandage", 10, 2.5);
        assertNotEquals(i, null);
    }

    @Test
    void equalsReturnsFalseForDifferentClass() { // Tests equals returns false when comparing to different class
        InventoryItem i = new InventoryItem("I017", "Bandage", 10, 2.5);
        assertNotEquals(i, "not an item");
    }
} 