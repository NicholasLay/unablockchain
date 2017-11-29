package com.ibm.bpm.adira.web;


import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.Ad1AuthRequest;
import com.ibm.bpm.adira.domain.StartProcessRequestBean;
import com.ibm.bpm.adira.domain.StartProcessResponseBean;
import com.ibm.bpm.adira.domain.StartProcessResponseToAcction;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

@Controller
public class LoginController 
{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private static final String OK_MESSAGE = "OK";
	private static final String EMPTY_STRING = "";
	private static final int EMPTY_INTEGER = 0;
	
	@RequestMapping(value="/demo", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody StartProcessRequestBean startProcessRequest)
	{
		Gson jsonParser = new Gson();
		StartProcessResponseToAcction startProcessResponse = new StartProcessResponseToAcction();
		String sendToAcction= "";
		if (basicAuth.startsWith("Basic"))
		{
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials),
	                Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("USERNAME "+values[0]);
			logger.info("PASSWORD "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String response = ad1ServiceImpl.authResponse(values[0], values[1]);
			logger.info("RESPONSE dari ad1 "+ response);
			
			if (response.equals(OK_MESSAGE))
			{	
				startProcessResponse.setOrderID("123");
				startProcessResponse.setProcessID(123);
				startProcessResponse.setProcessInstanceName("123");
				startProcessResponse.setDisplayName("123");
				startProcessResponse.setTaskID(123);
				startProcessResponse.setAssignedToType("123");
				startProcessResponse.setAssignTo("123");
				startProcessResponse.setStartTime("123");
				startProcessResponse.setDueTime("123");
				sendToAcction = jsonParser.toJson(startProcessResponse);
				return new ResponseEntity(sendToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				startProcessResponse.setOrderID(EMPTY_STRING);
				startProcessResponse.setProcessID(EMPTY_INTEGER);
				startProcessResponse.setProcessInstanceName(EMPTY_STRING);
				startProcessResponse.setDisplayName(EMPTY_STRING);
				startProcessResponse.setTaskID(EMPTY_INTEGER);
				startProcessResponse.setAssignedToType(EMPTY_STRING);
				startProcessResponse.setAssignTo(EMPTY_STRING);
				startProcessResponse.setStartTime(EMPTY_STRING);
				startProcessResponse.setDueTime(EMPTY_STRING);
				sendToAcction = jsonParser.toJson(startProcessResponse);
				
				return new ResponseEntity(sendToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}
		startProcessResponse.setOrderID(EMPTY_STRING);
		startProcessResponse.setProcessID(EMPTY_INTEGER);
		startProcessResponse.setProcessInstanceName(EMPTY_STRING);
		startProcessResponse.setDisplayName(EMPTY_STRING);
		startProcessResponse.setTaskID(EMPTY_INTEGER);
		startProcessResponse.setAssignedToType(EMPTY_STRING);
		startProcessResponse.setAssignTo(EMPTY_STRING);
		startProcessResponse.setStartTime(EMPTY_STRING);
		startProcessResponse.setDueTime(EMPTY_STRING);
		sendToAcction = jsonParser.toJson(startProcessResponse);
		return new ResponseEntity(sendToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
		
	}
}
