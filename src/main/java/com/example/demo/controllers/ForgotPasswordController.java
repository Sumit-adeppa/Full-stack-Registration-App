package com.example.demo.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repository.UserRepo;
import com.example.demo.services.OtpService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ForgotPasswordController {

    private final UserRepo userRepo;
    private final OtpService otpService;

    public ForgotPasswordController(UserRepo userRepo, OtpService otpService) {
        this.userRepo = userRepo;
        this.otpService = otpService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @RequestParam String email) {

        if (userRepo.findByEmail(email).isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("error", "Email not registered"));
        }

        otpService.sendOtp(email);

        return ResponseEntity.ok(
                Map.of("message", "OTP sent to registered email"));
    }
}
