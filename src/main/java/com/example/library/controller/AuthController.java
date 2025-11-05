package com.example.library.controller;

import com.example.library.model.Librarian;
import com.example.library.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public String login(@RequestParam String name, @RequestParam String password) {
        Optional<Librarian> user = authService.login(name, password);
        if (user.isPresent()) {
            return "Welcome " + user.get().getName() + "! Role: " + user.get().getRole();
        } else {
            return "Invalid credentials";
        }
    }
}
