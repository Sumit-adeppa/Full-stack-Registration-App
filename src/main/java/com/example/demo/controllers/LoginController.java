package com.example.demo.controllers;

import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entity.Users;
import com.example.demo.services.LoginService;
import com.example.demo.util.JwtUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {

    private final LoginService loginService;
    private final JwtUtil jwtUtil;

    public LoginController(LoginService loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody(required = false) LoginRequest request,
            HttpServletRequest httpRequest) {

        // ✅ Handle missing body safely
        if (request == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Request body is missing"));
        }

        // ✅ If valid JWT already exists, do NOT generate new one
        if (httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                if ("JWT".equals(cookie.getName())
                        && jwtUtil.isTokenValid(cookie.getValue())) {

                    return ResponseEntity.ok(
                            Map.of("message", "Already logged in"));
                }
            }
        }

        // Normal login
        Users user = loginService.login(
                request.getEmail(),
                request.getPassword());

        String token = jwtUtil.generateToken(user);

        ResponseCookie cookie = ResponseCookie.from("JWT", token)
                .httpOnly(true)
                .secure(false) // true in prod
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body(Map.of("message", "Login successful"));
    }
}
