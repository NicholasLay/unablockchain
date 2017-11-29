package com.ibm.bpm.adira.domain;

public class Ad1AuthRequest 
{
	private String login;
	private String password;
	
	public String getUsername() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
