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
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.domain.ReleaseTaskRequestBean;
import com.ibm.bpm.adira.domain.ReleaseTaskResponseBean;
import com.ibm.bpm.adira.domain.ReleaseTaskResponseToAcction;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;


/*
 *	Microservice to release task in IBM BPM. 
 */

@Controller
public class ReleaseTaskController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(ReleaseTaskController.class);

	
	@RequestMapping(value="/releaseTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody ReleaseTaskRequestBean releaseTask) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
	
	{

		GlobalString gs = new GlobalString();
		String orderId 	= releaseTask.getOrderID();
		int processId 	= releaseTask.getProcessID();
		int brmsScoring = releaseTask.getBrmsScoring();
		int taskId 		= releaseTask.getTaskID();
		Boolean mayor 	= releaseTask.getMayor();
		
		String RequestLog = 
				"From acction: "+ 
				"Order ID 	="+orderId+
				"Process ID ="+processId+
				"Task ID 	= "+taskId+
				"BRMS 		="+brmsScoring+
				"Mayor 		="+mayor;
		
		logger.info(RequestLog);
		
		propertiesLoader = new PropertiesLoader();
		
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		//String bpmip = propertiesLoader.loadProperties("bpmip");
		/*
		String walletBalanceUrl = "https://"
				+ bpmip
				+ ":9443/rest/bpm/wle/v1/task/"+taskId+"?action=cancel";
		*/
		String walletBalanceUrl = bpmUrl + "/task/"+taskId+"?action=cancel";
		logger.info("URL:"+ walletBalanceUrl);

		logger.info("-----------ENTERING AUTHORIZATION-----------");
		
		//String plainCreds = "acction:ADira2017";
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		logger.info("\"-----------PROCESSING AUTHORIZATION-----------\"");
		
		RestTemplate restTemplate = getRestTemplate();
		String response = restTemplate.postForObject(walletBalanceUrl, entity, String.class);
		Gson json = new Gson();

		ReleaseTaskResponseToAcction beanAcctionRelease = new ReleaseTaskResponseToAcction();
		String responseToAcction = "";
	
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
				
				if(response == null || response.isEmpty()) {
					ReleaseTaskResponseBean responseReleaseSucessBPM = new ReleaseTaskResponseBean();
					responseReleaseSucessBPM.setStatus("200");
				
					beanAcctionRelease.setOrderID(orderId);
					beanAcctionRelease.setTaskID(taskId);
					beanAcctionRelease.setStatus(responseReleaseSucessBPM.getStatus());
					
					responseToAcction = json.toJson(beanAcctionRelease);
				}
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				logger.info("-----------NOT OK RESPONSE -----------");
				beanAcctionRelease.setOrderID(GlobalString.EMPTY_STRING);
				beanAcctionRelease.setTaskID(GlobalString.EMPTY_INTEGER);
				beanAcctionRelease.setStatus(GlobalString.OK_MESSAGE);
				
				responseToAcction = json.toJson(beanAcctionRelease);
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}

		logger.info("-----------NOT BASIC AUTHORIZATION -----------");
		beanAcctionRelease.setOrderID(GlobalString.EMPTY_STRING);
		beanAcctionRelease.setTaskID(GlobalString.EMPTY_INTEGER);
		beanAcctionRelease.setStatus(GlobalString.AUTH_FAILED_AD1);
		
		responseToAcction = json.toJson(beanAcctionRelease);
		
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
