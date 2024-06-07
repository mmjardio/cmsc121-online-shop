package com.MP_121.service;

import com.MP_121.model.CartModel;
import com.MP_121.model.UsersModel;
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

    public List<CartModel> getCartItems(UsersModel buyerId) {
        return cartRepository.findByBuyer(buyerId);
    }

    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(UsersModel buyerId) {
        List<CartModel> cartItems = cartRepository.findByBuyer(buyerId);
        cartRepository.deleteAll(cartItems);
    }

}
