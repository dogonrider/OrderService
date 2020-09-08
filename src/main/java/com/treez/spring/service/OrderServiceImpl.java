package com.treez.spring.service;

import com.treez.spring.enums.OrderStatus;
import com.treez.spring.model.Inventory;
import com.treez.spring.model.Order;
import com.treez.spring.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryService inventoryService;

    @Override
    public Optional<Order> findOrder(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<List<Order>> findByInventoryId(Long id) {
        return orderRepository.findByInventoryId(id);
    }

    @Override
    public List<Order> getOrdersList() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        if (order.getOrderStatus() == OrderStatus.RECEIVED) {
            updateInventory(order.getInventoryId(), order.getQuantityOfOrder());
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order order) {
        Order fetchedOrder = orderRepository.findById(id).get();
        updateInventory(order, fetchedOrder);
        BeanUtils.copyProperties(order, fetchedOrder, "id");
        return orderRepository.save(fetchedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).get();
        orderRepository.delete(order);
    }

    private void updateInventory(Order order, Order fetchedOrder) {
        if (!isOrderUpdatingTheSameInventory(order, fetchedOrder)) {
            updateInventory(fetchedOrder.getInventoryId(), -fetchedOrder.getQuantityOfOrder());
            updateInventory(order.getInventoryId(), order.getQuantityOfOrder());
        } else {
            updateInventory(order.getInventoryId(), (order.getQuantityOfOrder() - fetchedOrder.getQuantityOfOrder()));
        }
    }

    private Inventory updateInventory(Long inventoryId, Integer quantityOfOrder) {
        Inventory inventory = inventoryService.findInventory(inventoryId).get();
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantityOfOrder);
        return inventoryService.saveInventory(inventory);
    }

    private boolean isOrderUpdatingTheSameInventory(Order updatedOrder, Order storedOrder) {
        return updatedOrder.getInventoryId() == storedOrder.getInventoryId();
    }
}
