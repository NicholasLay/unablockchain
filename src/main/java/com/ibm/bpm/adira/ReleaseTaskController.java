package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReleaseTaskController {

	@PostMapping("/releaseTask")
	public ResponseEntity<?> login(@RequestBody ReleaseTaskRequestBean releaseTaskReq)
	{
		return new ResponseEntity("Status : Task Released", new HttpHeaders(),HttpStatus.OK);
	}
	
	
}
