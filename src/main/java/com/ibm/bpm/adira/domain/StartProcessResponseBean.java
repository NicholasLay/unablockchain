package com.ibm.bpm.adira.domain;

import java.util.List;

public class StartProcessResponseBean {

	private DataDetail data;
	private Object status;
	
	public Object getStatus() {
		return status;
	}
	public void setStatus(Object status) {
		this.status = status;
	}
	
	public DataDetail getData() {
		return data;
	}
	public void setData(DataDetail data) {
		this.data = data;
	}


public class DataDetail{
	
		private List<Tasks> tasks;
		
		public List<Tasks> getTasks() {
			return tasks;
		}
		public void setTasks(List<Tasks> tasks) {
			this.tasks = tasks;
		}
	}
	
public class Tasks{
		
		private String displayName;
		private String processInstanceName;
		private int tkiid;
		private String assignedToType;
		private String dueTime;
		private String name;
		private String piid;
		
	
		public String getPiid() {
			return piid;
		}
		public void setPiid(String piid) {
			this.piid = piid;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public String getProcessInstanceName() {
			return processInstanceName;
		}
		public void setProcessInstanceName(String processInstanceName) {
			this.processInstanceName = processInstanceName;
		}
		public int getTkiid() {
			return tkiid;
		}
		public void setTkiid(int tkiid) {
			this.tkiid = tkiid;
		}
		public String getAssignedToType() {
			return assignedToType;
		}
		public void setAssignedToType(String assignedToType) {
			this.assignedToType = assignedToType;
		}
		public String getDueTime() {
			return dueTime;
		}
		public void setDueTime(String dueTime) {
			this.dueTime = dueTime;
		}
	}
}
