package com.ibm.bpm.adira.service;

import java.util.ArrayList;

/*
 * Class for handling service in Ad1 Microservice.
 */

public interface Ad1Service 
{
	String authResponse(String login,String password);
	
	String getNIK(ArrayList<String> GroupAlias,String LocationAlias, boolean isAlias);
}
