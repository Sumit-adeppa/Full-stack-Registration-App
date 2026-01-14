package com.example.demo.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepo;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final OtpService otpService;

    public RegisterServiceImpl(UserRepo userRepo, BCryptPasswordEncoder encoder, OtpService otpService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.otpService = otpService;
    }

    @Override
    public Users register(Users user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Entered email already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setEnabled(false);

        Users savedUser = userRepo.save(user);
        otpService.sendOtp(savedUser.getEmail());

        return savedUser;
    }
}
