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

    @Autowired
    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    @Autowired
    private final ProductService productService;
    private final UsersService usersService;

    @Autowired
    public CartService(CartRepository cartRepository, OrderRepository orderRepository, ProductService productService, UsersService usersService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.usersService = usersService;
    }


    public CartModel addItemToCart(Long productId, UsersModel buyer) {
        ProductModel product = productService.getItemById(productId);
        if (product != null) {
            CartModel cartItem = cartRepository.findByProductAndBuyer(product, buyer);
            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            } else {
                cartItem = new CartModel();
                cartItem.setProduct(product);
                cartItem.setBuyer(buyer);
                cartItem.setQuantity(1);
            }
            return cartRepository.save(cartItem);
        }
        return null; // Handle the case where the product is not found
    }

    public List<CartModel> getCartItems(UsersModel buyer) {
        return cartRepository.findAllByBuyer(buyer);
    }

    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    public void clearCart(UsersModel buyer) {
        List<CartModel> cartItems = cartRepository.findAllByBuyer(buyer);
        cartRepository.deleteAll(cartItems);
    }


    // Method to checkout the cart (process the order)
    public void checkoutCart(UsersModel buyer) {
        List<CartModel> cartItems = cartRepository.findAllByBuyer(buyer);
        // Perform checkout logic, such as creating orders, updating inventory, etc.
        // For demonstration purposes, we will just delete the cart items after checkout
        cartRepository.deleteAll(cartItems);
    }

}
