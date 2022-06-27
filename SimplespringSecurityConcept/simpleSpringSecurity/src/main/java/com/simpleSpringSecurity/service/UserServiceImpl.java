package com.simpleSpringSecurity.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.simpleSpringSecurity.controller.UserController;
import com.simpleSpringSecurity.entity.User;
import com.simpleSpringSecurity.error.UserNotFoundException;
import com.simpleSpringSecurity.repository.UserRepository;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	

	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long userId) throws UserNotFoundException {
		
		Optional<User> depbyId = userRepository.findById(userId);
		
		if (!depbyId.isPresent()) {
			
			throw new  UserNotFoundException("User not found !!!");
		}
		
		return depbyId.get();
	}

	@Override
	public User saveUser(User user) {
		
		return userRepository.save(user);
	}




}
