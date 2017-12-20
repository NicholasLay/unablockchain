package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.List;

/*
 * Class to handling the mapping of the request from API to Ad1. 
 */

public class Ad1NIKRequest 
{
	private ArrayList<String> GroupAlias;
	private String LocationAlias;
	private boolean isAlias;
	
	
	public ArrayList<String> getGroupAlias() {
		return GroupAlias;
	}
	public void setGroupAlias(ArrayList<String> groupAlias) {
		GroupAlias = groupAlias;
	}
	public void setAlias(boolean isAlias) {
		this.isAlias = isAlias;
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
}
