package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BackToIDEController {
	@PostMapping("/backToIDE")
	public ResponseEntity<?> login(@RequestBody BackToIDERequestBean backToIDEReq)
	{
		return new ResponseEntity("Status : Task has been sucesfully directed to IDE", new HttpHeaders(),HttpStatus.OK);
	}
}
