package com.MP_121.controller;

import com.MP_121.model.CartModel;
import com.MP_121.model.ProductModel;
import com.MP_121.service.CartService;
import com.MP_121.service.ProductService;
import com.MP_121.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private final ProductService productService;
    private final CartService cartService;
    private final UsersService usersService;

    @Autowired
    public BuyerController(ProductService productService, CartService cartService, UsersService usersService) {
        this.productService = productService;
        this.cartService = cartService;
        this.usersService = usersService;
    }

    @GetMapping("/products")
    public List<ProductModel> getAllProducts() {
        return productService.getAllItems();
    }

    @GetMapping("/products/search")
    public List<ProductModel> searchProductsByName(@RequestParam String keyword) {
        return productService.searchItemsByName(keyword);
    }

    @PostMapping("/cart/add/{productId}")
    public CartModel addToCart(@PathVariable Long productId, @RequestParam String email, @RequestParam String password) {
        ProductModel product = productService.getItemById(productId);
        if (product != null) {
            return cartService.addItemToCart(productId, email, password);
        } else {
            // Handle case where product with given ID is not found
            throw new RuntimeException("Product not found with id: " + productId);
        }
    }

    @GetMapping("/cart")
    public List<CartModel> getCartItems(@RequestParam String email, @RequestParam String password) {
        return cartService.getCartItems(email, password);
    }

    @DeleteMapping("/cart/remove/{cartId}")
    public void removeCartItem(@PathVariable Long cartId) {
        cartService.removeItemFromCart(cartId);
    }

    @PostMapping("/cart/checkout")
    public void checkoutCart(@RequestParam String email, @RequestParam String password) {
        cartService.checkoutCart(email, password);
        // Optionally, you can return a success message or status after checkout
    }
}