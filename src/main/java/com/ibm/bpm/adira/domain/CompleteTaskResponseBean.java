package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompleteTaskResponseBean {

	public String status;
	public Data data;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	
	
public class Data{
		
		private String displayName;
		private String assignedToType;
		private String piid;
		
		public String getPiid() {
			return piid;
		}
		public void setPiid(String piid) {
			this.piid = piid;
		}
		
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getAssignedToType() {
			return assignedToType;
		}
		public void setAssignedToType(String assignedToType) {
			this.assignedToType = assignedToType;
		}
	}

}
