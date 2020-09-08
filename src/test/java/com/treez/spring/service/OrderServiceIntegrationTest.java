package com.treez.spring.service;

import com.treez.spring.enums.OrderStatus;
import com.treez.spring.model.Inventory;
import com.treez.spring.model.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class OrderServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private Inventory savedInventory;

    private Inventory savedInventoryForOrder;

    private Order savedOrder;

    @Before
    public void setup() {
        Inventory inventory = Inventory.builder().price(23.56D).build();
        Inventory inventoryForOrder = Inventory.builder().price(23.56D).quantityAvailable(20).build();
        savedInventory = restTemplate.postForObject("/inventories", inventory, Inventory.class);
        savedInventoryForOrder = restTemplate.postForObject("/inventories", inventoryForOrder, Inventory.class);
        Order order = Order.builder().orderStatus(OrderStatus.RECEIVED).quantityOfOrder(2).inventoryId(savedInventoryForOrder.getId()).customerEmailAddress("test@testDomain.com").build();
        savedOrder = restTemplate.postForObject("/orders", order, Order.class);
    }

    @Test
    public void testCreateInventory() {
        Assert.assertEquals(Double.valueOf(23.56D), savedInventory.getPrice());
    }

    @Test
    public void testUpdateInventory() {
        String updateInventoryUrl = String.format("%s%s", "/inventories/", savedInventory.getId());
        Inventory fetchedInventory = restTemplate.getForObject(updateInventoryUrl, Inventory.class);
        Assert.assertNull(fetchedInventory.getDescription());
        savedInventory.setDescription("testUpdate");
        restTemplate.put(updateInventoryUrl, savedInventory);
        Inventory updatedInventory = restTemplate.getForObject(updateInventoryUrl, Inventory.class);
        Assert.assertEquals(updatedInventory.getDescription(), "testUpdate");
    }

    @Test
    public void testDeleteInventory() {
        String inventoryUrl = String.format("%s%s", "/inventories/", savedInventory.getId());
        restTemplate.delete(inventoryUrl);
        Inventory inventory = restTemplate.getForObject(inventoryUrl, Inventory.class);
        Assert.assertNull(inventory);
    }

    @Test
    public void testCreateOrder() {
        Assert.assertEquals("test@testDomain.com", savedOrder.getCustomerEmailAddress());
    }

    @Test
    public void testUpdateOrder() {
        String updateOrderUrl = String.format("%s%s", "/orders/", savedOrder.getId());
        Order fetchedOrder = restTemplate.getForObject(updateOrderUrl, Order.class);
        Assert.assertEquals(fetchedOrder.getCustomerEmailAddress(), savedOrder.getCustomerEmailAddress());
        savedOrder.setCustomerEmailAddress("testUpdate@testDomain.com");
        restTemplate.put(updateOrderUrl, savedOrder);
        Order updatedOrder = restTemplate.getForObject(updateOrderUrl, Order.class);
        Assert.assertEquals(updatedOrder.getCustomerEmailAddress(), "testUpdate@testDomain.com");
    }

    @Test
    public void testDeleteOrder() {
        String orderUrl = String.format("%s%s", "/orders/", savedOrder.getId());
        restTemplate.delete(orderUrl);
        Inventory inventory = restTemplate.getForObject(orderUrl, Inventory.class);
        Assert.assertNull(inventory);
    }
}
