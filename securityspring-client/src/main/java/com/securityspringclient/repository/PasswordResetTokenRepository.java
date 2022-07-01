package com.securityspringclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securityspringclient.entity.PasswordResetToken;
import com.securityspringclient.entity.VerificationToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

	PasswordResetToken findByToken(String token);
	

}
