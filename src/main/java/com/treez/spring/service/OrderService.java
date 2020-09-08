package com.treez.spring.service;

import com.treez.spring.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Optional<Order> findOrder(Long id);

    Optional<List<Order>> findByInventoryId(Long id);

    List<Order> getOrdersList();

    Order createOrder(Order Order);

    Order updateOrder(Long id, Order order);

    void deleteOrder(Long id);
}
