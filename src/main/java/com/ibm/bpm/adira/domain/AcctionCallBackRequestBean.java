package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.List;

public class AcctionCallBackRequestBean {
	
	public CurrentTask currentTask;
	public List<TasksAcction> tasks;

	
	public CurrentTask getCurrentTask() {
		return currentTask;
	}
	
	public void setCurrentTask(CurrentTask currentTask) {
		this.currentTask = currentTask;
	}
	public List<TasksAcction> getTasks() {
		return tasks;
	}

	public void setTasks(List<TasksAcction> tasks) {
		this.tasks = tasks;
	}
	
public class CurrentTask{
		public String orderID;
		public int processID;
		public int taskID;
		
		public CurrentTask(String oId, int proId, int taskId) {
			this.orderID = oId;
			this.processID = proId;
			this.taskID = taskId;
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

public class TasksAcction{
		 
		private String orderID;
		private int processID;
		private int taskID;
		private String status;
	
		public TasksAcction(String string, int i, int j, String string2) {
			// TODO Auto-generated constructor stub
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
	
	
}
