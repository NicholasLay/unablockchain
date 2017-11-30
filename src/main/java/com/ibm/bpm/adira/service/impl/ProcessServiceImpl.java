package com.ibm.bpm.adira.service.impl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean.CurrentTask;
import com.ibm.bpm.adira.domain.BasicAuthRestTemplate;
import com.ibm.bpm.adira.domain.CompleteTaskResponseBean;
import com.ibm.bpm.adira.domain.CompleteTaskResponseBean.NexTaskId;
import com.ibm.bpm.adira.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Async("processExecutor")
    @Override
    public void process(String service,String orderID,int processID,int taskID) {
        logger.info("Received request to process in ProcessServiceImpl.process()");
        
        logger.info("Process Service Impl:Service="+service+"orderID="+orderID,"processID="+processID+"taskID="+taskID);
        
        if (service.equals("CompleteTask"))
        {
        	try {
				completeTaskProcess(orderID, processID, taskID);
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//        callBackToAcction(orderID, processID, taskID);
    }
    
    public void callBackToAcction(String orderID,int processID, int taskID, List<NexTaskId>nextTaskId)
    {
    	RestTemplate restTemplate = new RestTemplate();
    	
    	String linkURL = "http://10.61.5.247:7727";
    	String acctionUrl = ""+linkURL+"/adira-acction/acction/v1/service/bpm/callback/complete";
    	
    	Gson json = new Gson();
    	//let it always empty
    	String status = "";
    	
    	AcctionCallBackRequestBean acctionBean = new AcctionCallBackRequestBean();
    	
    	AcctionCallBackRequestBean.CurrentTask currentTaskRequest = acctionBean.new CurrentTask();
    	currentTaskRequest.setOrderID(orderID);
    	currentTaskRequest.setProcessID(processID);
    	currentTaskRequest.setTaskID(taskID);

    	acctionBean.setCurrentTask(currentTaskRequest);
    	acctionBean.setTasks(nextTaskId);
   
    	String acctionCallbackRequest = json.toJson(acctionBean);
    	
    	logger.error("TEST");
    	
    	logger.info("Process Service Impl: acction URL"+ acctionUrl);
    	logger.info("Process Service Impl: acction Callback Request"+ acctionCallbackRequest);
    	
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(acctionCallbackRequest,headers);
        
        String answer = restTemplate.postForObject(acctionUrl, entity, String.class);
    	logger.info("Process Service Impl : Response Callback from acction"+ answer);
    }
    
    @SuppressWarnings({ "unchecked", "null" })
	public void completeTaskProcess(String orderID,int processID, int taskID) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException
    {
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

		String response = restTemplate.postForObject(completeTaskURL, entity, String.class);
		logger.info("Process Service Impl: Response from BPM "+ response);
		
		Gson json = new Gson();
		CompleteTaskResponseBean completeBeanResponse = json.fromJson(response, CompleteTaskResponseBean.class);
		
		
		List<NexTaskId> tasksRequestAcction = null;
		
		if(completeBeanResponse.getData().getNextTaskId().isEmpty()  ||
		   completeBeanResponse.getData().getNextTaskId().size() == 0||
		   completeBeanResponse.getData().getNextTaskId() == null) {
		
			tasksRequestAcction = completeBeanResponse.getData().getNextTaskId();
		
		}else {
			
		}
		
		logger.info("------COMPLETE TASK SUCCEED , NOW PROCESSING CALLBACK-----");
		
		callBackToAcction(orderID, processID,taskID,tasksRequestAcction);
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
//Call to Acction using basic auth
/* 
String username = "70000386";
String password = "adira";

BasicAuthRestTemplate restTemplate = new BasicAuthRestTemplate(username, password);

String url = "http://localhost:8080/gs-rest-service-0.1.0/backToIDE";
String acctionUrl = "http://10.61.5.247:9091/adira-acction/acction/service/bpm/callback";
String acctionCallback = "{\"orderID\":\"" +orderID+ "\","+
		"\"processID\":\"" +processID+ "\","+
		"\"taskID\":\"" +taskID+ "\","+
		"\"status\":\"onprogress\","+
		"\"displayName\":\"sa\","+
		"\"assignToType\":\"sa\""+ 
		"}";

String requestJson = "{\"orderID\":\"" +orderID+ "\","+
		"\"processID\":\"" +processID+ "\","+
		"\"taskID\":\"" +taskID+ "\","+
		"\"mayor\":true,"+
		"\"brmsScoring\":1"+ 
		"}";

logger.info("Callback Request:"+requestJson);
HttpHeaders headers = new HttpHeaders();

headers.setContentType(MediaType.APPLICATION_JSON);
HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);

String answer = restTemplate.postForObject(url, entity, String.class);
System.out.println(answer);
logger.info("Processing complete");
*/