package com.ibm.bpm.adira;

public class Authentication 
{
	private String username;
	private String password;
	
	public Authentication(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getAuthentication()
	{
		if (username .equals("70000386") && password.equals("adira"))
		{
			return "OK";
		}
		else
		{
			return "NOK";
		}
	}
}
