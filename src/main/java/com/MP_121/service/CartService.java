package com.MP_121.service;

import com.MP_121.model.CartModel;
import com.MP_121.model.OrderModel;
import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.repository.CartRepository;
import com.MP_121.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UsersService usersService;

    @Autowired
    public CartService(CartRepository cartRepository, OrderRepository orderRepository, ProductService productService, UsersService usersService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.usersService = usersService;
    }

    public CartModel addItemToCart(Long productId, String email, String password) {
        ProductModel product = productService.getItemById(productId);
        UsersModel buyer = usersService.authenticate(email, password);

        if (product == null) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        if (buyer == null) {
            throw new RuntimeException("Invalid credentials or buyer not found for email: " + email);
        }

        CartModel cart = new CartModel();
        cart.setProductId(product);
        cart.setBuyerId(buyer);

        return cartRepository.save(cart);
    }

    public List<CartModel> getCartItems(String email, String password) {
        UsersModel buyer = usersService.authenticate(email, password);

        if (buyer == null) {
            throw new RuntimeException("Invalid credentials or buyer not found for email: " + email);
        }

        return cartRepository.findByBuyer(buyer);
    }

    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void checkoutCart(String email, String password) {
        UsersModel buyer = usersService.authenticate(email, password);

        if (buyer == null) {
            throw new RuntimeException("Invalid credentials or buyer not found for email: " + email);
        }

        List<CartModel> cartItems = cartRepository.findByBuyer(buyer);

        for (CartModel cartItem : cartItems) {
            OrderModel order = new OrderModel();
            order.setBuyerId(buyer); // Set the buyer
            order.setProductId(cartItem.getProductId()); // Set the product associated with the cart item
            order.setQuantity(cartItem.getQuantity()); // Set the quantity

            // Optionally, set any other attributes specific to your OrderModel

            orderRepository.save(order); // Save the order to the database
        }

        cartRepository.deleteAll(cartItems); // Clear the cart after checkout
    }

    public void clearCart(UsersModel buyerId) {
        List<CartModel> cartItems = cartRepository.findByBuyer(buyerId);
        cartRepository.deleteAll(cartItems);
    }

}
