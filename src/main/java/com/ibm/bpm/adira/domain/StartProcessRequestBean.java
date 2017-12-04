package com.ibm.bpm.adira.domain;

public class StartProcessRequestBean {
	
	public String orderID;
	public int processID;
	public Boolean isSignPK;
	public Boolean isTele;
	public Boolean isMayor;
	public int brmsScoring;
	
	
	
	public StartProcessRequestBean()
	{
		this.orderID = orderID;
		this.processID = processID;
		this.isSignPK = isSignPK;
		this.isTele = isTele;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
	}
	
	public StartProcessRequestBean(String orderID, int processID, int taskID, Boolean isMayor, int brmsScoring, Boolean isSignPK, Boolean isTele)
	{
		this.orderID = orderID;
		this.processID = processID;
		this.isSignPK = isSignPK;
		this.isTele = isTele;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
	}
	
	public Boolean getIsSignPK() {
		return isSignPK;
	}

	public void setIsSignPK(Boolean isSignPK) {
		this.isSignPK = isSignPK;
	}

	public Boolean getIsTele() {
		return isTele;
	}

	public void setIsTele(Boolean isTele) {
		this.isTele = isTele;
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
