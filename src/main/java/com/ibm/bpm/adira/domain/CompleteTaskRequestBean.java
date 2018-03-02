package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.List;

/*
 *	This is a class for mapping the complete task request from Acction to API. 
 */

public class CompleteTaskRequestBean 
{

	public String orderID;
	public int processID;
	public int taskID;
	public ArrayList<String> GroupAlias;
	public String LocationAlias;
	public Boolean isAlias;
	public String isAOSCMOApprove;
	public String isAOSCFOApprove;
	public Boolean toIDE;
	public Boolean isDacor;
	public String surveyResult;
	public String approvalResult;
	public Boolean isMayor;
	public Boolean isSalesMayor;
	public Boolean isSignPK;
	public Integer maxLevel;
	public Boolean isDacorFDE;
	public Boolean isDacorDR;
	public Integer currentLevelOverride;
	public String docVerificationResult;
	public Integer lastApprovalLevel;
	
	
	
	public CompleteTaskRequestBean(){

	}
	
	public CompleteTaskRequestBean(String orderID, int processID , int taskID, ArrayList<String> GroupAlias, String LocationAlias , Boolean isAlias)
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
		this.GroupAlias = GroupAlias;
		this.LocationAlias = LocationAlias;
		this.isAlias = isAlias;
		
	}
	
	public CompleteTaskRequestBean(String orderID, int processID , int taskID, Boolean isDacor ,Boolean toIDE, String isAOSCMOApprove, String surveyResult )
	{
		this.orderID = orderID;
		this.processID = processID;
		this.taskID = taskID;
		this.isDacor = isDacor;
		this.toIDE = toIDE;
		this.isAOSCMOApprove = isAOSCMOApprove;
		this.surveyResult = surveyResult;
	}
	
	
	
	public Integer getLastApprovalLevel() {
		return lastApprovalLevel;
	}

	public void setLastApprovalLevel(Integer lastApprovalLevel) {
		this.lastApprovalLevel = lastApprovalLevel;
	}

	public String getDocVerificationResult() {
		return docVerificationResult;
	}

	public void setDocVerificationResult(String docVerificationResult) {
		this.docVerificationResult = docVerificationResult;
	}

	public Integer getCurrentLevelOverride() {
		return currentLevelOverride;
	}

	public void setCurrentLevelOverride(Integer currentLevelOverride) {
		this.currentLevelOverride = currentLevelOverride;
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

	public String getIsAOSCFOApprove() {
		return isAOSCFOApprove;
	}

	public void setIsAOSCFOApprove(String isAOSCFOApprove) {
		this.isAOSCFOApprove = isAOSCFOApprove;
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

	public Boolean getIsSignPK() {
		return isSignPK;
	}

	public void setIsSignPK(Boolean isSignPK) {
		this.isSignPK = isSignPK;
	}

	public String getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
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

	public ArrayList<String> getGroupAlias() {
		return GroupAlias;
	}

	public void setGroupAlias(ArrayList<String> groupAlias) {
		GroupAlias = groupAlias;
	}

	public String getLocationAlias() {
		return LocationAlias;
	}

	public void setLocationAlias(String locationAlias) {
		LocationAlias = locationAlias;
	}

	public Boolean getIsAlias() {
		return isAlias;
	}

	public void setIsAlias(Boolean isAlias) {
		this.isAlias = isAlias;
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
