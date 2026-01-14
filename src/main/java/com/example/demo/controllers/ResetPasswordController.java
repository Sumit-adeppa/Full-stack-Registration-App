package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ResetPasswordRequest;
import com.example.demo.entity.OTP;
import com.example.demo.entity.Users;
import com.example.demo.repository.OtpRepo;
import com.example.demo.repository.UserRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ResetPasswordController {

    private final UserRepo userRepo;
    private final OtpRepo otpRepo;
    private final BCryptPasswordEncoder encoder;

    public ResetPasswordController(
            UserRepo userRepo,
            OtpRepo otpRepo,
            BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.encoder = encoder;
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        OTP otp = otpRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity
                    .status(410)
                    .body(Map.of("error", "OTP expired"));
        }

        if (!otp.getCode().equals(request.getOtp())) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("error", "Invalid OTP"));
        }

        Users user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepo.save(user);

        otpRepo.delete(otp);

        return ResponseEntity.ok(
                Map.of("message", "Password reset successful"));
    }
}
