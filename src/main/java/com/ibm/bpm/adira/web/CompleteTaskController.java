package com.ibm.bpm.adira.web;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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
import com.ibm.bpm.adira.domain.CompleteTaskRequestBean;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class CompleteTaskController 
{
	
private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
	
	@RequestMapping(value="/completeTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CompleteTaskRequestBean completeTaskRequest) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
			
	{

		String orderID 	= completeTaskRequest.getOrderID();
		int processID 	= completeTaskRequest.getProcessID();
		int taskID 		= completeTaskRequest.getTaskID();
		
		String logTracker = 
				"From acction: "+ 
				"Order ID ="+orderID+
				"Process ID ="+processID+
				"Task ID = "+taskID;
	
		logger.info(logTracker);
		
		String completeTaskURL = "https://10.81.3.38:9443/rest/bpm/wle/v1/task/"+taskID+"?action=finish&parts=all";
    	
    	logger.info("Process Service Impl:"+completeTaskURL);
    	
    	logger.info("Masuk Auth");
		String plainCreds = "acction:ADira2017";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		logger.info("Auth is being processed");
		
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		String responseFinishTaskBPM = restTemplate.postForObject(completeTaskURL, entity, String.class);
		
		logger.info("----------- Response JSON CompeleteTask(JCT) from BPM: \n"+ responseFinishTaskBPM+"-------------");
		
		if (basicAuth.startsWith("Basic"))
		{
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("USERNAME "+values[0]);
			logger.info("PASSWORD "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String responseAd1Gate = ad1ServiceImpl.authResponse(values[0], values[1]);
			logger.info("--------RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			
			if (responseAd1Gate.equals(GlobalString.OK_MESSAGE))
			{					
				logger.info("-----COMPLETE TASK SUCESS, PROCESS CURRENT STATE TO GET NEXT TASK------");
				return new ResponseEntity(GlobalString.RESP_SUCESS, new HttpHeaders(),HttpStatus.OK);
				
			}
			else
			{	
				logger.info("-----COMPLETE TASK FAILED------");
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}
		logger.info("-----COMPLETE TASK AUTHORIZATION IS NOT BASIC------");
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
