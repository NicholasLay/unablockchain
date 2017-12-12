package com.ibm.bpm.adira.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean.CurrentTask;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;
import com.ibm.bpm.adira.domain.StartProcessResponseBean.Tasks;
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
	
		Gson json = new Gson();
		String responseBodyCurrState = "{\r\n" + 
				"  \"status\": \"200\",\r\n" + 
				"  \"data\": {\r\n" + 
				"    \"creationTime\": \"2017-12-11T08:12:26Z\",\r\n" + 
				"    \"description\": \"\",\r\n" + 
				"    \"richDescription\": \"\",\r\n" + 
				"    \"executionState\": \"Active\",\r\n" + 
				"    \"state\": \"STATE_RUNNING\",\r\n" + 
				"    \"lastModificationTime\": \"2017-12-11T08:12:26Z\",\r\n" + 
				"    \"name\": \"Main Process IDE:1485\",\r\n" + 
				"    \"piid\": \"1485\",\r\n" + 
				"    \"caseFolderID\": \"2126.512c7b00-0630-4014-ad63-f1b044152d96\",\r\n" + 
				"    \"caseFolderServerName\": \"IBM_BPM_ManagedStore\",\r\n" + 
				"    \"processTemplateID\": \"25.9a0484ab-9ece-44e0-8cc2-e086172e2cc1\",\r\n" + 
				"    \"processTemplateName\": \"Main Process IDE\",\r\n" + 
				"    \"processAppName\": \"Adira Process\",\r\n" + 
				"    \"processAppAcronym\": \"AP\",\r\n" + 
				"    \"processAppID\": \"2066.c464e5f1-3399-406f-a208-eddaad75b871\",\r\n" + 
				"    \"snapshotName\": \"API DEVELOPMENT 11/12/17\",\r\n" + 
				"    \"snapshotID\": \"2064.5d73b065-515d-41e5-9f3d-8c44f418c988\",\r\n" + 
				"    \"branchID\": \"2063.b06bda07-3d67-4084-8f37-3d6053dc2d24\",\r\n" + 
				"    \"branchName\": \"Main\",\r\n" + 
				"    \"snapshotTip\": false,\r\n" + 
				"    \"startingDocumentServerName\": null,\r\n" + 
				"    \"dueDate\": \"2017-12-11T16:12:26Z\",\r\n" + 
				"    \"comments\": [],\r\n" + 
				"    \"tasks\": [\r\n" + 
				"      {\r\n" + 
				"        \"activationTime\": \"2017-12-11T08:12:26Z\",\r\n" + 
				"        \"atRiskTime\": \"2017-12-11T05:55:07Z\",\r\n" + 
				"        \"clientTypes\": [\r\n" + 
				"          \"IBM_WLE_Coach\"\r\n" + 
				"        ],\r\n" + 
				"        \"completionTime\": \"2017-12-11T08:12:27Z\",\r\n" + 
				"        \"containmentContextID\": \"1485\",\r\n" + 
				"        \"description\": \"\",\r\n" + 
				"        \"displayName\": \"Step: IDE\",\r\n" + 
				"        \"isAtRisk\": true,\r\n" + 
				"        \"kind\": \"KIND_PARTICIPATING\",\r\n" + 
				"        \"lastModificationTime\": \"2017-12-11T08:12:27Z\",\r\n" + 
				"        \"originator\": \"bpmadmin\",\r\n" + 
				"        \"priority\": 30,\r\n" + 
				"        \"startTime\": \"2017-12-11T08:12:26Z\",\r\n" + 
				"        \"state\": \"STATE_FINISHED\",\r\n" + 
				"        \"piid\": \"1485\",\r\n" + 
				"        \"processInstanceName\": \"Main Process IDE:1485\",\r\n" + 
				"        \"priorityName\": \"Normal\",\r\n" + 
				"        \"data\": {\r\n" + 
				"          \"variables\": {\r\n" + 
				"            \"bpdInstanceName\": null,\r\n" + 
				"            \"subject\": null,\r\n" + 
				"            \"bpdName\": null,\r\n" + 
				"            \"activityName\": null,\r\n" + 
				"            \"taskNarrative\": null,\r\n" + 
				"            \"bpdInstanceId\": null,\r\n" + 
				"            \"defaultText\": null,\r\n" + 
				"            \"bpdDescription\": null,\r\n" + 
				"            \"activityDescription\": null,\r\n" + 
				"            \"userName\": null,\r\n" + 
				"            \"taskId\": null\r\n" + 
				"          }\r\n" + 
				"        },\r\n" + 
				"        \"externalActivitySnapshotID\": null,\r\n" + 
				"        \"serviceID\": \"1.c2d00e9e-9c34-3e08-9258-101bd61b964d\",\r\n" + 
				"        \"serviceSnapshotID\": \"2064.c7680890-5385-3f24-bbc9-20da937ac8c4\",\r\n" + 
				"        \"flowObjectID\": \"367ce9e0-6e92-4232-870f-7b2bd2664762\",\r\n" + 
				"        \"nextTaskId\": null,\r\n" + 
				"        \"collaboration\": {\r\n" + 
				"          \"status\": false,\r\n" + 
				"          \"currentUsers\": []\r\n" + 
				"        },\r\n" + 
				"        \"actions\": null,\r\n" + 
				"        \"tkiid\": \"2419\",\r\n" + 
				"        \"name\": \"IDE\",\r\n" + 
				"        \"status\": \"Closed\",\r\n" + 
				"        \"owner\": \"acction\",\r\n" + 
				"        \"assignedTo\": \"acction\",\r\n" + 
				"        \"assignedToDisplayName\": \"Acction Adira\",\r\n" + 
				"        \"assignedToType\": \"user\",\r\n" + 
				"        \"dueTime\": \"2017-12-11T09:12:26Z\"\r\n" + 
				"      }\r\n" + 
				"    ],\r\n" + 
				"    \"documents\": [],\r\n" + 
				"    \"actionDetails\": null,\r\n" + 
				"    \"data\": \"\\\"{\\\\\\\"orderID\\\\\\\":\\\\\\\"0000170321026586\\\\\\\",\\\\\\\"brmsScoring\\\\\\\":1,\\\\\\\"isManualAssign\\\\\\\":true}\\\"\",\r\n" + 
				"    \"variables\": {\r\n" + 
				"      \"orderID\": \"0000170321026586\",\r\n" + 
				"      \"brmsScoring\": 1,\r\n" + 
				"      \"isManualAssign\": true\r\n" + 
				"    },\r\n" + 
				"    \"relationship\": [],\r\n" + 
				"    \"businessData\": [\r\n" + 
				"      {\r\n" + 
				"        \"name\": \"orderID\",\r\n" + 
				"        \"type\": \"String\",\r\n" + 
				"        \"alias\": \"orderIDAcction\",\r\n" + 
				"        \"label\": \"Order ID Acction\",\r\n" + 
				"        \"value\": \"0000170321026586\"\r\n" + 
				"      }\r\n" + 
				"    ],\r\n" + 
				"    \"executionTree\": {\r\n" + 
				"      \"executionStatus\": \"1\",\r\n" + 
				"      \"root\": {\r\n" + 
				"        \"name\": \"Main Process IDE\",\r\n" + 
				"        \"nodeId\": \"1\",\r\n" + 
				"        \"createdTaskIDs\": null,\r\n" + 
				"        \"variables\": {\r\n" + 
				"          \"orderID\": \"0000170321026586\",\r\n" + 
				"          \"brmsScoring\": 1,\r\n" + 
				"          \"isManualAssign\": true\r\n" + 
				"        },\r\n" + 
				"        \"children\": [\r\n" + 
				"          {\r\n" + 
				"            \"name\": \"Instant Approval\",\r\n" + 
				"            \"nodeId\": \"8\",\r\n" + 
				"            \"flowObjectId\": \"d99f29fa-5f7c-4202-8be8-8a5be1282553\",\r\n" + 
				"            \"taskType\": \"5\",\r\n" + 
				"            \"tokenId\": \"10\",\r\n" + 
				"            \"createdTaskIDs\": null,\r\n" + 
				"            \"variables\": {\r\n" + 
				"              \"orderID\": \"0000170321026586\",\r\n" + 
				"              \"brmsScoring\": 1,\r\n" + 
				"              \"isManualAssign\": true\r\n" + 
				"            },\r\n" + 
				"            \"children\": [\r\n" + 
				"              {\r\n" + 
				"                \"name\": \"Process \\\"Process Instant Approval\\\"\",\r\n" + 
				"                \"nodeId\": \"9\",\r\n" + 
				"                \"processId\": \"25.7f13b43e-97c0-4fd6-905a-2413c2044832\",\r\n" + 
				"                \"snapshotId\": \"2064.5d73b065-515d-41e5-9f3d-8c44f418c988\",\r\n" + 
				"                \"createdTaskIDs\": null,\r\n" + 
				"                \"children\": [\r\n" + 
				"                  {\r\n" + 
				"                    \"name\": \"to PO\",\r\n" + 
				"                    \"nodeId\": \"16\",\r\n" + 
				"                    \"flowObjectId\": \"70f49068-c714-4803-8202-a381e076d279\",\r\n" + 
				"                    \"taskType\": \"5\",\r\n" + 
				"                    \"tokenId\": \"22\",\r\n" + 
				"                    \"createdTaskIDs\": null,\r\n" + 
				"                    \"variables\": {\r\n" + 
				"                      \"orderID\": \"0000170321026586\",\r\n" + 
				"                      \"isInsap\": true,\r\n" + 
				"                      \"isManualAssign\": true,\r\n" + 
				"                      \"brmsScoring\": 1\r\n" + 
				"                    },\r\n" + 
				"                    \"children\": [\r\n" + 
				"                      {\r\n" + 
				"                        \"name\": \"Process \\\"PO\\\"\",\r\n" + 
				"                        \"nodeId\": \"18\",\r\n" + 
				"                        \"processId\": \"25.5f029433-59e5-49a8-b224-0a169e9bc868\",\r\n" + 
				"                        \"snapshotId\": \"2064.5d73b065-515d-41e5-9f3d-8c44f418c988\",\r\n" + 
				"                        \"createdTaskIDs\": null,\r\n" + 
				"                        \"children\": [\r\n" + 
				"                          {\r\n" + 
				"                            \"name\": \"PO\",\r\n" + 
				"                            \"nodeId\": \"23\",\r\n" + 
				"                            \"flowObjectId\": \"c1048936-448f-428d-b550-39b287027a21\",\r\n" + 
				"                            \"taskType\": \"1\",\r\n" + 
				"                            \"tokenId\": \"30\",\r\n" + 
				"                            \"createdTaskIDs\": [\r\n" + 
				"                              \"2420\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"variables\": {\r\n" + 
				"                              \"isSignPK\": true,\r\n" + 
				"                              \"orderID\": \"0000170321026586\",\r\n" + 
				"                              \"isDacor\": true,\r\n" + 
				"                              \"brmsScoring\": 1,\r\n" + 
				"                              \"isMayor\": true,\r\n" + 
				"                              \"isAOSCMO\": true,\r\n" + 
				"                              \"isTele\": false,\r\n" + 
				"                              \"isCancelOrder\": false\r\n" + 
				"                            },\r\n" + 
				"                            \"children\": null\r\n" + 
				"                          }\r\n" + 
				"                        ]\r\n" + 
				"                      }\r\n" + 
				"                    ]\r\n" + 
				"                  },\r\n" + 
				"                  {\r\n" + 
				"                    \"name\": \"Telesurvey\",\r\n" + 
				"                    \"nodeId\": \"22\",\r\n" + 
				"                    \"flowObjectId\": \"f7d5a20c-f3aa-4415-8165-320cf5f59c6e\",\r\n" + 
				"                    \"taskType\": \"5\",\r\n" + 
				"                    \"tokenId\": \"29\",\r\n" + 
				"                    \"createdTaskIDs\": null,\r\n" + 
				"                    \"variables\": {\r\n" + 
				"                      \"orderID\": \"0000170321026586\",\r\n" + 
				"                      \"isInsap\": true,\r\n" + 
				"                      \"isManualAssign\": true,\r\n" + 
				"                      \"brmsScoring\": 1\r\n" + 
				"                    },\r\n" + 
				"                    \"children\": [\r\n" + 
				"                      {\r\n" + 
				"                        \"name\": \"Process \\\"Telesurvey\\\"\",\r\n" + 
				"                        \"nodeId\": \"24\",\r\n" + 
				"                        \"processId\": \"25.7bf33f2c-edaa-4f4d-b02d-cf906557ffce\",\r\n" + 
				"                        \"snapshotId\": \"2064.5d73b065-515d-41e5-9f3d-8c44f418c988\",\r\n" + 
				"                        \"createdTaskIDs\": null,\r\n" + 
				"                        \"children\": [\r\n" + 
				"                          {\r\n" + 
				"                            \"name\": \"SA TELE\",\r\n" + 
				"                            \"nodeId\": \"28\",\r\n" + 
				"                            \"flowObjectId\": \"617431ea-8444-4906-8793-ca0de5393ee3\",\r\n" + 
				"                            \"taskType\": \"1\",\r\n" + 
				"                            \"tokenId\": \"35\",\r\n" + 
				"                            \"createdTaskIDs\": [\r\n" + 
				"                              \"2421\"\r\n" + 
				"                            ],\r\n" + 
				"                            \"variables\": {\r\n" + 
				"                              \"isSignPK\": true,\r\n" + 
				"                              \"orderID\": \"0000170321026586\",\r\n" + 
				"                              \"isInsap\": true,\r\n" + 
				"                              \"isManualAssign\": true,\r\n" + 
				"                              \"brmsScoring\": 1,\r\n" + 
				"                              \"toIDE\": false,\r\n" + 
				"                              \"isContactable\": \"\",\r\n" + 
				"                              \"toCA\": false\r\n" + 
				"                            },\r\n" + 
				"                            \"children\": null\r\n" + 
				"                          }\r\n" + 
				"                        ]\r\n" + 
				"                      }\r\n" + 
				"                    ]\r\n" + 
				"                  }\r\n" + 
				"                ]\r\n" + 
				"              }\r\n" + 
				"            ]\r\n" + 
				"          }\r\n" + 
				"        ]\r\n" + 
				"      }\r\n" + 
				"    },\r\n" + 
				"    \"diagram\": {\r\n" + 
				"      \"processAppID\": \"2066.c464e5f1-3399-406f-a208-eddaad75b871\",\r\n" + 
				"      \"milestone\": null,\r\n" + 
				"      \"step\": [\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"Instant Approval\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.7f13b43e-97c0-4fd6-905a-2413c2044832\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 138,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": [\r\n" + 
				"            \"10\"\r\n" + 
				"          ],\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"d99f29fa-5f7c-4202-8be8-8a5be1282553\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"telesurvey\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.7bf33f2c-edaa-4f4d-b02d-cf906557ffce\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 248,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"674f2304-8d67-4270-8be3-bc2449164c39\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"Reguler\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.33b471ee-dcb5-4d4e-a440-8d91cea5895f\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 368,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"40d68eb2-83d8-46a1-8455-73181a3591a1\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"AOS CMO\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.48d053d6-2ac6-4e14-9380-8dbbfde2b607\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 478,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"e72aa54a-f91d-4c9e-83aa-ad72405f58eb\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"AOS CFO\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.8d4b63a2-fe69-465e-b96d-37467d8a9b24\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 608,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"b68a0350-f4d5-4e26-8db6-95f7caae7ef6\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"RPPD\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.8399bc28-3d08-4892-af4f-7b1d4da64ac3\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 1170,\r\n" + 
				"          \"y\": 327,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"6c5063ef-36da-4692-b4d3-41ef08f5dead\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"External\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"subBpd\",\r\n" + 
				"          \"externalID\": \"25.6e57fe7c-2060-43e9-83bd-922f878d360f\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 565,\r\n" + 
				"          \"y\": 38,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"c09208a5-6e99-4a20-81a5-8f6704b2329f\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To RPPD\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"9b033efd-ef02-4302-8662-b84b692e23fb\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"IDE\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": \"task\",\r\n" + 
				"          \"externalID\": \"1.c2d00e9e-9c34-3e08-9258-101bd61b964d\",\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 145,\r\n" + 
				"          \"y\": 77,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"aa06863d-11ba-46cb-843d-a1d48e62bf49\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To Scoring\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": \"2419\",\r\n" + 
				"          \"ID\": \"367ce9e0-6e92-4232-870f-7b2bd2664762\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"Start\",\r\n" + 
				"          \"type\": \"event\",\r\n" + 
				"          \"activityType\": null,\r\n" + 
				"          \"externalID\": null,\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 43,\r\n" + 
				"          \"y\": 50,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"367ce9e0-6e92-4232-870f-7b2bd2664762\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To IDE\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"3335efe6-80b9-4603-a3b0-ab1602c11fb6\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"End\",\r\n" + 
				"          \"type\": \"event\",\r\n" + 
				"          \"activityType\": null,\r\n" + 
				"          \"externalID\": null,\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 1407,\r\n" + 
				"          \"y\": 312,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": null,\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"6c5063ef-36da-4692-b4d3-41ef08f5dead\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"back to IDE\",\r\n" + 
				"          \"type\": \"event\",\r\n" + 
				"          \"activityType\": null,\r\n" + 
				"          \"externalID\": null,\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 25,\r\n" + 
				"          \"y\": 157,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"367ce9e0-6e92-4232-870f-7b2bd2664762\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To IDE\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"b682f8f7-f90e-4441-82f9-ba6cc8792e28\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"Scoring Result\",\r\n" + 
				"          \"type\": \"gateway\",\r\n" + 
				"          \"activityType\": null,\r\n" + 
				"          \"externalID\": null,\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"IDE\",\r\n" + 
				"          \"x\": 324,\r\n" + 
				"          \"y\": 308,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"d99f29fa-5f7c-4202-8be8-8a5be1282553\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To Instant Approval\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"40d68eb2-83d8-46a1-8455-73181a3591a1\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To Reguler\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"e72aa54a-f91d-4c9e-83aa-ad72405f58eb\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To AOS CMO\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"b68a0350-f4d5-4e26-8db6-95f7caae7ef6\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To AOS CFO\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"674f2304-8d67-4270-8be3-bc2449164c39\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To telesurvey\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"9b033efd-ef02-4302-8662-b84b692e23fb\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To External\"\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"6c5063ef-36da-4692-b4d3-41ef08f5dead\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To End\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"f642fc34-1029-4c9a-8465-fe3dc5fb5c09\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"Scoring\",\r\n" + 
				"          \"type\": \"activity\",\r\n" + 
				"          \"activityType\": null,\r\n" + 
				"          \"externalID\": null,\r\n" + 
				"          \"diagram\": null,\r\n" + 
				"          \"lane\": \"System\",\r\n" + 
				"          \"x\": 214,\r\n" + 
				"          \"y\": 74,\r\n" + 
				"          \"attachedTimer\": null,\r\n" + 
				"          \"preTrackingPoint\": null,\r\n" + 
				"          \"postTrackingPoint\": null,\r\n" + 
				"          \"attachedEventHandler\": null,\r\n" + 
				"          \"lines\": [\r\n" + 
				"            {\r\n" + 
				"              \"to\": \"f642fc34-1029-4c9a-8465-fe3dc5fb5c09\",\r\n" + 
				"              \"points\": \"\",\r\n" + 
				"              \"tokenID\": null,\r\n" + 
				"              \"name\": \"To Scoring Result\"\r\n" + 
				"            }\r\n" + 
				"          ],\r\n" + 
				"          \"tokenID\": null,\r\n" + 
				"          \"taskID\": null,\r\n" + 
				"          \"ID\": \"aa06863d-11ba-46cb-843d-a1d48e62bf49\"\r\n" + 
				"        }\r\n" + 
				"      ],\r\n" + 
				"      \"lanes\": [\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"IDE\",\r\n" + 
				"          \"height\": 700,\r\n" + 
				"          \"system\": false\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"          \"name\": \"System\",\r\n" + 
				"          \"height\": 200,\r\n" + 
				"          \"system\": true\r\n" + 
				"        }\r\n" + 
				"      ],\r\n" + 
				"      \"orphaned\": null\r\n" + 
				"    },\r\n" + 
				"    \"actions\": null,\r\n" + 
				"    \"starterId\": \"2048.1016\"\r\n" + 
				"  }\r\n" + 
				"}";
		
		
		CurrentStateResponseBean currStateResponse = json.fromJson(responseBodyCurrState, CurrentStateResponseBean.class);
		AcctionCallBackRequestBean tasksRequestAcction = new AcctionCallBackRequestBean();
		
		List<TasksCurrentState> taskDetailResponseToAcction = new ArrayList<TasksCurrentState>();
		
		List<TasksCurrentState> emptyArray = new ArrayList<TasksCurrentState>();
		
		
		for(TasksCurrentState tasks : currStateResponse.getData().getTasks()){
			
		String	assignTo = tasks.getName();
		String	assignToType = tasks.getAssignedToType();	
		int taskIdNextask = tasks.getTkiid();
		int	processId = tasks.getPiid();
		String status = tasks.getStatus();
			
//			logger.info("Detail for task : "+tasks.getTkiid()+" is : "
//					+ "assignTo: "+assignTo+""
//					+ "assignToType: "+assignToType+""
//					+ "processId : "+processId+""
//					+ "status : "+status+"");
			
			if (!status.equals(GlobalString.STATUS_TASK_CLOSED)) {
			
				
				tasks.setDisplayName(tasks.getName());
				tasks.setProcessID(processId);
				tasks.setAssignTo(assignTo);
				tasks.setAssignToType(assignToType);
				tasks.setTaskID(taskIdNextask);
				
				taskDetailResponseToAcction.add(tasks);
				
				tasksRequestAcction.setTasks(taskDetailResponseToAcction);
			}else {
//				tasksRequestAcction.setTasks(emptyArray);
//				logger.info("Status = "+status+" , Task Depereciated!");
			}
			
		}
		
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
