package com.UserRegSecurity;

public class UserModel {

	private String userName;
	private String password;
	private String role;
	public UserModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserModel(String userName, String password, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserModel [userName=" + userName + ", password=" + password + ", role=" + role + "]";
	}
	
	
}
