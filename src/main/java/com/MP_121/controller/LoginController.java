package com.MP_121.controller;

import com.MP_121.model.UsersModel;
import com.MP_121.service.AuthenticationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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

    @GetMapping("/buyer/login")
    public String showBuyerLoginForm(Model model) {
        return "buyer_login_page"; // buyer/login.html template
    }

    @GetMapping("/seller/login")
    public String showSellerLoginForm(Model model) {
        return "seller_login_page"; // seller/login.html template
    }

    @PostMapping("/authenticate-seller")
    public String authenticateSeller(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<UsersModel> userOptional = authenticationService.authenticate(email, password);
        if (userOptional.isPresent() && "Seller".equals(userOptional.get().getRole())) {
            return "redirect:/seller/dashboard";
        } else {
            return "redirect:/seller/login";
        }
    }

    @PostMapping("/authenticate-buyer")
    public String authenticateBuyer(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<UsersModel> userOptional = authenticationService.authenticate(email, password);
        if (userOptional.isPresent()) {
            UsersModel user = userOptional.get();
            if ("Buyer".equals(user.getRole())) {
                return "redirect:/buyer/dashboard";
            } else {
                // Redirect to a specific error page for role mismatch
                return "redirect:/buyer/login";
            }
        } else {
            // Handle authentication failure
            return "redirect:/login?error=auth";
        }
    }

}
