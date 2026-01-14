package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Role;
import com.example.demo.entity.Users;
import com.example.demo.services.RegisterService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {

        try {
            Users user = new Users();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(Role.USER);

            registerService.register(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("message", "User registered successfully. OTP sent"));

        } catch (RuntimeException ex) {
            // Duplicate email case
            if (ex.getMessage().contains("already exists")) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "This email is already registered"));
            }

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", ex.getMessage()));
        }
    }
}
