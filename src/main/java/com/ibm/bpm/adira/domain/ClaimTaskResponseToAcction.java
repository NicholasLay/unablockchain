package com.ibm.bpm.adira.domain;

/*
 * Class for handling the mapping the API response to Acction. 
 */

public class ClaimTaskResponseToAcction 
{	
	String orderID;
	int taskID;
	String status;
	String userClaim;
	
	
	public String getUserClaim() {
		return userClaim;
	}
	public void setUserClaim(String userClaim) {
		this.userClaim = userClaim;
	}
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
