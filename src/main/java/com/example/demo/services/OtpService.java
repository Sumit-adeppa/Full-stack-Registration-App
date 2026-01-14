package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OTP;
import com.example.demo.repository.OtpRepo;

@Service
public class OtpService {

    private final OtpRepo otpRepo;
    private final JavaMailSender mailSender;

    public OtpService(OtpRepo otpRepo, JavaMailSender mailSender) {
        this.otpRepo = otpRepo;
        this.mailSender = mailSender;
    }

    public void sendOtp(String email) {

        otpRepo.findByEmail(email).ifPresent(otpRepo::delete);

        String code = String.valueOf(100000 + new Random().nextInt(900000));
        System.out.println(code);
        OTP otp = new OTP();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        otpRepo.save(otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP Verification");
        message.setText("Your OTP is: " + code);

        mailSender.send(message);
    }
}
