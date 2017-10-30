package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ClaimTaskController {
	//Test Commit Controller 2 
	@PostMapping("/claimTask")
	public ResponseEntity<?> login(@RequestBody ClaimTaskRequestBean claimtaskReq)
	{
		return new ResponseEntity("Status : Task Claimed", new HttpHeaders(),HttpStatus.OK);
	}
}


