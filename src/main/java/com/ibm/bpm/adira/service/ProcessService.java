package com.ibm.bpm.adira.service;

public interface ProcessService {

    void process(String orderID, int processID, int taskID);
}