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

import com.ibm.bpm.adira.domain.BackToIDERequestBean;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class BackToIDEController 
{
	
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/backToIDE", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody BackToIDERequestBean backToIDERequest)
	{
		String orderId	= backToIDERequest.getOrderID();
		int processId	= backToIDERequest.getProcessID();
		int brmsScoring = backToIDERequest.getBrmsScoring();
		int taskId		= backToIDERequest.getTaskID();
		Boolean mayor	= backToIDERequest.getMayor();
		
		String logTracker = 
				"From acction: "+ 
				"Order ID ="+orderId+
				"Process ID ="+processId+
				"Task ID = "+taskId+
				"BRMS ="+brmsScoring+
				"Mayor ="+mayor;
		
		logger.info(logTracker);
		
		processService.process("BackToIDE",orderId,processId,taskId);
		
		
		return new ResponseEntity("{\"status\": \"Success\"}", new HttpHeaders(),HttpStatus.OK);
	}
	
}
	
