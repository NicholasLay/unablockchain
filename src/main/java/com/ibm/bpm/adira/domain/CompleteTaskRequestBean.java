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
	public Boolean toIDE;
	public Boolean isDacor;
	public String surveyResult;
	public String approvalResult;
	
	
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
