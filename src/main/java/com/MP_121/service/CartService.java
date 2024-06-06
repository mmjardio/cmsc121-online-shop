package com.MP_121.service;

import com.MP_121.model.CartModel;
import com.MP_121.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public CartModel addItemToCart(CartModel cart) {
        return cartRepository.save(cart);
    }

    public List<CartModel> getCartItems(Long buyerId) {
        return cartRepository.findByBuyerId(buyerId);
    }

    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(Long buyerId) {
        List<CartModel> cartItems = cartRepository.findByBuyerId(buyerId);
        cartRepository.deleteAll(cartItems);
    }

}
