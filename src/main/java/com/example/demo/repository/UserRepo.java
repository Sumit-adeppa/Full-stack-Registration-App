package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmail(String email);
}
