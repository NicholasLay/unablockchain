package com.ibm.bpm.adira.web;

public class Login 
{
	public String username;
	public String password;
	
	public Login()
	{
		this.username = username;
		this.password = password;
	}
	
	public Login(String username,String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}
}
