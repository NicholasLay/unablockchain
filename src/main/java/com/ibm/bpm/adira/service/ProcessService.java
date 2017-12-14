package com.ibm.bpm.adira.service;

public interface ProcessService {

    void processCurrentState(String service,String orderID, int processID, int taskID);
}