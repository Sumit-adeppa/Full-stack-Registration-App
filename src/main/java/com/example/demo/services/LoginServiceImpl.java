package com.example.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepo;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    public LoginServiceImpl(UserRepo userRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public Users login(String email, String password) {

        Users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify OTP before login");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }
}
