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
import com.ibm.bpm.adira.domain.GeneralRequestParameter;
import com.ibm.bpm.adira.service.impl.Ad1ServiceImpl;

@Controller
public class CompleteTaskSynchronousController 
{
	private PropertiesLoader propertiesLoader = null;
	private static final Logger logger = LoggerFactory.getLogger(CompleteTaskSynchronousController.class);
	
	@RequestMapping(value="/completeTaskSync", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String basicAuth,
			@RequestBody CompleteTaskRequestBean completeTaskSynchronous) 
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, JsonProcessingException, InterruptedException
			
	{
		Gson json = new Gson();
		String orderID 				 = completeTaskSynchronous.getOrderID();
		int processID 				 = completeTaskSynchronous.getProcessID();
		int taskID 					 = completeTaskSynchronous.getTaskID();
		ArrayList<String> groupAlias = completeTaskSynchronous.getGroupAlias();
		String locationAlias 		 = completeTaskSynchronous.getLocationAlias();
		Integer maxLevel 			 = completeTaskSynchronous.getMaxLevel();
		Boolean isDacor 			 = completeTaskSynchronous.getIsDacor();
		Boolean isLocation 			 = false;
	
		
		String logTracker = json.toJson(completeTaskSynchronous);
	
		logger.info("[CompleteTaskSynchronus] Request Complete Task Acction :"+logTracker+"");
		
		propertiesLoader = new PropertiesLoader();
		
		String bpmUrl = propertiesLoader.loadProperties("bpmurl");
		
		String completeTaskURL = bpmUrl + "/task/"+taskID+"?action=finish&params={completeTaskRequestAcction}&parts=all";
    	
		logger.info("[CompleteTaskSynchronus] URL TO BPM:"+completeTaskURL);
    	
    	logger.info("Masuk Auth");
		String plainCreds = propertiesLoader.loadProperties("plaincreds");
    	byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
	
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		
		if (basicAuth.startsWith("Basic"))
		{
//			String base64Credentials = basicAuth.substring("Basic".length()).trim();
//			String credentials = new String(Base64.decodeBase64(base64Credentials),Charset.forName("UTF-8"));
//			String[] values = credentials.split(":",2);
//			logger.info("[CompleteTaskSynchronus] Authentication from Acction to API. Username "+values[0] + "Password "+values[1]);
//			Ad1ServiceImpl ad1ServiceImpl = new Ad1ServiceImpl();
//			String responseAd1Gate = ad1ServiceImpl.getNIK(groupAlias, locationAlias, isLocation);
//			logger.info("--------[CompleteTaskSynchronus]RESPONSE From AD1GATE :  "+ responseAd1Gate +"-------------");
			
//			if (responseAd1Gate.matches("(.*)"+values[0]+"(.*)"))
//			{				
			
				logger.info("-----[CompleteTaskSynchronus] USER MATCHED, NOW PROCESSING CURRENT STATE TO GET NEXT TASK------");
				String completeTaskRequestAcction = "";
				GeneralRequestParameter parameterComplete = new GeneralRequestParameter();
				
				if(null != isDacor) {
					parameterComplete.setIsDacor(isDacor);
				}
				if(null != maxLevel) {
					parameterComplete.setMaxLevel(maxLevel);
				}else {
					maxLevel = 0;
				}
					
				completeTaskRequestAcction = json.toJson(parameterComplete);
			
				logger.info("[CompleteTaskSynchronus]: Parameter Complete Task: "+completeTaskRequestAcction);
				
				
				String responseFinishTaskBPM = restTemplate.postForObject(completeTaskURL, entity, String.class, completeTaskRequestAcction);
				
				logger.info("----------- [CompleteTaskSynchronus] Response JSON CompeleteTask from BPM: \n"+ responseFinishTaskBPM+"-------------");
				
				
				String responseToAcction =  currentState(orderID, processID, taskID, maxLevel);
				
				return new ResponseEntity(responseToAcction, new HttpHeaders(),HttpStatus.OK);
			}
			else
			{	
				logger.info("-----[CompleteTaskSynchronus] USER NOT FOUND, COMPLETE TASK FAILED------");
				return new ResponseEntity(GlobalString.RESP_FAILED, new HttpHeaders(),HttpStatus.FORBIDDEN);
			}
//		}else {
//			logger.info("-----[CompleteTaskSynchronus] AUTHORIZATION IS NOT BASIC------");
//			return new ResponseEntity(GlobalString.AUTH_FAILED_AD1, new HttpHeaders(),HttpStatus.FORBIDDEN);
//		}
}
		
			
	public String currentState(String orderID,int processID ,int taskID, Integer maxLevel) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException, InterruptedException{
    	
    	logger.info("--------------------------Entering current state--------------------------\n");
    	
    	Gson json = new Gson();
    	propertiesLoader = new PropertiesLoader();
		
    	String bpmUrl = propertiesLoader.loadProperties("bpmurl");
    	String currentStateURL = bpmUrl + "/process/"+processID+"?parts=all";
    	
    	logger.info("-------------------- [CompleteTaskSynchronus] URL CURRENT STATE :"+currentStateURL+"------------------------------");
		
    	logger.info("Masuk Auth");
    	String plainCreds = propertiesLoader.loadProperties("plaincreds");
    	byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
	 
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Basic " + base64Creds);
		httpHeaders.setContentType(MediaType.APPLICATION_XML);
		logger.info("Auth is being processed");
		
		RestTemplate restTemplate = getRestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>("",httpHeaders);
		Thread.sleep(5000);
    	ResponseEntity<String> responseCurrStateBPM = restTemplate.exchange(currentStateURL, HttpMethod.GET, entity, String.class);
    	
    	String responseBodyCurrState = responseCurrStateBPM.getBody();
    	
		logger.info("----------------[CompleteTaskSynchronus] Response from BPM GetCurrentState : \n "+ responseBodyCurrState+"\n-------------------");
		
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
				Integer currentLevel = tasks.getData().getVariables().getCurrentLevel();
				
				if(null == currentLevel) {
					currentLevel = 0;
				}
				
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
					tasks.setMaxLevel(maxLevel);
					tasks.setCurrentLevel(currentLevel);
					
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
    	
    	logger.info("[CompleteTaskSynchronus]: Acction Callback Request : \n"+ acctionCallbackRequest);
		
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
