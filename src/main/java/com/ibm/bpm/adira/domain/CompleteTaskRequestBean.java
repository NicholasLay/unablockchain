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
	private ArrayList<String> GroupAlias;
	private String LocationAlias;
	private boolean isAlias;
	
	
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

	public boolean getIsAlias() {
		return isAlias;
	}
	public void setIsAlias(boolean isAlias) {
		this.isAlias = isAlias;
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
}
