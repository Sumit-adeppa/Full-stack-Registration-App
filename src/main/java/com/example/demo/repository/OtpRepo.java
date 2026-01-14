package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.OTP;

public interface OtpRepo extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmail(String email);
}
