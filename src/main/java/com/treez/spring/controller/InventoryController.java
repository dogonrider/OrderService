package com.treez.spring.controller;

import com.treez.spring.customException.InventoryDeleteException;
import com.treez.spring.model.Inventory;
import com.treez.spring.service.InventoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/inventories")
    @ResponseBody
    public List<Inventory> fetchInventories() {
        List<Inventory> inventoryList = inventoryService.getInventoryList();
        return inventoryList;
    }

    @GetMapping("/inventories/{id}")
    @ResponseBody
    public Inventory fetchInventory(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryService.findInventory(id);
        return inventory.isPresent() ? inventory.get() : null;
    }

    @PostMapping("/inventories")
    @ResponseBody
    public Inventory createInventory(@RequestBody Inventory inventory) {
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        return savedInventory;
    }

    @PutMapping("/inventories/{id}")
    @ResponseBody
    public Inventory updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Optional<Inventory> fetchedInventoryOptional = inventoryService.findInventory(id);
        if (fetchedInventoryOptional.isPresent()) {
            Inventory fetchedInventory = fetchedInventoryOptional.get();
            BeanUtils.copyProperties(inventory, fetchedInventory, "id");
            fetchedInventory = inventoryService.saveInventory(fetchedInventory);
            return fetchedInventory;
        }
        return null;
    }

    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) throws InventoryDeleteException {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.ok(String.format("%s%s", "Inventory deleted having the id: ", id));
        } catch (InventoryDeleteException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
