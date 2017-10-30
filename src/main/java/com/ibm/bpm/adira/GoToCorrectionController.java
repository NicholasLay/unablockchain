package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GoToCorrectionController {

	@PostMapping("/goToCorrection")
	public ResponseEntity<?> login(@RequestBody GoToCorrectionRequest goToCorrReq)
	{
		return new ResponseEntity("Status : Allocated to Data Correction", new HttpHeaders(),HttpStatus.OK);
	}
	
}
