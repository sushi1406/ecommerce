package com.Shopsea.project.controller;

import com.Shopsea.project.Entity.Cart;
import com.Shopsea.project.Entity.Product;
import com.Shopsea.project.Entity.Users;
import com.Shopsea.project.Repository.CartRepository;
import com.Shopsea.project.Repository.ProductRepository;
import com.Shopsea.project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    // Get cart items by user ID
    @GetMapping("/user/{userId}")
    public List<Cart> getCartItemsByUser(@PathVariable Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Add item to cart
    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestParam Long userId,
                                          @RequestParam Long productId,
                                          @RequestParam Integer quantity) {
        Optional<Users> user = usersRepository.findById(userId);
        Optional<Product> product = productRepository.findById(productId);

        if (user.isPresent() && product.isPresent()) {
            Cart cartItem = new Cart(quantity, user.get(), product.get());
            return ResponseEntity.ok(cartRepository.save(cartItem));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update cart item quantity
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long id,
                                               @RequestParam Integer quantity) {
        Optional<Cart> cartItem = cartRepository.findById(id);
        if (cartItem.isPresent()) {
            Cart item = cartItem.get();
            item.setQuantity(quantity);
            return ResponseEntity.ok(cartRepository.save(item));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove item from cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
