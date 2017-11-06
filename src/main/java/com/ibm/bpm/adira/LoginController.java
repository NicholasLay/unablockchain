package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController 
{
	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestBody Login login)
	{
		final String uri = "http://www.nba.com";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return new ResponseEntity("Successfully Login"+result, new HttpHeaders(),HttpStatus.OK);
	}
	
}
