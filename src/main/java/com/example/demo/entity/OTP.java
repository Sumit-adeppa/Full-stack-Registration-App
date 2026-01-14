package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "otp")
public class OTP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	@Column
	private String code;
	
	@Column
	private LocalDateTime expiresAt;
	
	public OTP() {
		// TODO Auto-generated constructor stub
	}

	public OTP(String email, String code, LocalDateTime expiresAt) {
		super();
		this.email = email;
		this.code = code;
		this.expiresAt = expiresAt;
	}

	public OTP(Long id, String email, String code, LocalDateTime expiresAt) {
		super();
		this.id = id;
		this.email = email;
		this.code = code;
		this.expiresAt = expiresAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, email, expiresAt, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OTP other = (OTP) obj;
		return Objects.equals(code, other.code) && Objects.equals(email, other.email)
				&& Objects.equals(expiresAt, other.expiresAt) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OTP [id=" + id + ", email=" + email + ", code=" + code + ", expiresAt=" + expiresAt + "]";
	}

}
