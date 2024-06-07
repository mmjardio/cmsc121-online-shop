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
import java.util.stream.Collectors;

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
    public String showDashboard(@RequestParam(value = "search", required = false) String search, Model model, HttpSession session) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer == null || !"Buyer".equals(buyer.getRole())) {
            model.addAttribute("error", "You must be logged in as a buyer to view the dashboard.");
            return "buyer_login_page";
        }

        List<ProductModel> products;
        if (search != null && !search.isEmpty()) {
            products = productService.searchItemsByName(search);
        } else {
            products = productService.getAllItems();
        }

        List<CartModel> cartItems = cartService.getCartItemsByBuyer(buyer);

        List<Double> cartCosts = cartItems.stream()
                .map(item -> item.getProduct().getPrice() * item.getQuantity())
                .collect(Collectors.toList());

        double totalCost = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();

        model.addAttribute("products", products);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalCost", totalCost);
        return "buyer_dashboard";
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

        if (cartItem.getQuantity() < 0) {
            model.addAttribute("error", "Quantity cannot be negative.");
            return "error_page"; // Handle invalid quantity
        }

        // Set the product and buyer in cartItem
        cartItem.setProduct(product);
        cartItem.setBuyer(buyer);

        // Add the cartItem to the cart with the specified quantity
        cartService.addItemToCart(productId, buyer, cartItem.getQuantity());

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

    @RequestMapping(value="/cart/remove/{cartId}", method={RequestMethod.DELETE, RequestMethod.GET})
    public String removeCartItem(@PathVariable Long cartId, HttpSession session) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer != null && "Buyer".equals(buyer.getRole())) {
            cartService.removeItemFromCart(cartId);
            return "redirect:/buyer/dashboard"; // Redirect to buyer dashboard
        } else {
            // Handle unauthorized access
            return "redirect:/login"; // Redirect to login page or error page
        }
    }

    @PostMapping("/cart/checkout")
    public String checkoutCart(HttpSession session, Model model) {
        UsersModel buyer = (UsersModel) session.getAttribute("user");
        if (buyer == null || !"Buyer".equals(buyer.getRole())) {
            model.addAttribute("error", "You must be logged in as a buyer to checkout.");
            return "buyer_login_page"; // Redirect to login page if buyer is not logged in
        }

        // Perform the checkout process
        cartService.checkoutCart(buyer);

        // Redirect to buyer dashboard or order confirmation page
        return "redirect:/buyer/dashboard"; // or "order_confirmation_page"
    }
}