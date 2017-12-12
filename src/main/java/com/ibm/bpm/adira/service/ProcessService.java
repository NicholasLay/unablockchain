package com.ibm.bpm.adira.service;

import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean;

public interface ProcessService {

    void processCurrentState(String service,String orderID, int processID, int taskID);
}