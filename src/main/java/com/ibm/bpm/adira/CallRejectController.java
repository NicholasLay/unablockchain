package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CallRejectController {
	
	@PostMapping("/callReject")
	public ResponseEntity<?> login(@RequestBody CallRejectRequestBean callRejReq)
	{
		return new ResponseEntity("Status : Task Rejected", new HttpHeaders(),HttpStatus.OK);
	}
}
