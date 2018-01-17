package com.ibm.bpm.adira.domain;

import java.util.ArrayList;

public class GeneralRequestParameter {

	public String orderID;
	public String isAOSCMOApprove;
	public Boolean toIDE;
	public Boolean isDacor;
	public String surveyResult;
	public String approvalResult;
	public Integer brmsScoring;
	public String isAOSCFOApprove;
	public Boolean isCancelOrder;
	public Boolean isManualAssign;
	public Boolean needTele;
	public Boolean isMayor;
	public Boolean isSalesMayor;
	public Boolean isSignPK;
	public Integer maxLevel;
	public Boolean isDacorFDE;
	public Boolean isDacorDR;
	
	
	public GeneralRequestParameter(){
		
	}
	
	public GeneralRequestParameter(String orderID, int processID , Boolean isDacor ,Boolean toIDE, String isAOSCMOApprove, String surveyResult )
	{
		this.orderID = orderID;
		this.isDacor = isDacor;
		this.toIDE = toIDE;
		this.isAOSCMOApprove = isAOSCMOApprove;
		this.surveyResult = surveyResult;
	}

	
	
	
	public Boolean getIsDacorFDE() {
		return isDacorFDE;
	}

	public void setIsDacorFDE(Boolean isDacorFDE) {
		this.isDacorFDE = isDacorFDE;
	}

	public Boolean getIsDacorDR() {
		return isDacorDR;
	}

	public void setIsDacorDR(Boolean isDacorDR) {
		this.isDacorDR = isDacorDR;
	}

	public Integer getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
	}

	public Boolean getIsSignPK() {
		return isSignPK;
	}

	public void setIsSignPK(Boolean isSignPK) {
		this.isSignPK = isSignPK;
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

	public String getOrderID() {
		return orderID;
	}


	public void setOrderID(String orderID) {
		this.orderID = orderID;
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


	public String getApprovalResult() {
		return approvalResult;
	}


	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}


	public Integer getBrmsScoring() {
		return brmsScoring;
	}


	public void setBrmsScoring(Integer brmsScoring) {
		this.brmsScoring = brmsScoring;
	}


	public String getIsAOSCFOApprove() {
		return isAOSCFOApprove;
	}


	public void setIsAOSCFOApprove(String isAOSCFOApprove) {
		this.isAOSCFOApprove = isAOSCFOApprove;
	}


	public Boolean getIsCancelOrder() {
		return isCancelOrder;
	}


	public void setIsCancelOrder(Boolean isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}


	public Boolean getIsManualAssign() {
		return isManualAssign;
	}


	public void setIsManualAssign(Boolean isManualAssign) {
		this.isManualAssign = isManualAssign;
	}


	public Boolean getNeedTele() {
		return needTele;
	}


	public void setNeedTele(Boolean needTele) {
		this.needTele = needTele;
	}
}
