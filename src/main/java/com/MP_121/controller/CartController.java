package com.MP_121.controller;

import com.MP_121.model.CartModel;
import com.MP_121.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public CartModel addItemToCart(@RequestBody CartModel cart) {
        return cartService.addItemToCart(cart);
    }

    @GetMapping("/{buyerId}")
    public List<CartModel> getCartItems(@PathVariable Long buyerId) {
        return cartService.getCartItems(buyerId);
    }

    @DeleteMapping("/remove/{cartId}")
    public void removeItemFromCart(@PathVariable Long cartId) {
        cartService.removeItemFromCart(cartId);
    }
}
