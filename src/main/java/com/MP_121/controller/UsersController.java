package com.MP_121.controller;

import com.MP_121.model.UsersModel;
import com.MP_121.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public UsersModel registerUser(@RequestParam String login, @RequestParam String email, @RequestParam String password, @RequestParam String role) {
        return usersService.registerUser(login, email, password, role);
    }

    @PostMapping("/login")
    public UsersModel authenticateUser(@RequestParam String email, @RequestParam String password) {
        return usersService.authenticate(email, password);
    }


}
