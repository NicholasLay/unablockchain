package com.ibm.bpm.adira.web;

import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.ibm.bpm.adira.domain.BackToIDERequestBean;
import com.ibm.bpm.adira.domain.Login;
import com.ibm.bpm.adira.domain.UrlLoader;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class LoginController 
{
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/api/login", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody BackToIDERequestBean backToIDERequest)
	{
		logger.info("From acction: Order ID ="+backToIDERequest.getOrderID()+
				"Process ID"+backToIDERequest.getProcessID()+"Task ID"+backToIDERequest.getTaskID()
			+"BRMS"+backToIDERequest.getBrmsScoring()+"Mayor"+backToIDERequest.getMayor());
		processService.process(backToIDERequest.getOrderID(),
				backToIDERequest.getProcessID(),backToIDERequest.getTaskID());
		return new ResponseEntity("{\"status\": \"Success\"}", new HttpHeaders(),HttpStatus.OK);
		//return new ResponseEntity<List<JSONObject>>(HttpStatus.OK);
	}
	
}
