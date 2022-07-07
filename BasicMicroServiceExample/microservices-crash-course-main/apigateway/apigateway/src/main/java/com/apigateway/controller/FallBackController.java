package com.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {
	
	@GetMapping("/userServiceFallback")
	public String userServiceCallBack() {
		
		return "User service is down at this time";

	}
	
	@GetMapping("/contactServiceFallback")
	public String contactServiceCallBack() {
		
		return "Contact service is down at this time";
	}
}
