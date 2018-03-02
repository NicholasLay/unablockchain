package com.ibm.bpm.adira.domain;

import java.util.List;
import com.ibm.bpm.adira.domain.CurrentStateResponseBean.TasksCurrentState;

/*
 * A class for handling the mapping for the Callback Request to Acction.
 */

public class AcctionCallBackRequestBean 
{
	
	private CurrentTask currentTask;
	private List<TasksCurrentState> tasks;

	public CurrentTask getCurrentTask() 
	{
		return currentTask;
	}
	public void setCurrentTask(CurrentTask currentTask) {
		this.currentTask = currentTask;
	}
 
	public List<TasksCurrentState> getTasks() {
		return tasks;
	}
	public void setTasks(List<TasksCurrentState> tasks) {
		this.tasks = tasks;
	}

public class CurrentTask{
	
		public String orderID;
		public int processID;
		public int taskID;
		public String dueTime;
		public String displayName;
		public String startTime;
		public String processInstanceName;
		public String status;
		public String userClaim;
		public String assignTo;
		public String assignToType;
		public String approvalResult;
		public Integer lastApprovalLevel;
		
		
		
		public Integer getLastApprovalLevel() {
			return lastApprovalLevel;
		}
		public void setLastApprovalLevel(Integer lastApprovalLevel) {
			this.lastApprovalLevel = lastApprovalLevel;
		}
		public String getApprovalResult() {
			return approvalResult;
		}
		public void setApprovalResult(String approvalResult) {
			this.approvalResult = approvalResult;
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
		public String getProcessInstanceName() {
			return processInstanceName;
		}
		public void setProcessInstanceName(String processInstanceName) {
			this.processInstanceName = processInstanceName;
		}
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

}

