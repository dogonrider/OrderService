package com.treez.spring.service;

import com.treez.spring.customException.InventoryDeleteException;
import com.treez.spring.model.Inventory;
import com.treez.spring.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Optional<Inventory> findInventory(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public List<Inventory> getInventoryList() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) throws InventoryDeleteException {
        if(orderService.findByInventoryId(id).isPresent()) { throw new InventoryDeleteException("Cant delete inventory as orders exist on it"); }
        Inventory inventory = inventoryRepository.findById(id).get();
        inventoryRepository.delete(inventory);
    }
}
