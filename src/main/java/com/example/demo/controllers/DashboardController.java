package com.example.demo.controllers;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard(HttpServletRequest request) {

        String email = (String) request.getAttribute("email");

        if (email == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok("Welcome " + email);
    }
}
