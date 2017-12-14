package com.ibm.bpm.adira.domain;

/*
 *	This is a class for mapping the complete task request from Acction to API. 
 */

public class CompleteTaskRequestBean 
{

	public String orderID;
	public int processID;
	public int taskID;

	
	
	public CompleteTaskRequestBean()
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;

	}
	
	public CompleteTaskRequestBean(String orderID, int processID, int taskID, Boolean mayor, int brmsScoring)
	{
		this.orderID = orderID;
		this.processID = processID;
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
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

}
