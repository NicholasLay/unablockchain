package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class StartProcessController 
{
	@PostMapping("/startProcessIDE")
	public ResponseEntity<?> login(@RequestBody StartProcessRequestBean startProcess)
	{
		return new ResponseEntity("Successfully Login", new HttpHeaders(),HttpStatus.OK);
	}
	
}
