package com.ibm.bpm.adira.web;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.bpm.adira.domain.StartProcessRequestBean;
import com.ibm.bpm.adira.service.impl.ProcessServiceImpl;

@Controller
public class StartProcessController 
{
	private static final Logger logger = LoggerFactory.getLogger(StartProcessController.class);
	@RequestMapping(value="/startProcessIDE", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody StartProcessRequestBean startProcess) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException
	{
		logger.info("From acction: Order ID "+startProcess.getOrderID()+
				"Process ID "+startProcess.getProcessID()+"Task ID "+startProcess.getTaskID()
			+"BRMS "+startProcess.getBrmsScoring()+"Mayor "+startProcess.getMayor());

		String walletBalanceUrl = "https://10.81.3.38:9443/rest/bpm/wle/v1/process?action=start&bpdId=25.9a0484ab-9ece-44e0-8cc2-e086172e2cc1&snapshotId=2064.a6d5833c-bf89-4687-ae69-38206c30fef2&branchId=2063.b06bda07-3d67-4084-8f37-3d6053dc2d24&processAppId=2066.c464e5f1-3399-406f-a208-eddaad75b871&parts=all";

		logger.info("Masuk Auth");
		String plainCreds = "ariadana:password";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		logger.info("Auth is being processed");

		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);

		String response = restTemplate.postForObject(walletBalanceUrl, entity, String.class);
		
		JsonParser springParser = JsonParserFactory.getJsonParser();
		
		Map<String, Object> result = springParser.parseMap(response);
		String inner = "";
		try {
			inner = new ObjectMapper().writeValueAsString(result.get("data"));
			result = springParser.parseMap(inner);
			inner = new ObjectMapper().writeValueAsString(result.get("tasks"));
			inner = inner.substring(1);
			inner = inner.substring(0,inner.length()-1);
			result = springParser.parseMap(inner);
			inner = new ObjectMapper().writeValueAsString(result.get("tkiid"));
			inner = inner.substring(1);
			inner = inner.substring(0,inner.length()-1);
			logger.info("Task ID :" + inner);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String timestamp = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss a").format(new java.util.Date()); 
		String responseToAcction = "{\"orderID\":\"" +startProcess.getOrderID()+ "\","+
        		"\"processID\":\"" +startProcess.getProcessID()+ "\","+
        		"\"taskID\":\"" +inner+ "\","+
        		"\"displayName\":\"Submit IDE\","+
				"\"assignToType\":\"IDE\","+
				"\"startTime\":\""+ timestamp  +"\","+
        		"\"status\":\"Complete\""+ 
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
