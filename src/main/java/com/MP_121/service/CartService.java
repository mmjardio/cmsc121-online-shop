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

    @Autowired
    private final OrderRepository orderRepository;

    @Autowired
    private final ProductService productService;

    @Autowired
    private final UsersService usersService;

    @Autowired
    public CartService(CartRepository cartRepository, OrderRepository orderRepository, ProductService productService, UsersService usersService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.usersService = usersService;
    }

    public CartModel addItemToCart(Long productId, UsersModel buyer, int quantity) {
        ProductModel product = productService.getItemById(productId);
        if (product != null) {
            CartModel cartItem = cartRepository.findByProductAndBuyer(product, buyer);
            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            } else {
                cartItem = new CartModel();
                cartItem.setProduct(product);
                cartItem.setBuyer(buyer);
                cartItem.setQuantity(quantity);
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

    @Transactional
    public List<CartModel> getCartItemsByBuyer(UsersModel buyer) {
        return cartRepository.findAllByBuyer(buyer);
    }

    public void checkoutCart(UsersModel buyer) {
        List<CartModel> cartItems = cartRepository.findAllByBuyer(buyer);
        createOrders(cartItems, buyer);
        deleteCartItems(cartItems);
    }

    private void createOrders(List<CartModel> cartItems, UsersModel buyer) {
        for (CartModel cartItem : cartItems) {
            OrderModel order = new OrderModel();
            order.setBuyer(buyer);
            order.setSeller(cartItem.getProduct().getSellerId());
            order.setProductId(cartItem.getProduct());
            order.setQuantity(cartItem.getQuantity());
            orderRepository.save(order);
        }
    }

    private void deleteCartItems(List<CartModel> cartItems) {
        cartRepository.deleteAll(cartItems);
    }
}
