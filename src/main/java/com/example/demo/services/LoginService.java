package com.example.demo.services;

import com.example.demo.entity.Users;

public interface LoginService {

    Users login(String email, String password);
}
