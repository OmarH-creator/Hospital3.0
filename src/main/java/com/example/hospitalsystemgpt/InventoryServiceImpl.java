package com.example.hospitalsystemgpt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of InventoryService.
 */
public class InventoryServiceImpl implements InventoryService {
    private final Map<String, InventoryItem> itemMap = new HashMap<>();

    /**
     * Adds a new inventory item. Throws if item is null or already exists.
     */
    @Override
    public void addInventoryItem(InventoryItem item) {
        if (item == null) throw new IllegalArgumentException("Inventory item cannot be null");
        if (itemMap.containsKey(item.getItemId())) throw new IllegalArgumentException("Inventory item already exists");
        itemMap.put(item.getItemId(), item);
    }

    /**
     * Finds an inventory item by its unique ID.
     */
    @Override
    public InventoryItem findInventoryItemById(String id) {
        return itemMap.get(id);
    }

    /**
     * Returns a list of all inventory items.
     */
    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return new ArrayList<>(itemMap.values());
    }

    /**
     * Updates an existing inventory item. Throws if item is null or does not exist.
     */
    @Override
    public void updateInventoryItem(InventoryItem item) {
        if (item == null) throw new IllegalArgumentException("Inventory item cannot be null");
        if (!itemMap.containsKey(item.getItemId())) throw new IllegalArgumentException("Inventory item does not exist");
        itemMap.put(item.getItemId(), item);
    }

    /**
     * Deletes an inventory item by its ID. Returns true if deleted, false if not found.
     */
    @Override
    public boolean deleteInventoryItem(String id) {
        return itemMap.remove(id) != null;
    }
} 