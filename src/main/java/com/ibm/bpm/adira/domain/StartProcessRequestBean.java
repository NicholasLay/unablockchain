package com.ibm.bpm.adira.domain;

public class StartProcessRequestBean {
	
	public String orderID;
	public int processID;
//	public int taskID;
	public Boolean isMayor;
	public int brmsScoring;
	
	
	
	public StartProcessRequestBean()
	{
		this.orderID = orderID;
		this.processID = processID;
//		this.taskID = taskID;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
	}
	
	public StartProcessRequestBean(String orderID, int processID, int taskID, Boolean isMayor, int brmsScoring)
	{
		this.orderID = orderID;
		this.processID = processID;
//		this.taskID = taskID;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
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
//	public int getTaskID() {
//		return taskID;
//	}
//	public void setTaskID(int taskID) {
//		this.taskID = taskID;
//	}

	public Boolean getIsMayor() {
		return isMayor;
	}

	public void setIsMayor(Boolean isMayor) {
		this.isMayor = isMayor;
	}
	
	public int getBrmsScoring() {
		return brmsScoring;
	}
	
	public void setBrmsScoring(int brmsScoring) {
		this.brmsScoring = brmsScoring;
	}
	
}
