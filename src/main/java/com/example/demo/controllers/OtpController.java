package com.example.demo.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.OtpRequest;
import com.example.demo.entity.OTP;
import com.example.demo.entity.Users;
import com.example.demo.repository.OtpRepo;
import com.example.demo.repository.UserRepo;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OtpController {

    private final OtpRepo otpRepo;
    private final UserRepo userRepo;

    public OtpController(OtpRepo otpRepo, UserRepo userRepo) {
        this.otpRepo = otpRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {

        OTP otp = otpRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(410).body("OTP expired");
        }

        if (!otp.getCode().equals(request.getOtp())) {
            return ResponseEntity.status(400).body("Invalid OTP");
        }

        Users user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEnabled(true);
        userRepo.save(user);
        otpRepo.delete(otp);

        return ResponseEntity.ok("Account verified successfully");
    }
}
