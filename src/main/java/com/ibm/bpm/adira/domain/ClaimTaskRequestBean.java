package com.ibm.bpm.adira.domain;

import java.util.ArrayList;

/*
 * Class for mapping the claim task request from Acction to API
 */
public class ClaimTaskRequestBean 
{
	
	public String orderID;
	public int taskID;
	public Boolean mayor;
	public int brmsScoring;
	public String userClaim;
	private ArrayList<String> GroupAlias;
	private String LocationAlias;
	private boolean isAlias;
	
	
	public ClaimTaskRequestBean()
	{
		this.orderID = orderID;
		this.taskID = taskID;
		this.mayor = mayor;
		this.brmsScoring = brmsScoring;
	}
	
	public ClaimTaskRequestBean(String orderID, int taskID, Boolean mayor, int brmsScoring)
	{
		this.orderID = orderID;
		this.taskID = taskID;
		this.mayor = mayor;
		this.brmsScoring = brmsScoring;
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

	public String getUserClaim() {
		return userClaim;
	}

	public void setUserClaim(String userClaim) {
		this.userClaim = userClaim;
	}
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
