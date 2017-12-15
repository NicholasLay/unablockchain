package com.ibm.bpm.adira.web;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.Ad1AuthRequest;
import com.ibm.bpm.adira.domain.Ad1AuthResponse;

/*
 * 	MicroService to Ad1 for authentication.
 */

@Controller
public class Ad1AuthController 
{
	private static final String AD1_URL =  "http://10.50.1.18:99/Portsightdev/api/AuthenticateUser";
	private static final Logger logger = LoggerFactory.getLogger(Ad1AuthController.class);
	
	@RequestMapping(value="/ad1", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> auth(@RequestBody Ad1AuthRequest ad1AuthRequest) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
	{
		Gson json = new Gson();
		String ad1Request = "";
		ad1Request = json.toJson(ad1AuthRequest);
		logger.info("[Ad1AuthController] Request to Ad1 from API : " + ad1Request);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(ad1Request,httpHeaders);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.postForObject(AD1_URL, entity, String.class);
		Ad1AuthResponse ad1Response = new Ad1AuthResponse();
		ad1Response.setLoginStatus(response);
		response = json.toJson(ad1Response);
		logger.info("[Ad1AuthController] Response from Ad1 to API : " + response);
		return new ResponseEntity(ad1Response, new HttpHeaders(),HttpStatus.OK);
	}
}
