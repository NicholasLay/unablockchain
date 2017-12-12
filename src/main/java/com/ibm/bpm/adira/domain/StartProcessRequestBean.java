package com.ibm.bpm.adira.domain;

public class StartProcessRequestBean {
	
	public String orderID;
	public Boolean isSignPK;
	public Boolean isTele;
	public Boolean isMayor;
	public Boolean isSalesMayor;
	public int brmsScoring;
	public Boolean isManualAssign;
	

	public StartProcessRequestBean()
	{
		this.orderID = orderID;
		this.isSignPK = isSignPK;
		this.isTele = isTele;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
		this.isSalesMayor = isSalesMayor;
		this.isManualAssign = isManualAssign;
	}
	
	public StartProcessRequestBean(String orderID, int processID, int taskID, Boolean isMayor, int brmsScoring, Boolean isSignPK, Boolean isTele, Boolean isSalesMayor, Boolean isManualAssign)
	{
		this.orderID = orderID;
		this.isSignPK = isSignPK;
		this.isTele = isTele;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
		this.isSalesMayor = isSalesMayor;
		this.isManualAssign = isManualAssign;
	}
	
	
	public Boolean getIsManualAssign() {
		return isManualAssign;
	}

	public void setIsManualAssign(Boolean isManualAssign) {
		this.isManualAssign = isManualAssign;
	}

	public Boolean getIsSalesMayor() {
		return isSalesMayor;
	}

	public void setIsSalesMayor(Boolean isSalesMayor) {
		this.isSalesMayor = isSalesMayor;
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
