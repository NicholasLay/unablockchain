package com.ibm.bpm.adira.domain;

public class AcctionCallBackRequestBean {
	private String orderID;
	private int processID;
	private int taskID;
	private String status;
	private String displayName;
	private String assignToType;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAssignToType() {
		return assignToType;
	}
	public void setAssignToType(String assignToType) {
		this.assignToType = assignToType;
	}
	
	
}
