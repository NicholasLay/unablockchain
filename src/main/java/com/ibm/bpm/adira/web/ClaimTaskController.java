package com.ibm.bpm.adira.web;

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

@Controller
public class ClaimTaskController {
	
	@RequestMapping(value="/claimTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody ClaimTaskRequestBean claimtaskReq)
	{
		String response = "{\"orderID\":\"" +claimtaskReq.getOrderID()+ "\","+
        		"\"processID\":\"" +claimtaskReq.getProcessID()+ "\","+
        		"\"taskID\":\"" +claimtaskReq.getTaskID()+ "\","+
        		"\"displayName\":\"Submit IDE\","+
				"\"assignToType\":\"IDE\","+
				"\"startTime\":\"1/23/2017  12:00:00 AM\","+
        		"\"status\":\"Complete\""+ 
        		"}";
		return new ResponseEntity(response, new HttpHeaders(),HttpStatus.OK);
	}
}


