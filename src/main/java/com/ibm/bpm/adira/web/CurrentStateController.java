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
import com.ibm.bpm.adira.domain.CurrentStateRequestBean;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

/*
 *	Microservice to Get Current State of the task in IBM BPM. 
 */

@Controller
public class CurrentStateController 
{

	@Autowired
    private ProcessService processService;
	
	private static final Logger logger = LoggerFactory.getLogger(CurrentStateController.class);
	
	@RequestMapping(value="/currentState", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CurrentStateRequestBean currStateReq) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
	{	
		
		Gson json = new Gson();
		String orderId	 			 = currStateReq.getOrderID();
		int processId 				 = currStateReq.getProcessID();
		int taskId 					 = currStateReq.getTaskID();
		ArrayList<String> groupAlias = currStateReq.getGroupAlias();
		String locationAlias 		 = currStateReq.getLocationAlias();
		Boolean isLocation 			 = false;
	
		Integer maxLevel 			 = null;
		String approvalResult		 = "";
		
		if(null == maxLevel) {
			maxLevel = 0;
		}else {
			maxLevel = currStateReq.getMaxLevel();
		}
		
		if(null != approvalResult) {
			approvalResult = currStateReq.getApprovalResult();
		}
		
		String logTracker = json.toJson(currStateReq);
		
		logger.info("[CurrentStateController] Request CurrentState Acction :"+logTracker+"");
				
		if (basicAuth.startsWith("Basic"))
		{
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("[CurrentStateController] Authentication from Acction to API. Username "+values[0] + "Password "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String responseAd1GateCurrentState = ad1ServiceImpl.getNIK(groupAlias, locationAlias, isLocation);
			
			logger.info("--------[CurrentStateController]RESPONSE From AD1GATE :  "+ responseAd1GateCurrentState +"-------------");
			
			if (responseAd1GateCurrentState.matches("(.*)"+values[0]+"(.*)"))
			{	
				logger.info("-----------[CurrentStateController]USER MATCHES, SUCCSESS ENTERING AUTHORIZATION -----------");
				
				processService.processCurrentState(GlobalString.SERVICE_NAME_CURRENT_STATE,orderId,processId,taskId,maxLevel,approvalResult);
				
				return new ResponseEntity(GlobalString.RESP_SUCESS, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				logger.info("-----------[CurrentStateController] USER NOT FOUND ,NOT OK RESPONSE -----------");
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}
		}
		logger.info("-----------[CurrentStateController] NOT BASIC AUTHORIZATION -----------");
		return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
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
//String inner = "";	
//try{
//	inner = new ObjectMapper().writeValueAsString(result.get("data"));
//	result = springParser.parseMap(inner);
//	inner = new ObjectMapper().writeValueAsString(result.get("tasks"));
//	inner = inner.substring(1);
//	inner = inner.substring(0,inner.length()-1);
//	result = springParser.parseMap(inner);
//	inner = new ObjectMapper().writeValueAsString(result.get("tkiid"));
//	inner = inner.substring(1);
//	inner = inner.substring(0,inner.length()-1);
//	logger.info("Task ID :" + inner);
//} catch () {
//	// TODO Auto-generated catch block
//	
//}

//String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(new java.util.Date()); 