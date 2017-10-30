package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CompleteTaskController {
	@PostMapping("/completeTask")
	public ResponseEntity<?> login(@RequestBody CompleteTaskRequestBean completeTaskReq)
	{
		return new ResponseEntity("Status : Task Completed", new HttpHeaders(),HttpStatus.OK);
	}
}
