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

import com.ibm.bpm.adira.domain.StartProcessRequestBean;

@Controller
public class StartProcessController 
{
	@RequestMapping(value="/startProcessIDE", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody StartProcessRequestBean startProcess)
	{
		String response = "{\"orderID\":\"" +startProcess.getOrderID()+ "\","+
        		"\"processID\":\"" +startProcess.getProcessID()+ "\","+
        		"\"taskID\":\"" +startProcess.getTaskID()+ "\","+
        		"\"displayName\":\"Submit IDE\","+
				"\"assignToType\":\"IDE\","+
				"\"startTime\":\"1/23/2017  12:00:00 AM\","+
        		"\"status\":\"Complete\""+ 
        		"}";
		return new ResponseEntity(response, new HttpHeaders(),HttpStatus.OK);
	}
	
}
