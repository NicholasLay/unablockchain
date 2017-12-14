package com.ibm.bpm.adira.domain;

/*
 * Class for handling the current state request from Acction to API. 
 */

public class CurrentStateRequestBean {
	
	public String orderID;
	public int processID;
	public int taskID;
	
	
	public CurrentStateRequestBean()
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
	}
	
	public CurrentStateRequestBean(String orderID, int processID , int taskID)
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
	}
	
	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	
	public int getProcessID() {
		return processID;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
}
