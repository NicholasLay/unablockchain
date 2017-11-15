package com.ibm.bpm.adira.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.ibm.bpm.adira.domain.BasicAuthRestTemplate;
import com.ibm.bpm.adira.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Async("processExecutor")
    @Override
    public void process(String orderID,int processID,int taskID) {
        logger.info("Received request to process in ProcessServiceImpl.process()");
        
        String username = "70000386";
        String password = "adira";
        
        BasicAuthRestTemplate restTemplate = new BasicAuthRestTemplate(username, password);
       
        String url = "http://localhost:8080/gs-rest-service-0.1.0/backToIDE";
<<<<<<< HEAD
        String acctionUrl = "http://10.61.5.247:9091/adira-acction/acction/service/bpm/callback";
        String acctionCallback = "{\"orderID\":\"" +orderID+ "\","+
        		"\"processID\":\"" +processID+ "\","+
        		"\"taskID\":\"" +taskID+ "\","+
        		"\"status\":\"onprogress\","+
				"\"displayName\":\"sa\","+
        		"\"assignToType\":\"sa\""+ 
        		"}";
=======
     
>>>>>>> c2a4823890d75341b5afcfce9742b3e18c852e30
        String requestJson = "{\"orderID\":\"" +orderID+ "\","+
        		"\"processID\":\"" +processID+ "\","+
        		"\"taskID\":\"" +taskID+ "\","+
        		"\"mayor\":true,"+
        		"\"brmsScoring\":1"+ 
        		"}";
        
        logger.info("Callback Request:"+requestJson);
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        
        String answer = restTemplate.postForObject(url, entity, String.class);
        System.out.println(answer);
        logger.info("Processing complete");
    }
}