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
		public List<NexTaskId> nextTaskId;
		
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
		public List<NexTaskId> getNextTaskId() {
			return nextTaskId;
		}
		public void setNextTaskId(List<NexTaskId> nextTaskId) {
			this.nextTaskId = nextTaskId;
		}
		
	}

public class NexTaskId{
	
		String orderID;
		int taskID;
		
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
	}

}
