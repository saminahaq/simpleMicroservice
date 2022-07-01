package com.securityspringclient.service;

import java.util.Optional;

import com.securityspringclient.entity.User;
import com.securityspringclient.entity.VerificationToken;
import com.securityspringclient.model.UserModel;

public interface UserService {

	 User registerUser(UserModel userModel);

	void saveVerificationTokenforService(String token, User user);

	String validateVerificationToken(String token);

	VerificationToken generateNewVerificationToken(String oldToken);

	User findUserByEmail(String email);

	void createPasswordResetTokenForUser(User user, String token);

	Optional<User> getUserByPasswordResetToken(String token);

	void changePassword(User user, String newPassword);

	boolean checkIfValidOldPassword(User user, String oldPassword);

	String validatePasswordResetToken(String token); 

}
 