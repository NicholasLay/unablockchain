package com.ibm.bpm.adira.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibm.bpm.adira.domain.ClaimTaskRequestBean;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class ClaimTaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/claimTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody ClaimTaskRequestBean claimTaskRequest)
	{
<<<<<<< HEAD
		String response = "{\"orderID\":\"" +claimtaskReq.getOrderID()+ "\","+
        		"\"processID\":\"" +claimtaskReq.getProcessID()+ "\","+
        		"\"taskID\":\"" +claimtaskReq.getTaskID()+ "\","+
        		"\"displayName\":\"Submit IDE\","+
				"\"assignToType\":\"IDE\","+
				"\"startTime\":\"1/23/2017  12:00:00 AM\","+
        		"\"status\":\"Complete\""+ 
        		"}";
		return new ResponseEntity(response, new HttpHeaders(),HttpStatus.OK);
=======
		String orderId 	= claimTaskRequest.getOrderID();
		int processId 	= claimTaskRequest.getProcessID();
		int brmsScoring = claimTaskRequest.getBrmsScoring();
		int taskId 		= claimTaskRequest.getTaskID();
		Boolean mayor 	= claimTaskRequest.getMayor();
		
		String logTracker = 
				"From acction: "+ 
				"Order ID ="+orderId+
				"Process ID ="+processId+
				"Task ID = "+taskId+
				"BRMS ="+brmsScoring+
				"Mayor ="+mayor;
		
		logger.info(logTracker);
		
		processService.process(orderId,processId,taskId);
		
		
		return new ResponseEntity("{\"status\": \"Success\"}", new HttpHeaders(),HttpStatus.OK);
>>>>>>> c2a4823890d75341b5afcfce9742b3e18c852e30
	}
	
}


