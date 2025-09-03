package com.Shopsea.project.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Shopsea.project.Entity.Cart;
import com.Shopsea.project.Repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    public Cart getCartItemById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCartItem(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}

