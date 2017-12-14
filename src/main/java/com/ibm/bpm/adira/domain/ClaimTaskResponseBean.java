package com.ibm.bpm.adira.domain;

/*
 *  Class to handle mapping of claim task response from IBM BPM to API.
 */

public class ClaimTaskResponseBean 
{

	private String status;
	private String userClaim;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getUserClaim() {
		return userClaim;
	}

	public void setUserClaim(String userClaim) {
		this.userClaim = userClaim;
	}
	
}
