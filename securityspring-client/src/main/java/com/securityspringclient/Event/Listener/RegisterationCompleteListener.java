package com.securityspringclient.Event.Listener;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.securityspringclient.Event.RegisterationCompleteEvent;
import com.securityspringclient.entity.User;
import com.securityspringclient.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
//Step # 01 : extends  ApplicationListener with "RegisterationCompleteEvent"
public class RegisterationCompleteListener implements 
						ApplicationListener<RegisterationCompleteEvent> {


	Logger log = LoggerFactory.getLogger(RegisterationCompleteListener.class);
	@Autowired
	private UserService userService;
	
	@Override
	public void onApplicationEvent(RegisterationCompleteEvent event) {
		//step # 02 : Create he verification Token for the user with Link
		//put add getter and setter  at RegisterationCompleteEvent class
		 
		User user = event.getUser();
		//step # 03 :now create the token and that individual token for the individual user matched in order to success sign-in
		
		String token = UUID.randomUUID().toString();
		
		//step # 04 : now create the verificationToken Entity
		//also uptill now we have the token and user as well
		/*
		 * and ready to create the userService [autowired] and 
		 * crate a function saveVerificationTokenforService and follwo the pattren of entity 
		 * repository interface of verificationToken Entity 
		 * autowired at UserserviceImp
		 */
		userService.saveVerificationTokenforService(token, user); //done
	
		//Step # 0d : send email to user
		//Send Mail to user
        String url =
                event.getApplicationUrl()
                        + "/verifyRegistration?token="
                        + token;

        //sendVerificationEmail()
        log.info("Click the link to verify your account :" + url);
     
      
	}
	 
	

}
