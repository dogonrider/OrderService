package com.treez.spring.controller;

import com.treez.spring.model.Order;
import com.treez.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    @ResponseBody
    public List<Order> fetchOrders() {
        List<Order> ordersList = orderService.getOrdersList();
        return ordersList;
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    public Order fetchOrder(@PathVariable Long id) {
        Optional<Order> order = orderService.findOrder(id);
        return order.isPresent() ? order.get() : null;
    }

    @PostMapping("/orders")
    @ResponseBody
    public Order createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        return savedOrder;
    }

    @PutMapping("/orders/{id}")
    @ResponseBody
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        Order updatedOrder =  orderService.updateOrder(id, order);
        return updatedOrder;
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(String.format("%s%s", "Order deleted having the id: ", id));
    }
}
