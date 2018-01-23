package com.ibm.bpm.adira.web;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;

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
import com.ibm.bpm.adira.domain.StartProcessRequestBean;
import com.ibm.bpm.adira.domain.StartProcessResponseBean;
import com.ibm.bpm.adira.domain.StartProcessResponseToAcction;
import com.ibm.bpm.adira.domain.StartProcessResponseBean.Tasks;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

/*
 * This class is used to start the IDE Process in IBM BPM.
 */

@Controller
public class StartProcessController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(StartProcessController.class);
	@RequestMapping(value="/startProcessIDE", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody StartProcessRequestBean startProcess) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
	{
		String currentTime = "";
		String orderId			= startProcess.getOrderID();
		int brmsScoring			= startProcess.getBrmsScoring();
		Boolean isSignPK		= startProcess.getIsSignPK();
		Boolean isTele			= startProcess.getNeedTele();
		Boolean isMayor 	   	= startProcess.getIsMayor();
		Boolean isSalesMayor	= startProcess.getIsSalesMayor();
		Boolean isManualAssign	= startProcess.getIsManualAssign();
		String  cmoApprove		= startProcess.getIsAOSCMOApprove();
		Boolean toIDe 			= startProcess.getToIDE();
		Boolean isDacor 		= startProcess.getIsDacor();
		
		Gson json = new Gson();
	
		//Response BPM Initialize
		String assignedToType 		= "";
		String displayName			= "";
		String dueTime				= "";
		String processInstanceName	= "";
		int tkiid					= 0;
		String assignTo				="";
		int processId	= 0;
				
		Gson jsonRequest = new Gson();
		
		String jsonStartRequestAcction = jsonRequest.toJson(startProcess);
		logger.info(new Timestamp(System.currentTimeMillis())+
				"[StartProcessController] Request From Acction: "+jsonStartRequestAcction+""
		);
		
		propertiesLoader = new PropertiesLoader();
		
		String bpdId = propertiesLoader.loadProperties("bpdid");
		String snapshotId = propertiesLoader.loadProperties("snapshotid");
		String processAppId = propertiesLoader.loadProperties("processappid");
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		
		String walletBalanceUrl = bpmUrl + "/process?action=start&"
				+ "bpdId="+bpdId+"&"
				+ "snapshotId="+snapshotId+"&"
				+ "processAppId="+processAppId+"&params={jsonStartRequestAcction}&parts=all";
		
		logger.info(new Timestamp(System.currentTimeMillis())+"-----------[StartProcessController] URL : "+walletBalanceUrl+"---------------");
		
		logger.info(new Timestamp(System.currentTimeMillis())+"-----------[StartProcessController] ENTERING AUTHORIZATION IBM BPM-----------");
		
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		logger.info(new Timestamp(System.currentTimeMillis())+"-----------[StartProcessController] PROCESSING AUTHORIZATION IBM BPM-----------\"");

		RestTemplate restTemplate = getRestTemplate();
		String responseFromBPM = restTemplate.postForObject(walletBalanceUrl, entity, String.class, jsonStartRequestAcction);
		String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()); 
		
		logger.info(new Timestamp(System.currentTimeMillis())+"-----------[StartProcessController] Response StartProcess From BPM:  ("+timestamp+") = "+responseFromBPM+"-----------");
		
		
		
		StartProcessResponseBean responseBeanBPM = json.fromJson(responseFromBPM, StartProcessResponseBean.class);

		for(Tasks responseTask : responseBeanBPM.getData().getTasks()) {
			processInstanceName = responseTask.getProcessInstanceName();
			displayName 		= responseTask.getDisplayName();
			tkiid 				= responseTask.getTkiid();
			assignedToType 		= responseTask.getAssignedToType();
			dueTime		 		= responseTask.getDueTime();
			assignTo			= responseTask.getName();
			processId			= Integer.parseInt(responseTask.getPiid());
			}

		StartProcessResponseToAcction beanAcction = new StartProcessResponseToAcction();
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
			
			logger.info(new Timestamp(System.currentTimeMillis())+"--------RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			
			if (responseAd1Gate.equals(GlobalString.OK_MESSAGE))
			{	
				logger.info(new Timestamp(System.currentTimeMillis())+"-----------SUCCESS ENTERING RESPONSE -----------");
				beanAcction.setProcessInstanceName(processInstanceName);
				beanAcction.setDisplayName(displayName);
				beanAcction.setTaskID(tkiid);
				beanAcction.setAssignTo(assignTo);
				beanAcction.setAssignedToType(assignedToType);
				beanAcction.setStartTime(dueTime);
				beanAcction.setDueTime(dueTime);
				beanAcction.setOrderID(orderId);
				beanAcction.setProcessID(processId);
				
				responseToAcction = json.toJson(beanAcction);

				logger.info(new Timestamp(System.currentTimeMillis())+"-----------RESPONSE TO  ACCTION START PROCESS :"+responseToAcction+"-----------");
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				logger.info(new Timestamp(System.currentTimeMillis())+"-----------NOT OK RESPONSE -----------");
				beanAcction.setOrderID(GlobalString.EMPTY_STRING);
				beanAcction.setProcessID(GlobalString.EMPTY_INTEGER);
				beanAcction.setProcessInstanceName(GlobalString.EMPTY_STRING);;
				beanAcction.setDisplayName(GlobalString.EMPTY_STRING);
				beanAcction.setTaskID(GlobalString.EMPTY_INTEGER);
				beanAcction.setAssignedToType(GlobalString.EMPTY_STRING);
				beanAcction.setAssignTo(GlobalString.EMPTY_STRING);
				beanAcction.setStartTime(GlobalString.EMPTY_STRING);
				beanAcction.setDueTime(GlobalString.EMPTY_STRING);
				
				responseToAcction = json.toJson(beanAcction);
				
				logger.info(new Timestamp(System.currentTimeMillis())+"-----------RESPONSE TO  ACCTION START PROCESS :"+responseToAcction+"-----------");
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}

		logger.info(new Timestamp(System.currentTimeMillis())+"-----------NOT BASIC AUTHORIZATION -----------");
		beanAcction.setOrderID(GlobalString.EMPTY_STRING);
		beanAcction.setProcessID(GlobalString.EMPTY_INTEGER);
		beanAcction.setProcessInstanceName(GlobalString.EMPTY_STRING);
		beanAcction.setDisplayName(GlobalString.EMPTY_STRING);
		beanAcction.setTaskID(GlobalString.EMPTY_INTEGER);
		beanAcction.setAssignedToType(GlobalString.EMPTY_STRING);
		beanAcction.setAssignTo(GlobalString.EMPTY_STRING);
		beanAcction.setStartTime(GlobalString.EMPTY_STRING);
		beanAcction.setDueTime(GlobalString.EMPTY_STRING);
		
		responseToAcction = json.toJson(beanAcction);
		logger.info(new Timestamp(System.currentTimeMillis())+"-----------RESPONSE TO  ACCTION START PROCESS :"+responseToAcction+"-----------");
		
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