package com.ibm.bpm.adira.domain;

/*
 * Class for handling release task request from Acction to API.
 */
public class ReleaseTaskRequestBean {

	public String orderID;
	public int processID;
	public int taskID;
	public Boolean mayor;
	public int brmsScoring;
	
	
	public ReleaseTaskRequestBean()
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
		this.mayor = mayor;
		this.brmsScoring = brmsScoring;
	}
	
	public ReleaseTaskRequestBean(String orderID, int processID, int taskID, Boolean mayor, int brmsScoring)
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
		this.mayor = mayor;
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
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public Boolean getMayor() {
		return mayor;
	}
	public void setMayor(Boolean mayor) {
		this.mayor = mayor;
	}
	public int getBrmsScoring() {
		return brmsScoring;
	}
	public void setBrmsScoring(int brmsScoring) {
		this.brmsScoring = brmsScoring;
	}
	
	
}
