package com.securityspringclient.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securityspringclient.entity.PasswordResetToken;
import com.securityspringclient.entity.User;
import com.securityspringclient.entity.VerificationToken;
import com.securityspringclient.model.UserModel;
import com.securityspringclient.repository.PasswordResetTokenRepository;
import com.securityspringclient.repository.UserRepository;
import com.securityspringclient.repository.VerificationTokenRepository;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	
	 @Autowired
	    private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override
	public User registerUser(UserModel userModel) {
		
		User user = new User();
		user.setEmail(userModel.getEmail()); 
		user.setFirstName(userModel.getFirstName());
		user.setLastName(userModel.getLastName());
		user.setRole("USER");
		user.setPassword(passwordEncoder.encode(userModel.getPassword())); //to encrypt the password used encoder
		
		userRepository.save(user);
 	 
		return user;
	}

	@Override
	public void saveVerificationTokenforService(String token, User user) {
		
		VerificationToken verToken = new VerificationToken(user, token);
		verificationTokenRepository.save(verToken); 
		
		
		
	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

		if (verificationToken == null) {
			return "invalid";
		}

		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
			verificationTokenRepository.delete(verificationToken);
			return "expired";
		}

		user.setEnabled(true);
		userRepository.save(user);
		return "valid";
	}
	
	@Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken
                = new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

	@Override
	
	    public String validatePasswordResetToken(String token) {
	        PasswordResetToken passwordResetToken
	                = passwordResetTokenRepository.findByToken(token);

	        if (passwordResetToken == null) {
	            return "invalid";
	        }

	        User user = passwordResetToken.getUser();
	        Calendar cal = Calendar.getInstance();

	        if ((passwordResetToken.getExpirationTime().getTime()
	                - cal.getTime().getTime()) <= 0) {
	            passwordResetTokenRepository.delete(passwordResetToken);
	            return "expired";
	        }

	        return "valid";
	    }
}