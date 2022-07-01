package com.securityspringclient.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity//First step, after adding dependency add this annotation 
public class WebSecurity {

	/*
	 * Second step access the "PasswordEncoder" interface and create
	 * the passwordEncoder function with the object of the   BCryptPasswordEncoder with strength 11.
	 * 
	 * 
	 * Third step : add annotation @Bean
	 */
	
	private static final String[] WHITE_LIST_URLS = {
            "/hello",
            "/register",
            "/verifyRegistration*",
            "/resendVerifyToken*"
    };
		
	
	@Bean
	public PasswordEncoder passwordEncoder() { 
	
		return new BCryptPasswordEncoder(11);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(WHITE_LIST_URLS).permitAll();
		
		 return http.build();
	}
	
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .antMatchers(WHITE_LIST_URLS).permitAll()
//                .antMatchers("/api/**").authenticated()
//                .and()
//                .oauth2Login(oauth2login ->
//                        oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
//                .oauth2Client(Customizer.withDefaults());
//
//        return http.build();
//		}
}