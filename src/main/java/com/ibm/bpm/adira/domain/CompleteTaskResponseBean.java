package com.ibm.bpm.adira.domain;

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
		
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getAssignToType() {
			return assignedToType;
		}
		public void setAssignToType(String assignToType) {
			this.assignedToType = assignToType;
		}
	}
}
