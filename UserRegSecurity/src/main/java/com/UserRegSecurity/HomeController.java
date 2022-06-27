package com.UserRegSecurity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController

public class HomeController {

	private UserService service;
	
	@GetMapping("/home")
	public String homepage() {
		
		return "This is home page";
	}
	
	@GetMapping("/welcome")
	public String admin() {
		
		return "Welcome Page";

	}

	
}
