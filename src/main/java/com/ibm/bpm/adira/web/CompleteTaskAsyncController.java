package com.ibm.bpm.adira.web;

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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean;
import com.ibm.bpm.adira.domain.CompleteTaskRequestBean;
import com.ibm.bpm.adira.domain.CompleteTaskResponseBean;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;
import com.ibm.bpm.adira.domain.GeneralRequestParameter;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

@Controller
public class CompleteTaskAsyncController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(CompleteTaskAsyncController.class);
	
	@Autowired
    private ProcessService processService;
	
	@RequestMapping(value="/completeTaskAsync", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CompleteTaskRequestBean completeTaskAsync) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
			
	{
		Gson json = new Gson();
		String orderID 				 = completeTaskAsync.getOrderID();
		int processID 				 = completeTaskAsync.getProcessID();
		int taskID 					 = completeTaskAsync.getTaskID();
		ArrayList<String> groupAlias = completeTaskAsync.getGroupAlias();
		String locationAlias 		 = completeTaskAsync.getLocationAlias();
		Boolean isLocation 			 = false;
		Integer currLevelOverride	 = completeTaskAsync.getCurrentLevelOverride();
		String approvalResult 		 = completeTaskAsync.getApprovalResult();
		Integer maxLevel			 = completeTaskAsync.getMaxLevel();
		Integer currLevelOverrride	 = completeTaskAsync.getCurrentLevelOverride();
		
		
		logger.info("[CompleteTaskController] Request Complete Task From Acction :"+json.toJson(completeTaskAsync)+"");
		
		String completeTaskAsyncAcction = "";
		GeneralRequestParameter parameterComplete = new GeneralRequestParameter();
		
		if(null != completeTaskAsync.getSurveyResult()) {
			 parameterComplete.setSurveyResult(completeTaskAsync.getSurveyResult());
		}
		if(null != completeTaskAsync.getIsAOSCMOApprove()) {
			parameterComplete.setIsAOSCMOApprove(completeTaskAsync.getIsAOSCMOApprove());
		}
		if(null != completeTaskAsync.getIsAOSCFOApprove()) {
			parameterComplete.setIsAOSCFOApprove(completeTaskAsync.getIsAOSCFOApprove());
		}
		if(null != completeTaskAsync.getToIDE()){
			parameterComplete.setToIDE(completeTaskAsync.getToIDE());
		}
		if(null != completeTaskAsync.getIsDacor()) {
			parameterComplete.setIsDacor(completeTaskAsync.getIsDacor());
		}
		if(null != completeTaskAsync.getApprovalResult()) {
			parameterComplete.setApprovalResult(completeTaskAsync.getApprovalResult());
		}else {
			approvalResult = "";
		}
		if(null != completeTaskAsync.getIsSignPK()) {
			parameterComplete.setIsSignPK(completeTaskAsync.getIsSignPK());
		}
		if(null != completeTaskAsync.getIsDacorDR()) {
			parameterComplete.setIsDacorDR(completeTaskAsync.getIsDacorDR());
		}
		if(null != completeTaskAsync.getIsDacorFDE()) {
			parameterComplete.setIsDacorFDE(completeTaskAsync.getIsDacorFDE());
		}
		if(null != completeTaskAsync.getCurrentLevelOverride()) {
			parameterComplete.setCurrentLevelOverride(currLevelOverride);
		}
		if(null != maxLevel) {
			parameterComplete.setMaxLevel(maxLevel);
		}else {
			maxLevel = 0;
		}
		if(null == currLevelOverrride) {
			currLevelOverrride= 0;
		}
		
		completeTaskAsyncAcction = json.toJson(parameterComplete);
    	logger.info("[CompleteTaskAsyncController] Complete JSON Parameter : "+completeTaskAsyncAcction+"");
		
		propertiesLoader = new PropertiesLoader();
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		String completeTaskURL = bpmUrl + "/task/"+taskID+"?action=finish&params={completeTaskAsyncAcction}&parts=all";
    	
		logger.info("[CompleteTaskAsyncController] URL TO BPM:"+completeTaskURL);
	
    	logger.info("Masuk Auth");
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
    	byte[] plainCredsBytes 	= plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds 	 	= new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		if (basicAuth.startsWith("Basic")){
				
				logger.info("-----[CompleteTaskController] BASIC AUTHORIZATION COMPLETE, PROCESSING COMPLETE TASK------");
				
				 try
			        {
			            while(true)
			            {
			                logger.info("[CompleteTaskAsyncController] PROCESSING COMPLETE TASK....");
			                String responseFinishTaskBPM = restTemplate.postForObject(completeTaskURL, entity, String.class, completeTaskAsyncAcction);
				
							logger.info("----------- [CompleteTaskController] Response JSON CompeleteTask from BPM: \n"+ responseFinishTaskBPM+"-------------");
							CompleteTaskResponseBean completeTaskBeanAsync = json.fromJson(responseFinishTaskBPM, CompleteTaskResponseBean.class);
			                logger.info("[CompleteTaskAsyncController] COMPLETE TASK SUCCESS");
			                Thread.sleep(1000);
			                break;
			            }
		        }catch(Exception e){
			    
		        	logger.info("-----[CompleteTaskAsyncController] COMPLETE TASK FAILED. Caused by :"+e+"------");
		        	
		        }finally {
		        		 processService.processCurrentState(GlobalString.SERVIVE_NAME_COMPLETE_TASK,orderID,processID,taskID,maxLevel,approvalResult,currLevelOverrride);
			        	 return new ResponseEntity(GlobalString.RESP_SUCESS, new HttpHeaders(),HttpStatus.OK);
		        }
		}else {
			logger.info("-----[CompleteTaskController] AUTHORIZATION IS NOT BASIC------");
			return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
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
