package com.ibm.bpm.adira.web;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.bpm.adira.domain.ClaimTaskRequestBean;
import com.ibm.bpm.adira.domain.CompleteTaskRequestBean;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class CompleteTaskController {
	
private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/completeTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CompleteTaskRequestBean completeTaskRequest) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
			
	{

		String orderId 	= completeTaskRequest.getOrderID();
		int processId 	= completeTaskRequest.getProcessID();
		int brmsScoring = completeTaskRequest.getBrmsScoring();
		int taskId 		= completeTaskRequest.getTaskID();
		Boolean mayor 	= completeTaskRequest.getMayor();
		
		String logTracker = 
				"From acction: "+ 
				"Order ID ="+orderId+
				"Process ID ="+processId+
				"Task ID = "+taskId+
				"BRMS ="+brmsScoring+
				"Mayor ="+mayor;
		
		logger.info(logTracker);
		
		if (basicAuth.startsWith("Basic"))
		{
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("USERNAME "+values[0]);
			logger.info("PASSWORD "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String responseAd1Gate = ad1ServiceImpl.authResponse(values[0], values[1]);
			logger.info("--------RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			
			if (responseAd1Gate.equals(GlobalString.OK_MESSAGE))
			{	

				//Call Async CallBack Process
				String processServiceName = "CompleteTask";
				processService.process(processServiceName,orderId,processId,taskId);
				
				return new ResponseEntity("{\"status\": \"Complete Task Success\"}", new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				String processServiceName = "CompleteTask";
				processService.process(processServiceName,orderId,processId,taskId);
				
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}

		String processServiceName = "CompleteTask";
		processService.process(processServiceName,orderId,processId,taskId);
		
		return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
	}
}
