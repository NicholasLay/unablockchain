package com.ibm.bpm.adira.web;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.SSLContext;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.CompleteTaskRequestBean;
import com.ibm.bpm.adira.domain.CompleteTaskResponseBean;
import com.ibm.bpm.adira.domain.GeneralRequestParameter;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

/*
 *	Microservice to complete task in IBM BPM. 
 */

@Controller
public class CompleteTaskController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@RequestMapping(value="/completeTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
	@RequestBody CompleteTaskRequestBean completeTaskRequest) 
	throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException,JsonProcessingException , InterruptedException

	{
		Gson json = new Gson();
		String orderID 				 = completeTaskRequest.getOrderID();
		int processID 				 = completeTaskRequest.getProcessID();
		int taskID 					 = completeTaskRequest.getTaskID();
		ArrayList<String> groupAlias = completeTaskRequest.getGroupAlias();
		String locationAlias 		 = completeTaskRequest.getLocationAlias();
		Boolean isLocation 			 = false;
		Integer currLevelOverride	 = completeTaskRequest.getCurrentLevelOverride();
		
		logger.info("[CompleteTaskController] Request Complete Task Acction :"+json.toJson(completeTaskRequest)+"");
		
		String completeTaskRequestAcction = "";
		GeneralRequestParameter parameterComplete = new GeneralRequestParameter();
		
		if(null != completeTaskRequest.getSurveyResult()) {
			 parameterComplete.setSurveyResult(completeTaskRequest.getSurveyResult());
		}
		if(null != completeTaskRequest.getIsAOSCMOApprove()) {
			parameterComplete.setIsAOSCMOApprove(completeTaskRequest.getIsAOSCMOApprove());
		}
		if(null != completeTaskRequest.getIsAOSCFOApprove()) {
			parameterComplete.setIsAOSCFOApprove(completeTaskRequest.getIsAOSCFOApprove());
		}
		if(null != completeTaskRequest.getToIDE()){
			parameterComplete.setToIDE(completeTaskRequest.getToIDE());
		}
		if(null != completeTaskRequest.getIsDacor()) {
			parameterComplete.setIsDacor(completeTaskRequest.getIsDacor());
		}
		if(null != completeTaskRequest.getApprovalResult()) {
			parameterComplete.setApprovalResult(completeTaskRequest.getApprovalResult());
		}
		if(null != completeTaskRequest.getIsSignPK()) {
			parameterComplete.setIsSignPK(completeTaskRequest.getIsSignPK());
		}
		if(null != completeTaskRequest.getIsDacorDR()) {
			parameterComplete.setIsDacorDR(completeTaskRequest.getIsDacorDR());
		}
		if(null != completeTaskRequest.getIsDacorFDE()) {
			parameterComplete.setIsDacorFDE(completeTaskRequest.getIsDacorFDE());
		}
		if(null != completeTaskRequest.getCurrentLevelOverride()) {
			parameterComplete.setCurrentLevelOverride(currLevelOverride);
		}
		
		completeTaskRequestAcction = json.toJson(parameterComplete);
		propertiesLoader = new PropertiesLoader();
		
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		String completeTaskURL = bpmUrl + "/task/"+taskID+"?action=finish&params={completeTaskRequestAcction}&parts=all";
    	logger.info("[CompleteTaskController] URL TO BPM:"+completeTaskURL);
    	
    	logger.info("Masuk Auth");
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
    	byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		try {
			
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
	
		Thread.sleep(5000);
		
		
		if (basicAuth.startsWith("Basic")){
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("[CompleteTaskController] Authentication from Acction to API. Username "+values[0] + "Password "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String responseAd1Gate = ad1ServiceImpl.getNIK(groupAlias, locationAlias, isLocation);
		
			logger.info("--------[CompleteTaskController]RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			if (responseAd1Gate.matches("(.*)"+values[0]+"(.*)"))
			{					
				logger.info("-----[CompleteTaskController] USER MATCHES, PROCESSING COMPLETE TASK------");
				
				String responseFinishTaskBPM = restTemplate.postForObject(completeTaskURL, entity, String.class, completeTaskRequestAcction);
				Thread.sleep(5000);
//				CompleteTaskResponseBean completeTaskResponseBPM = json.fromJson(responseFinishTaskBPM, CompleteTaskResponseBean.class);
				
				logger.info("----------- [CompleteTaskController] Response JSON CompeleteTask from BPM: \n"+ responseFinishTaskBPM+"-------------");
				return new ResponseEntity(GlobalString.RESP_SUCESS, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{	
				logger.info("-----[CompleteTaskController] USER NOT FOUND, COMPLETE TASK FAILED------");
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}
		}else {
			logger.info("-----[CompleteTaskController] AUTHORIZATION IS NOT BASIC------");
			return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
		}
	}catch(Exception e) {
		logger.info("-----[CompleteTaskController]Exception Invoked. Complete Task has been canceled . Cause by : "+ e+"------");
		return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
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
