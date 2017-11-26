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
import com.ibm.bpm.adira.domain.CompleteTaskRequestBean;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class CompleteTaskController {
	
private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/completeTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody CompleteTaskRequestBean completeTaskRequest)
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
		
		//Call Async CallBack Process
		String processServiceName = "CompleteTask";
		processService.process(processServiceName,orderId,processId,taskId);
		
		return new ResponseEntity("{\"status\": \"Complete Task Success\"}", new HttpHeaders(),HttpStatus.OK);
	}
}
