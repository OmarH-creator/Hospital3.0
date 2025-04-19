package com.example.hospitalsystemgpt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryServiceImplTest {
    private InventoryService service;
    private InventoryItem item1;
    private InventoryItem item2;

    @BeforeEach
    void setUp() {
        service = new InventoryServiceImpl();
        item1 = new InventoryItem("I001", "Bandage", 10, 2.5);
        item2 = new InventoryItem("I002", "Gauze", 5, 1.0);
    }

    @Test
    void addInventoryItemWorks() { // Tests adding a new inventory item
        service.addInventoryItem(item1);
        assertEquals(item1, service.findInventoryItemById("I001"));
    }

    @Test
    void addNullInventoryItemThrowsException() { // Tests that adding null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.addInventoryItem(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void addDuplicateInventoryItemThrowsException() { // Tests that adding duplicate item throws exception
        service.addInventoryItem(item1);
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.addInventoryItem(item1));
        assertTrue(ex.getMessage().contains("already exists"));
    }

    @Test
    void findInventoryItemByIdReturnsCorrectItem() { // Tests finding an item by ID
        service.addInventoryItem(item1);
        service.addInventoryItem(item2);
        assertEquals(item2, service.findInventoryItemById("I002"));
    }

    @Test
    void findInventoryItemByIdReturnsNullIfNotFound() { // Tests finding a non-existent item returns null
        assertNull(service.findInventoryItemById("I999"));
    }

    @Test
    void getAllInventoryItemsReturnsAllAdded() { // Tests getting all added items
        service.addInventoryItem(item1);
        service.addInventoryItem(item2);
        List<InventoryItem> items = service.getAllInventoryItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void updateInventoryItemWorks() { // Tests updating an existing item
        service.addInventoryItem(item1);
        InventoryItem updated = new InventoryItem("I001", "Updated", 20, 3.0);
        service.updateInventoryItem(updated);
        assertEquals("Updated", service.findInventoryItemById("I001").getName());
        assertEquals(20, service.findInventoryItemById("I001").getQuantity());
        assertEquals(3.0, service.findInventoryItemById("I001").getUnitPrice());
    }

    @Test
    void updateNullInventoryItemThrowsException() { // Tests that updating null throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateInventoryItem(null));
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void updateNonExistentInventoryItemThrowsException() { // Tests that updating a non-existent item throws exception
        Exception ex = assertThrows(IllegalArgumentException.class, () -> service.updateInventoryItem(item1));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void deleteInventoryItemWorks() { // Tests deleting an existing item
        service.addInventoryItem(item1);
        assertTrue(service.deleteInventoryItem("I001"));
        assertNull(service.findInventoryItemById("I001"));
    }

    @Test
    void deleteNonExistentInventoryItemReturnsFalse() { // Tests deleting a non-existent item returns false
        assertFalse(service.deleteInventoryItem("I999"));
    }
} 