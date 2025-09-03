package com.Shopsea.project.controller;

import com.Shopsea.project.Entity.Order;
import com.Shopsea.project.Entity.Users;
import com.Shopsea.project.Repository.OrderRepository;
import com.Shopsea.project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository usersRepository;

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get orders by user ID
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestParam Long userId,
                                             @RequestBody Order orderDetails) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            Order order = new Order();
            order.setUser(user.get());
            order.setOrderDate(new Date());
            order.setTotalAmount(orderDetails.getTotalAmount());
            order.setStatus(orderDetails.getStatus());
            order.setItems(orderDetails.getItems()); // Assuming items are passed correctly
            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update order status
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id,@RequestParam String status) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            Order existingOrder = order.get();
            existingOrder.setStatus(status);
            return ResponseEntity.ok(orderRepository.save(existingOrder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

