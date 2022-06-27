package com.simpleSpringSecurity.service;

import java.util.List;

import com.simpleSpringSecurity.entity.User;
import com.simpleSpringSecurity.error.UserNotFoundException;

public interface UserService {

	public User saveUser(User user);
	
	public List<User> getAllUsers();

	public User getUserById(Long userId) throws UserNotFoundException;

	




	

}
