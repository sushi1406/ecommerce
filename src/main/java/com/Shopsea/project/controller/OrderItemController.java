package com.Shopsea.project.controller;


import com.Shopsea.project.Entity.OrderItem;
import com.Shopsea.project.Entity.Order;
import com.Shopsea.project.Entity.Product;
import com.Shopsea.project.Repository.OrderItemRepository;
import com.Shopsea.project.Repository.OrderRepository;
import com.Shopsea.project.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // Get all order items
    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Get order items by order ID
    @GetMapping("/order/{orderId}")
    public List<OrderItem> getItemsByOrder(@PathVariable Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Create a new order item
    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestParam Long orderId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity,
                                                     @RequestParam Double price) {
        Optional<Order> order = orderRepository.findById(orderId);
        Optional<Product> product = productRepository.findById(productId);

        if (order.isPresent() && product.isPresent()) {
            OrderItem item = new OrderItem(quantity, price, order.get(), product.get());
            return ResponseEntity.ok(orderItemRepository.save(item));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update an order item
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id,
                                                     @RequestParam Integer quantity,
                                                     @RequestParam Double price) {
        Optional<OrderItem> item = orderItemRepository.findById(id);
        if (item.isPresent()) {
            OrderItem existingItem = item.get();
            existingItem.setQuantity(quantity);
            existingItem.setPrice(price);
            return ResponseEntity.ok(orderItemRepository.save(existingItem));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an order item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
