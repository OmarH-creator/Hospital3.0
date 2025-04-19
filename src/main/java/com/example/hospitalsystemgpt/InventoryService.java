package com.example.hospitalsystemgpt;

import java.util.List;

/**
 * Service interface for managing inventory items.
 */
public interface InventoryService {
    /**
     * Adds a new inventory item.
     * @param item the inventory item to add
     * @throws IllegalArgumentException if the item is null or already exists
     */
    void addInventoryItem(InventoryItem item);

    /**
     * Finds an inventory item by its unique ID.
     * @param id the item ID
     * @return the InventoryItem if found, or null if not found
     */
    InventoryItem findInventoryItemById(String id);

    /**
     * Returns a list of all inventory items.
     * @return list of inventory items
     */
    List<InventoryItem> getAllInventoryItems();

    /**
     * Updates an existing inventory item.
     * @param item the inventory item with updated information
     * @throws IllegalArgumentException if the item is null or does not exist
     */
    void updateInventoryItem(InventoryItem item);

    /**
     * Deletes an inventory item by its ID.
     * @param id the item ID
     * @return true if the item was deleted, false if not found
     */
    boolean deleteInventoryItem(String id);
} 