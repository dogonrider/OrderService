package com.treez.spring.service;

import com.treez.spring.customException.InventoryDeleteException;
import com.treez.spring.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    Optional<Inventory> findInventory(Long id);

    List<Inventory> getInventoryList();

    Inventory saveInventory(Inventory inventory);

    void deleteInventory(Long id) throws InventoryDeleteException;
}
