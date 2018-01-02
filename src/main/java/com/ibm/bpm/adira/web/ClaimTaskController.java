package com.ibm.bpm.adira.web;

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
import com.ibm.bpm.adira.domain.ClaimTaskRequestBean;
import com.ibm.bpm.adira.domain.ClaimTaskResponseBean;
import com.ibm.bpm.adira.domain.ClaimTaskResponseToAcction;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;


/*
 *	Microservice to claim Task to IBM BPM. 
 */

@Controller
public class ClaimTaskController {
	
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(StartProcessController.class);
	
	@RequestMapping(value="/claimTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody ClaimTaskRequestBean claimtaskReq) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
	
	{

		String orderId	 = claimtaskReq.getOrderID();
		int brmsScoring	 = claimtaskReq.getBrmsScoring();
		int taskId 		 = claimtaskReq.getTaskID();
		Boolean mayor 	 = claimtaskReq.getMayor();
		String userClaim = claimtaskReq.getUserClaim();
		
		logger.info("From acction: "+ 
				"Order ID "+orderId+
				"Task ID "+taskId+
				"BRMS "+brmsScoring+
				"Mayor "+mayor
				);
		
		propertiesLoader = new PropertiesLoader();
		String bpmUrl = propertiesLoader.loadProperties("bpmUrl");
		//String bpmip = propertiesLoader.loadProperties("bpmip");
		/*
		String walletBalanceUrl = "https://"
				+ bpmip
				+ ":9443/rest/bpm/wle/v1/task/"+taskId+"?action=assign&toMe=true&parts=all";
		*/
		String walletBalanceUrl = bpmUrl + "/task/"+taskId+"?action=assign&toMe=true&parts=all";
		logger.info("URL:"+ walletBalanceUrl);

		logger.info("-----------[ClaimTaskController] ENTERING AUTHORIZATION-----------");
	
		//String plainCreds = "acction:ADira2017";
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		logger.info("\"-----------[ClaimTaskController] PROCESSING AUTHORIZATION-----------\"");

		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);

		String response = restTemplate.postForObject(walletBalanceUrl, entity, String.class);
		String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()); 

		logger.info("-----------[ClaimTaskController] RESPONSE JSON BPM ("+timestamp+") = "+response+"-----------");
		
		Gson json = new Gson();
		
		ClaimTaskResponseBean responseBpmClaim = json.fromJson(response, ClaimTaskResponseBean.class);
		ClaimTaskResponseToAcction beanAcctionClaim = new ClaimTaskResponseToAcction();
		
		String responseToAcction = "";
		
		if (basicAuth.startsWith("Basic"))
		{
			
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("USERNAME "+values[0]);
			logger.info("PASSWORD "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			
			ArrayList<String> groupAlias = claimtaskReq.getGroupAlias();
			String locationAlias = claimtaskReq.getLocationAlias();
			Boolean isLocation = false;
			
			String responseAd1Gate = ad1ServiceImpl.getNIK(groupAlias, locationAlias, isLocation);
			
			logger.info("--------[ClaimTaskController]RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			if(responseAd1Gate.matches("(.*)"+values[0]+"(.*)"))
			{	
				
				logger.info("-----------[ClaimTaskController] USER MATCHES,  ENTERING RESPONSE TO ACCTION -----------");
				beanAcctionClaim.setOrderID(orderId);
				beanAcctionClaim.setTaskID(taskId);
				beanAcctionClaim.setStatus(responseBpmClaim.getStatus());
				beanAcctionClaim.setUserClaim(userClaim); 
				
				responseToAcction = json.toJson(beanAcctionClaim);
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				logger.info("-----------[ClaimTaskController] USER NOT FOUND , NOT OK RESPONSE -----------");
				beanAcctionClaim.setOrderID(GlobalString.EMPTY_STRING);
				beanAcctionClaim.setTaskID(GlobalString.EMPTY_INTEGER);
				beanAcctionClaim.setStatus(GlobalString.RESP_FAILED);
			
				responseToAcction = json.toJson(beanAcctionClaim);
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}
		}

		logger.info("-----------[ClaimTaskController] NOT BASIC AUTHORIZATION -----------");
		beanAcctionClaim.setOrderID(GlobalString.EMPTY_STRING);
		beanAcctionClaim.setTaskID(GlobalString.EMPTY_INTEGER);
		beanAcctionClaim.setStatus(GlobalString.AUTH_FAILED_AD1);
		
		responseToAcction = json.toJson(beanAcctionClaim);
		
		return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
	
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


