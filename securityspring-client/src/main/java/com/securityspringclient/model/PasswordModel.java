package com.securityspringclient.model;

import lombok.Data;

@Data
public class PasswordModel {

    private String email;
    private String oldPassword;
    private String newPassword;
    
	public PasswordModel(String email, String oldPassword, String newPassword) {
		super();
		this.email = email;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public PasswordModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
    
    
}
