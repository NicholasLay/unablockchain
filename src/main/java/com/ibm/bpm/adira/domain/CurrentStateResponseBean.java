package com.ibm.bpm.adira.domain;

import java.io.Serializable;
import java.util.List;

/*
 * Class for handling Current State Response from IBM BPM to API & API to Acction.
 */

public class CurrentStateResponseBean implements Serializable 
{


	private static final long serialVersionUID = 1L;
	public Data data;
	 
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

public class Data{
		public List<TasksCurrentState> tasks;

		public List<TasksCurrentState> getTasks() {
			return tasks;
		}

		public void setTasks(List<TasksCurrentState> tasks) {
			this.tasks = tasks;
		}
	}
		
public class TasksCurrentState {
			
			//Initializing variables for IBM BPM.
			public int tkiid;
			public String name;
			public String assignedTo;
			public String assignedToType;
			public String dueTime;
			public String displayName;
			public String startTime;
			public String processInstanceName;
			public int piid;
			public String status;
			
			// Initializing variables for Acction.
			public String assignTo;
			public String assignToType;
			public int taskID;
			public int processID; 
			
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public int getPiid() {
				return piid;
			}
			public void setPiid(int piid) {
				this.piid = piid;
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
			public String getAssignTo() {
				return assignTo;
			}
			public void setAssignTo(String assignTo) {
				this.assignTo = assignTo;
			}
			public String getAssignToType() {
				return assignToType;
			}
			public void setAssignToType(String assignToType) {
				this.assignToType = assignToType;
			}
			public String getAssignedTo() {
				return assignedTo;
			}
			public void setAssignedTo(String assignedTo) {
				this.assignedTo = assignedTo;
			}
			public String getAssignedToType() {
				return assignedToType;
			}
			public void setAssignedToType(String assignedToType) {
				this.assignedToType = assignedToType;
			}
			public String getProcessInstanceName() {
				return processInstanceName;
			}
			public void setProcessInstanceName(String processInstanceName) {
				this.processInstanceName = processInstanceName;
			}
			public String getDueTime() {
				return dueTime;
			}
			public void setDueTime(String dueTime) {
				this.dueTime = dueTime;
			}
			public String getDisplayName() {
				return displayName;
			}
			public void setDisplayName(String displayName) {
				this.displayName = displayName;
			}
			public String getStartTime() {
				return startTime;
			}
			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}
			public int getTkiid() {
				return tkiid;
			}
			public void setTkiid(int tkiid) {
				this.tkiid = tkiid;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
		}
}
