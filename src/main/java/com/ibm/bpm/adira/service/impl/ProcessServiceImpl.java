package com.ibm.bpm.adira.service.impl;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;
import com.ibm.bpm.adira.domain.GlobalString;
import com.ibm.bpm.adira.domain.PropertiesLoader;
import com.ibm.bpm.adira.service.ProcessService;

@Service
public class ProcessServiceImpl implements ProcessService {
	
	private PropertiesLoader propertiesLoader = null;
    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
   
    @Async("processExecutor")
	@Override
	public void processCurrentState(String service, String orderID, int processID , int taskID, Integer maxLevel, String approvalResult, Integer currentLevelOverride)  {
		// TODO Auto-generated method stub
    	
    	 logger.info("Received request to process in ProcessServiceImpl.process()");
    	 logger.info("GETTING INTO CURRENT STATE PROCESS SERVICE");
         logger.info("Process Service Impl:Service="+service+ ", orderID="+orderID+ ", processID="+processID+"");
         
     	try {
     		currentState(orderID, processID,taskID,maxLevel,approvalResult, currentLevelOverride);
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
    
    public void callBackToAcctionCurrentState(String orderID,int processID, AcctionCallBackRequestBean nextTaskId, int taskID, String approvalResult)
    {
    	RestTemplate restTemplate = new RestTemplate();
    	Gson json = new Gson();
    	propertiesLoader = new PropertiesLoader();
		
    	String acctionUrl = propertiesLoader.loadProperties("acctionurl");
    	
    	logger.info("[ProcessServiceImplCurrentState] : ACCTION URL"+ acctionUrl);
    	
    	AcctionCallBackRequestBean acctionBean = new AcctionCallBackRequestBean();
    	
    	AcctionCallBackRequestBean.CurrentTask currentTaskRequest = acctionBean.new CurrentTask();
    	currentTaskRequest.setOrderID(orderID);
    	currentTaskRequest.setProcessID(processID);
    	currentTaskRequest.setTaskID(taskID);
    	currentTaskRequest.setApprovalResult(approvalResult);
    	
    	acctionBean.setCurrentTask(currentTaskRequest);   	
    	acctionBean.setTasks(nextTaskId.getTasks());
    	
    	String acctionCallbackRequest = json.toJson(acctionBean);
    	
    	logger.info("[ProcessServiceImpl]: Acction Callback Request : \n"+ acctionCallbackRequest);
    	
        HttpHeaders headers = new HttpHeaders();
        
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(acctionCallbackRequest,headers);
        
        String answer = restTemplate.postForObject(acctionUrl, entity, String.class);
    	logger.info("Process Service Impl : Response Callback from acction"+ answer);
    
    }
    
    @SuppressWarnings({ "unchecked", "null" })
    public void currentState(String orderID,int processID ,int taskID, Integer maxLevel, String approvalResult, Integer currentLevelOverride) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException{
    	
    	logger.info("--------------------------Entering current state--------------------------\n");
    	
    	propertiesLoader = new PropertiesLoader();
		
    	String bpmUrl = propertiesLoader.loadProperties("bpmurl");
    	Gson json = new Gson();
    	
    	String currentStateURL = bpmUrl + "/process/"+processID+"?parts=all";
    	
    	logger.info("-------------------- [ProcessServiceImpl] URL CURRENT STATE :"+currentStateURL+"------------------------------");
    	
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
    	
    	ResponseEntity<String> responseCurrStateBPM = restTemplate.exchange(currentStateURL, HttpMethod.GET, entity, String.class);
    	
    	String responseBodyCurrState = responseCurrStateBPM.getBody();
    	
		logger.info("----------------[ProcessServiceImpl] Response from BPM GetCurrentState : \n "+ responseBodyCurrState+"\n-------------------");
		
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
				Integer rejectLevel = tasks.getData().getVariables().getRejectLevel();
				
				
				
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
					tasks.setCurrentLevelOverride(currentLevelOverride);
					tasks.setRejectLevel(rejectLevel);
					
					
					
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
		callBackToAcctionCurrentState(orderID, processID,tasksRequestAcction,taskID,approvalResult);
	
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