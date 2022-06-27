package com.simpleSpringSecurity.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import com.simpleSpringSecurity.entity.User;
import com.simpleSpringSecurity.error.UserNotFoundException;
import com.simpleSpringSecurity.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	private final Logger  log  = LoggerFactory.getLogger(UserController.class);
	@GetMapping("/AllUsers")
	public List<User> getAllUsers() {
		
		return userService.getAllUsers();
	}
	

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") Long userId) throws UserNotFoundException {
		
		return userService.getUserById(userId);

	}
	
	@PostMapping("/addUser")
	public User saveDepartment(@Valid @RequestBody User user) { //because we put the validation into the entity = department for the department name , so that's why we are putting here "@valid"
		log.info("Here : Add User" + user);
		return userService.saveUser(user); 
		}
	
	
	
}
