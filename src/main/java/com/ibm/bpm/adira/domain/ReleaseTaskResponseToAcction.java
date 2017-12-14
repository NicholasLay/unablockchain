package com.ibm.bpm.adira.domain;

/*
 *	Class for handling Release Task Response from API to Acction. 
 */

public class ReleaseTaskResponseToAcction 
{
	String orderID;
	int taskID;
	String status;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
