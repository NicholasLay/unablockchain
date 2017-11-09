package com.ibm.bpm.adira.domain;

public class ReleaseTaskResponseBean {

	private int processId;
	private int taskId;
	private String displayName; 
	private String assignToType;
	private String startTime;
	private String status;
	
	public int getProcessId() {
		return this.processId;
	}
	public void setProcessId(int processId) {
		this.processId = processId;
	}
	public int getTaskId() {
		return this.taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAssignToType() {
		return assignToType;
	}
	public void setAssignToType(String assignToType) {
		this.assignToType = assignToType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
