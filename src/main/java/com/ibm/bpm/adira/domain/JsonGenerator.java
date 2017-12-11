package com.ibm.bpm.adira.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean.CurrentTask;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;
import com.ibm.bpm.adira.domain.StartProcessResponseBean.Tasks;
import com.ibm.bpm.adira.web.LoginController;
import java.util.*;
import java.util.function.*;
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
import org.springframework.web.client.RestTemplate;
import java.lang.*;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class JsonGenerator {

	public static void main(String[] args) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
	
	}
}
//for(Children rootChildren : currStateResponse.getData().getExecutionTree().getRoot().getChildren()){
//	for (Children2 childrenChild : rootChildren.getChildren()) {
//		for (Children3 childrenCreatedTask : childrenChild.getChildren()) {
//			
//			if(currStateResponse.getData().getExecutionTree().getRoot().getChildren().isEmpty() || 
//			   currStateResponse.getData().getExecutionTree().getRoot().getChildren().size() == 0 ||
//			   currStateResponse.getData().getExecutionTree().getRoot().getChildren() == null) {
//				logger.info("------TASK IS DONE-----");
//				
//				tasksRequestAcction.setTasks(emptyArray);
//				
//			}else {
//				
//				logger.info("------THE NEXT TASK IS : "+childrenCreatedTask.getCreateTaskIDs().get(0)+"-----");
//				
//				String taskDetailURL = "https://10.81.3.38:9443/rest/bpm/wle/v1/task/"+childrenCreatedTask.getCreateTaskIDs().get(0)+"?parts=all";
//				
//				logger.info("--------------------------Entering Task Detail--------------------------");
//				
//				String responseTaskDetail = restTemplate.getForObject(taskDetailURL, String.class, new HashMap<String,Object>() );
//				
//				TaskDetailBPMResponseBean taskDetailResponse = json.fromJson(responseTaskDetail, TaskDetailBPMResponseBean.class);
//			
//				List<TaskDetailBPMResponseBean> taskDetailResponseToAcction = new ArrayList<TaskDetailBPMResponseBean>();
//				
//				assignTo = taskDetailResponse.getData().getName();
//				taskDetailResponse.getData().setAssignTo(assignTo);
//				
//				taskDetailResponseToAcction.add(taskDetailResponse);
//				tasksRequestAcction.setTasks(taskDetailResponseToAcction);
//			}
//		}
//	}
//}
