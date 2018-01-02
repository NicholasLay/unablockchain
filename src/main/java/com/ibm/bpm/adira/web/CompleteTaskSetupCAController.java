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
import com.ibm.bpm.adira.domain.CurrentStateResponseBean;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

@Controller
public class CompleteTaskSetupCAController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(CompleteTaskSetupCAController.class);
	
	@RequestMapping(value="/completeTaskSetupCA", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CompleteTaskRequestBean completeTaskSetupCARequest) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException
			
	{
		Gson json = new Gson();
		String orderID 				 = completeTaskSetupCARequest.getOrderID();
		int processID 				 = completeTaskSetupCARequest.getProcessID();
		int taskID 					 = completeTaskSetupCARequest.getTaskID();
		ArrayList<String> groupAlias = completeTaskSetupCARequest.getGroupAlias();
		String locationAlias 		 = completeTaskSetupCARequest.getLocationAlias();
		Boolean isLocation 			 = false;
		
		String logTracker = json.toJson(completeTaskSetupCARequest);
	
		logger.info("[CompleteTaskSetupCAController] Request Complete Task Acction :"+logTracker+"");
		
		propertiesLoader = new PropertiesLoader();
		
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		//String bpmip = propertiesLoader.loadProperties("bpmip");
		/*
		String completeTaskURL = "https://"
				+ bpmip
				+ ":9443/rest/bpm/wle/v1/task/"+taskID+"?action=finish&parts=all";
    	*/
		String completeTaskURL = bpmUrl + "/task/"+taskID+"?action=finish&parts=all";
    	logger.info("[CompleteTaskSetupCAController] URL TO BPM:"+completeTaskURL);
    	
    	logger.info("Masuk Auth");
		//String plainCreds = "acction:ADira2017";
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
    	byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		String responseFinishTaskBPM = restTemplate.postForObject(completeTaskURL, entity, String.class);
		
		logger.info("----------- [CompleteTaskSetupCAController] Response JSON CompeleteTask from BPM: \n"+ responseFinishTaskBPM+"-------------");
		
		if (basicAuth.startsWith("Basic"))
		{
			String base64Credentials = basicAuth.substring("Basic".length()).trim();
			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
			String[] values = credentials.split(":",2);
			
			logger.info("[CompleteTaskSetupCAController] Authentication from Acction to API. Username "+values[0] + "Password "+values[1]);
			
			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
			String responseAd1Gate = ad1ServiceImpl.getNIK(groupAlias, locationAlias, isLocation);
		
			logger.info("--------[CompleteTaskSetupCAController]RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
			if (responseAd1Gate.matches("(.*)"+values[0]+"(.*)"))
			{					
				logger.info("-----[CompleteTaskSetupCAController] USER MATCHES, NOW PROCESSING CURRENT STATE TO GET NEXT TASK------");
				String responseToAcction =  currentState(orderID, processID, taskID);
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{	
				logger.info("-----[CompleteTaskSetupCAController] USER NOT FOUND, COMPLETE TASK FAILED------");
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}

		}
		logger.info("-----[CompleteTaskSetupCAController] AUTHORIZATION IS NOT BASIC------");
		return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
	}
	
	
public String currentState(String orderID,int processID ,int taskID) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{
    	
    	logger.info("--------------------------Entering current state--------------------------\n");
    	
    	Gson json = new Gson();
    	propertiesLoader = new PropertiesLoader();
		
		String bpmip = propertiesLoader.loadProperties("bpmip");
    	String currentStateURL = "https://"
    			+ bpmip
    			+ ":9443/rest/bpm/wle/v1/process/"+processID+"?parts=all";
		
    	logger.info("-------------------- [CompleteTaskSetupCAController] URL CURRENT STATE :"+currentStateURL+"------------------------------");
		
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
    	
    	ResponseEntity<String> responseCurrStateBPM = restTemplate.exchange(currentStateURL, HttpMethod.GET, entity, String.class);
    	
    	String responseBodyCurrState = responseCurrStateBPM.getBody();
    	
		logger.info("----------------[CompleteTaskSetupCAController] Response from BPM GetCurrentState : \n "+ responseBodyCurrState+"\n-------------------");
		
		CurrentStateResponseBean currStateResponse = json.fromJson(responseBodyCurrState, CurrentStateResponseBean.class);
		
		AcctionCallBackRequestBean tasksRequestAcction = new AcctionCallBackRequestBean();
		
		List<TasksCurrentState> taskDetailResponseToAcction = new ArrayList<TasksCurrentState>();
		
		List<TasksCurrentState> emptyArray = new ArrayList<TasksCurrentState>();
		
		String assignTo = "";
		String assignToType = "";
		int taskIdNextask = 0;
		int processId = 0;
		String status = "";
		
		int indexCounter = currStateResponse.getData().getTasks().size();
		
		if (indexCounter > 0) 
		{
			for(TasksCurrentState tasks : currStateResponse.getData().getTasks()){
			
				assignTo = tasks.getName();
				assignToType = tasks.getAssignedToType();	
				taskIdNextask = tasks.getTkiid();
				processId = tasks.getPiid();
				status = tasks.getStatus();
				
				logger.info("Detail for task:  "+tasks.getTkiid()+" is : "
						+ "assignTo:  "+assignTo+""
						+ "assignToType:  "+assignToType+""
						+ "processId:  "+processID+""
						+ "status:  "+status+"");
				
				if (!status.equals(GlobalString.STATUS_TASK_CLOSED)) {
				
					logger.info("Status = "+status+", Task Added!");
					
					tasks.setDisplayName(tasks.getName());
					tasks.setProcessID(processId);
					tasks.setAssignTo(assignTo);
					tasks.setAssignToType(assignToType);
					tasks.setTaskID(taskIdNextask);
					
					taskDetailResponseToAcction.add(tasks);
					
					tasksRequestAcction.setTasks(taskDetailResponseToAcction);
				}else {
					logger.info("Status = "+status+" , Task Depereciated!");
					tasksRequestAcction.setTasks(emptyArray);
				}
			}
			logger.info("------------TOTAL RECEIVED TASKS: "+tasksRequestAcction.getTasks().size()+"-------------");
		}else {
				tasksRequestAcction.setTasks(emptyArray);
		}
			
		AcctionCallBackRequestBean acctionBean = new AcctionCallBackRequestBean();
    	
    	AcctionCallBackRequestBean.CurrentTask currentTaskRequest = acctionBean.new CurrentTask();
    
    	currentTaskRequest.setOrderID(orderID);
    	currentTaskRequest.setProcessID(processID);
    	currentTaskRequest.setTaskID(taskID);
    	
    	acctionBean.setCurrentTask(currentTaskRequest);   	
    	acctionBean.setTasks(tasksRequestAcction.getTasks());
    	
    	String acctionCallbackRequest = json.toJson(acctionBean);
    	
    	logger.info("[CompleteTaskSetupCAController]: Acction Callback Request : \n"+ acctionCallbackRequest);
		
	return acctionCallbackRequest;
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
