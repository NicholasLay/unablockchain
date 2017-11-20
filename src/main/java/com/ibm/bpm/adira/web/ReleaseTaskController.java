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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.ibm.bpm.adira.domain.ReleaseTaskRequestBean;
import com.ibm.bpm.adira.service.ProcessService;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

//Async Task
@Controller
public class ReleaseTaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReleaseTaskController.class);
	
	@RequestMapping(value="/releaseTask", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody ReleaseTaskRequestBean releaseTask) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException
	{
		
		String orderId 	= releaseTask.getOrderID();
		int processId 	= releaseTask.getProcessID();
		int brmsScoring = releaseTask.getBrmsScoring();
		int taskId 		= releaseTask.getTaskID();
		Boolean mayor 	= releaseTask.getMayor();
		
		String logTracker = 
				"From acction: "+ 
				"Order ID ="+orderId+
				"Process ID ="+processId+
				"Task ID = "+taskId+
				"BRMS ="+brmsScoring+
				"Mayor ="+mayor;
		
		logger.info(logTracker);
		
		String walletBalanceUrl = "https://10.81.3.38:9443/rest/bpm/wle/v1/task/"+taskId+"?action=cancel";
		
		logger.info("URL:"+ walletBalanceUrl);
		
		logger.info("Entering Authorization..");
		
		
		String plainCreds = "acction:ADira2017";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		
		logger.info("Release Task Controller - plainCreds :"+plainCreds);
		
		RestTemplate restTemplate = getRestTemplate();
		
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);

		String response = restTemplate.postForObject(walletBalanceUrl, entity, String.class);
		
		String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()); 
		
		String responseToAcction = 
				"{\"orderID\":\"" +orderId+ "\","+
        		"\"processID\":\"" +processId+ "\","+
        		"\"taskID\":\"" +taskId+ "\","+
        		"\"displayName\":\"Submit IDE\","+
				"\"assignToType\":\"IDE\","+
				"\"startTime\":\""+timestamp+"\","+
        		"\"status\":\"Task has been released\""+ 
        		"}";
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
