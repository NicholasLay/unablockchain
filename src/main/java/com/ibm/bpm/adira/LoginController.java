package com.ibm.bpm.adira;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController 
{
	@PostMapping("/api/login")
	public ResponseEntity<?> login(@RequestHeader(value="username") String username,
			@RequestHeader(value="password") String password,
			@RequestBody Login login)
	{
		//final String uri = "http://www.nba.com";
		//RestTemplate restTemplate = new RestTemplate();
		//String result = restTemplate.getForObject(uri, String.class);
		Authentication authentication = new Authentication(username,password);
		String result = authentication.getAuthentication();
		HttpStatus status;
		String message = "";
		if (result == "OK")
		{
			status = HttpStatus.OK;
			message = "Login Success";
		}
		else
		{
			status = HttpStatus.FORBIDDEN;
			message = "Login Failed ";
		}
		return new ResponseEntity(message+username+"/"+password+result, new HttpHeaders(),status);
	}
	
}
