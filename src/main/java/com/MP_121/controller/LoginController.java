package com.MP_121.controller;

import com.MP_121.model.UsersModel;
import com.MP_121.service.AuthenticationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-role-choose";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<UsersModel> userOptional = authenticationService.authenticate(email, password);

        if (userOptional.isPresent()) {
            UsersModel user = userOptional.get();
            String role = user.getRole();

            if ("BUYER".equals(role)) {
                // Redirect to buyer dashboard
                return "redirect:/buyer/dashboard";
            } else if ("SELLER".equals(role)) {
                // Redirect to seller dashboard
                return "redirect:/seller/dashboard";
            } else {
                // Handle other roles if necessary
                return "redirect:/login?error=role";
            }
        } else {
            // Handle authentication failure
            return "redirect:/login?error=auth";
        }
    }

    @GetMapping("/buyer/login")
    public String showBuyerLoginForm(Model model) {
        return "buyer_login_page"; // buyer/login.html template
    }

    @GetMapping("/seller/login")
    public String showSellerLoginForm(Model model) {
        return "seller_login_page"; // seller/login.html template
    }

    @PostMapping("/authenticate-buyer")
    public String authenticateBuyer(@RequestParam String email, @RequestParam String password, Model model) {
        return authenticateUser(email, password, model);
    }

    @PostMapping("/authenticate-seller")
    public String authenticateSeller(@RequestParam String email, @RequestParam String password, Model model) {
        return authenticateUser(email, password, model);
    }

}
