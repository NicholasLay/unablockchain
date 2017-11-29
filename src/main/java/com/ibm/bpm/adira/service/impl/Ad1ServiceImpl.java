package com.ibm.bpm.adira.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.Ad1AuthRequest;
import com.ibm.bpm.adira.service.Ad1Service;

public class Ad1ServiceImpl implements Ad1Service
{

	private static final String AD1_URL =  "http://10.50.1.18:99/Portsightdev/api/AuthenticateUser";
	@Override
	public String authResponse(String login, String password) 
	{
		Gson json = new Gson();
		String ad1Request = "";
		Ad1AuthRequest ad1AuthRequest = new Ad1AuthRequest();
		ad1AuthRequest.setLogin(login);
		ad1AuthRequest.setPassword(password);
		ad1Request = json.toJson(ad1AuthRequest);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(ad1Request,httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(AD1_URL, entity, String.class);
		return response;
	}

}
