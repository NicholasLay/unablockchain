package com.una.core.app.web;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.una.core.app.general.GlobalString;
import com.una.core.app.general.PropertiesLoader;
import com.una.core.app.model.GeneralParameterBean;
import com.una.core.app.model.TestingBean;
import com.una.core.app.service.TestingService;


@Controller
@RestController
@RequestMapping("/testing")
public class TestingClassController {
	
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(TestingClassController.class);
	
	@Autowired
    private TestingService testingService;
	
	@RequestMapping(value=GlobalString.EXAMPLE_URL, method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody TestingBean testingBean) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
			
	{	
		GeneralParameterBean generalBean = new GeneralParameterBean();
		Gson jsonTest 	= new Gson();
		String userName = testingBean.getEmployeeName();
		int userID 		= testingBean.getEmployeeID();
		String userRole = testingBean.getEmployeeRole();
		
//		propertiesLoader = new PropertiesLoader();
//		String url 		 = propertiesLoader.loadProperties("url");
//		String testURL 	 = "";
    	
//		logger.info("[TestingController] URL:"+testURL);
		
//		String plainCreds = propertiesLoader.loadProperties("plaincreds");
	 
		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.add("Authorization", "Basic " + plainCreds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		 
		try{
			logger.info("-----[TestingController] BASIC AUTHORIZATION COMPLETE, PROCESSING TASK------");	
	    
			testingService.addUser(testingBean);
			
			generalBean.setStatus("200- Commit Success");
			
			String responseBack = jsonTest.toJson(generalBean);
			
			return new ResponseEntity(responseBack, new HttpHeaders(),HttpStatus.OK);
			   
		}catch(Exception e){   
        	
			logger.info("-----[TestingController] COMPLETE TASK FAILED. Caused by :"+e+"------");
        	return new ResponseEntity(""+GlobalString.RESP_FAILED+". EXCEPTION : "+e+"", new HttpHeaders(),HttpStatus.FORBIDDEN);
		
		}
	}
			
	
	public RestTemplate getRestTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	    TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
	        @Override
	        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
	            return true;
	        }
	    };
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	}
	
}
