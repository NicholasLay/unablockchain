package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClaimTaskController {
	
	@RequestMapping(value="/claimTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody ClaimTaskRequestBean claimtaskReq)
	{
		return new ResponseEntity("{\"status\": \"Success\"}", new HttpHeaders(),HttpStatus.OK);
	}
}


