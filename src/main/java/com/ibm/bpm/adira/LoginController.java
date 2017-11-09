package com.ibm.bpm.adira;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Entity;

@Controller
public class LoginController 
{
	@RequestMapping(value="/api/login", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody Login login)
	{
		//final String uri = "https://af-newbpmdev:9443/rest/bpm/wle/v1/task/79?parts=all";
		//RestTemplate restTemplate = new RestTemplate();
		//String result = restTemplate.getForObject(uri, String.class);
		/*
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
		*/
		/*
		List<Entity> entityList = entityManager.findAll();

		List<JSONObject> entities = new ArrayList<JSONObject>();
	    for (Entity n : entityList) {
	        JSONObject entity = new JSONObject();
	        entity.put("id", n.getId());
	        entity.put("address", n.getAddress());
	        entities.add(entity);
	    }
*/
		return new ResponseEntity("{\"status\": \"Success\"}", new HttpHeaders(),HttpStatus.OK);
		//return new ResponseEntity<List<JSONObject>>(HttpStatus.OK);
	}
	
}
