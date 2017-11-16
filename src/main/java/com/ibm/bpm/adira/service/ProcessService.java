package com.ibm.bpm.adira.service;

public interface ProcessService {

    void process(String service,String orderID, int processID, int taskID);
}