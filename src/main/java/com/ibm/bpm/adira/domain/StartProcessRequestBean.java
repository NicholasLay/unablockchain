package com.ibm.bpm.adira.domain;

/*
 *	Class for handling the request for start process request from Acction to API. 
 */

public class StartProcessRequestBean 
{
	
	public String orderID;
	public Boolean isSignPK;
	public Boolean needTele;
	public Boolean isMayor;
	public Boolean isSalesMayor;
	public int brmsScoring;
	public Boolean isManualAssign;
	public String isAOSCMOApprove;
	public Boolean toIDE;
	public Boolean isDacor;
	public String surveyResult;
	

	public StartProcessRequestBean(){
	}
	
	public StartProcessRequestBean(String orderID, int processID, int taskID, Boolean isMayor, int brmsScoring,
			Boolean isSignPK, Boolean needTele, Boolean isSalesMayor, Boolean isManualAssign, Boolean isDacor, Boolean toIDE , String isAOSCMOApprove , String surveyResult)
	{
		this.orderID = orderID;
		this.isSignPK = isSignPK;
		this.needTele = needTele;
		this.isMayor = isMayor;
		this.brmsScoring = brmsScoring;
		this.isSalesMayor = isSalesMayor;
		this.isManualAssign = isManualAssign;
		this.isDacor = isDacor;
		this.toIDE = toIDE;
		this.isAOSCMOApprove = isAOSCMOApprove;
		this.surveyResult = surveyResult;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public Boolean getIsSignPK() {
		return isSignPK;
	}

	public void setIsSignPK(Boolean isSignPK) {
		this.isSignPK = isSignPK;
	}

	public Boolean getNeedTele() {
		return needTele;
	}

	public void setNeedTele(Boolean needTele) {
		this.needTele = needTele;
	}

	public Boolean getIsMayor() {
		return isMayor;
	}

	public void setIsMayor(Boolean isMayor) {
		this.isMayor = isMayor;
	}

	public Boolean getIsSalesMayor() {
		return isSalesMayor;
	}

	public void setIsSalesMayor(Boolean isSalesMayor) {
		this.isSalesMayor = isSalesMayor;
	}

	public int getBrmsScoring() {
		return brmsScoring;
	}

	public void setBrmsScoring(int brmsScoring) {
		this.brmsScoring = brmsScoring;
	}

	public Boolean getIsManualAssign() {
		return isManualAssign;
	}

	public void setIsManualAssign(Boolean isManualAssign) {
		this.isManualAssign = isManualAssign;
	}

	public String getIsAOSCMOApprove() {
		return isAOSCMOApprove;
	}

	public void setIsAOSCMOApprove(String isAOSCMOApprove) {
		this.isAOSCMOApprove = isAOSCMOApprove;
	}

	public Boolean getToIDE() {
		return toIDE;
	}

	public void setToIDE(Boolean toIDE) {
		this.toIDE = toIDE;
	}

	public Boolean getIsDacor() {
		return isDacor;
	}

	public void setIsDacor(Boolean isDacor) {
		this.isDacor = isDacor;
	}

	public String getSurveyResult() {
		return surveyResult;
	}

	public void setSurveyResult(String surveyResult) {
		this.surveyResult = surveyResult;
	}
}
