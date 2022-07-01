package com.securityspringclient.Event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.securityspringclient.entity.User;


public class RegisterationCompleteEvent extends ApplicationEvent { //Step # 01 : extends  ApplicationEvent

	//step # 02 F2 on "RegisterationCompleteEvent" and implement the object function and assigned to the User and applicationUrl
	//step # 03  Create data filed  User ,applicationUrl
	//Step # 04 add this into the parameterized constructor
	
	private User user;
	private String applicationUrl;
	
	public RegisterationCompleteEvent(User user, String applicationUrl ) {
		super(user);
		this.user = user;
		this.applicationUrl = applicationUrl;
		
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	

}
