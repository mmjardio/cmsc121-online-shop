package com.MP_121.controller;

import com.MP_121.model.CartModel;
import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.service.CartService;
import com.MP_121.service.ProductService;
import com.MP_121.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    @Autowired
    private final ProductService productService;

    @Autowired
    private final CartService cartService;

    @Autowired
    private final UsersService usersService;

    @Autowired
    public BuyerController(ProductService productService, CartService cartService, UsersService usersService) {
        this.productService = productService;
        this.cartService = cartService;
        this.usersService = usersService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer != null && "Buyer".equals(buyer.getRole())) {
            List<ProductModel> products = productService.getAllItems();
            List<CartModel> cartItems = cartService.getCartItems(buyer);
            model.addAttribute("products", products);
            model.addAttribute("cartItems", cartItems);
            return "buyer_dashboard"; // Displays buyer_dashboard.html template
        } else {
            model.addAttribute("error", "You must be logged in as a buyer to view this page");
            return "login"; // Redirects to login page if buyer is not logged in
        }

    }

    @GetMapping("/products")
    public List<ProductModel> getAllProducts() {
        return productService.getAllItems();
    }

    @GetMapping("/products/search")
    public List<ProductModel> searchProductsByName(@RequestParam String keyword) {
        return productService.searchItemsByName(keyword);
    }

    @GetMapping("/cart/add/{productId}")
    public String showQuantitySelectionPage(@PathVariable Long productId, HttpSession session, Model model) {
        ProductModel product = productService.getItemById(productId);
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (product != null && buyer != null) {
            CartModel cartItem = new CartModel();
            cartItem.setProduct(product); // Set the product association in CartModel
            cartItem.setBuyer(buyer); // Set the buyer association in CartModel
            model.addAttribute("cartItem", cartItem);
            return "buyer_add_to_cart"; // Return the correct template name
        } else {
            // Handle case where product or buyer is not found
            return "error_page";
        }
    }
    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            @ModelAttribute("cartItem") CartModel cartItem,
                            HttpSession session, Model model) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer == null || !"Buyer".equals(buyer.getRole())) {
            model.addAttribute("error", "You must be logged in as a buyer to add items to cart.");
            return "buyer_login_page"; // Redirect to login page if buyer is not logged in
        }

        ProductModel product = productService.getItemById(productId);
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "error_page"; // Handle case where product is not found
        }

        // Initialize cartItem if not already initialized
        if (cartItem == null) {
            cartItem = new CartModel();
        }

        // Set the product and buyer in cartItem
        cartItem.setProduct(product);
        cartItem.setBuyer(buyer);

        // Add the cartItem to the cart
        cartService.addItemToCart(productId, buyer);

        // Redirect to buyer dashboard or cart view
        return "redirect:/buyer/dashboard";
    }

    @GetMapping("/cart")
    public ResponseEntity<List<CartModel>> getCartItems(HttpSession session) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer != null && "Buyer".equals(buyer.getRole())) {
            List<CartModel> cartItems = cartService.getCartItems(buyer);
            return ResponseEntity.ok(cartItems);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/cart/remove/{cartId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartId, HttpSession session) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer != null && "Buyer".equals(buyer.getRole())) {
            cartService.removeItemFromCart(cartId);
            return ResponseEntity.ok("Cart item removed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in as a buyer to remove from cart");
        }
    }

    @PostMapping("/cart/checkout")
    public ResponseEntity<String> checkoutCart(HttpSession session) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer != null && "Buyer".equals(buyer.getRole())) {
            cartService.checkoutCart(buyer);
            return ResponseEntity.ok("Checkout successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in as a buyer to checkout");
        }
    }
}