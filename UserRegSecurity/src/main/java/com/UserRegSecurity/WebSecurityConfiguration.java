package com.UserRegSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

	//userDetailService spring interface that helps to authenticate 
	@Autowired
	private UserDetailsService detailsService;
	

	/*
	 * before this method add encrypted password into the database
	 */
	
	@Bean
	//this method is telling that we need to bypass the spring generated credential, instead use from the database
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		//implement user detail service
		provider.setUserDetailsService(detailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		return provider;
		
	}
	
	protected void configure(HttpSecurity http) throws Exception  {
		http.authorizeRequests()
			.antMatchers("/")
			.permitAll()
			.antMatchers("/home")
			.hasAuthority("user")
			.antMatchers("/admin")
			.hasAuthority("admin")
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
			

	}

}
