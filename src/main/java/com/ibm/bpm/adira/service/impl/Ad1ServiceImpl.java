package com.ibm.bpm.adira.service.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.Ad1AuthRequest;
import com.ibm.bpm.adira.domain.Ad1NIKRequest;
import com.ibm.bpm.adira.service.Ad1Service;
import com.ibm.bpm.adira.web.StartProcessController;

public class Ad1ServiceImpl implements Ad1Service
{

	private static final String AD1_URL_NIK =  "http://10.50.1.18:99/Portsightdev/api/GetDistinctListPersonByGroupLocation";
	private static final String AD1_URL_AUTH =  "http://10.50.1.18:99/Portsightdev/api/AuthenticateUser";
	private static final Logger logger = LoggerFactory.getLogger(Ad1ServiceImpl.class);
	
	@Override
	public String authResponse(String login, String password) 
	{
		Gson json = new Gson();
		String ad1Request = "";
		Ad1AuthRequest ad1AuthRequest = new Ad1AuthRequest();
		ad1AuthRequest.setLogin(login);
		ad1AuthRequest.setPassword(password);
		ad1Request = json.toJson(ad1AuthRequest);
		logger.info("[Ad1ServiceImpl] Request to Ad1 from API."+ ad1Request);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(ad1Request,httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		String response = restTemplate.postForObject(AD1_URL_AUTH, entity, String.class);

		return response;
	}

	@Override
	public String getNIK(ArrayList<String> GroupAlias, String LocationAlias, boolean isAlias) {
		// TODO Auto-generated method stub
		Gson json = new Gson();
		String ad1Request = "";
		
		Ad1NIKRequest ad1NIKRequest = new Ad1NIKRequest();
		ad1NIKRequest.setGroupAlias(GroupAlias);
		ad1NIKRequest.setLocationAlias(LocationAlias);
		ad1NIKRequest.setIsAlias(isAlias);
		
		ad1Request = json.toJson(ad1NIKRequest);
		
		logger.info("[Ad1ServiceImpl] Request to Ad1 from API."+ ad1Request);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(ad1Request,httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		
		String response = restTemplate.postForObject(AD1_URL_NIK, entity, String.class);
		
		return response;
	}

}
