package com.ibm.bpm.adira.web;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.ClaimTaskRequestBean;
import com.ibm.bpm.adira.domain.ClaimTaskResponseBean;
import com.ibm.bpm.adira.domain.ClaimTaskResponseToAcction;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class ClaimTaskController {
	private static final Logger logger = LoggerFactory.getLogger(ClaimTaskController.class);
	
	@RequestMapping(value="/claimTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody ClaimTaskRequestBean claimtaskReq) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException
	{
		String orderId	 = claimtaskReq.getOrderID();
		int processId	 = claimtaskReq.getProcessID();
		int brmsScoring	 = claimtaskReq.getBrmsScoring();
		int taskId 		 = claimtaskReq.getTaskID();
		Boolean mayor 	 = claimtaskReq.getMayor();
		String userClaim = claimtaskReq.getUserClaim();
		
		logger.info("From acction: "+ 
				"Order ID "+orderId+
				"Process ID "+processId+
				"Task ID "+taskId+
				"BRMS "+brmsScoring+
				"Mayor "+mayor
				);
		
		String walletBalanceUrl = "https://10.81.3.38:9443/rest/bpm/wle/v1/task/"+taskId+"?action=assign&toMe=true&parts=all";
	
		logger.info("URL:"+ walletBalanceUrl);

		logger.info("-----------ENTERING AUTHORIZATION-----------");
	
		String plainCreds = "acction:ADira2017";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		logger.info("\"-----------PROCESSING AUTHORIZATION-----------\"");

		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);

		String response = restTemplate.postForObject(walletBalanceUrl, entity, String.class);
		String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()); 

		logger.info("-----------RESPONSE JSON BPM ("+timestamp+") = "+response+"-----------");
		
		Gson json = new Gson();
		
		ClaimTaskResponseBean responseBpmClaim = json.fromJson(response, ClaimTaskResponseBean.class);
		
		ClaimTaskResponseToAcction beanAcctionClaim = new ClaimTaskResponseToAcction();
		beanAcctionClaim.setOrderID(orderId);
		beanAcctionClaim.setTaskID(taskId);
		beanAcctionClaim.setStatus(responseBpmClaim.getStatus());
		beanAcctionClaim.setUserClaim(userClaim);
		
		String responseToAcction = json.toJson(beanAcctionClaim);
		
		return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
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


