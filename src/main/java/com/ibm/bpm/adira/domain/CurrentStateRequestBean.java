package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for handling the current state request from Acction to API. 
 */

public class CurrentStateRequestBean {
	
	public String orderID;
	public int processID;
	public int taskID;
	public ArrayList<String> GroupAlias;
	public String LocationAlias;
	public boolean isAlias;
	public Integer maxLevel;
	public String approvalResult;
	public Integer currentlevelOverride;
	
	
	public CurrentStateRequestBean(){

	}
	
	
	
	public Integer getCurrentlevelOverride() {
		return currentlevelOverride;
	}



	public void setCurrentlevelOverride(Integer currentlevelOverride) {
		this.currentlevelOverride = currentlevelOverride;
	}



	public String getApprovalResult() {
		return approvalResult;
	}
	
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	
	public Integer getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(Integer maxLevel) {
		this.maxLevel = maxLevel;
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


	public boolean isAlias() {
		return isAlias;
	}


	public void setAlias(boolean isAlias) {
		this.isAlias = isAlias;
	}
	
}
