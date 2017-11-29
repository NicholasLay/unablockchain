package com.ibm.bpm.adira.domain;

public class CompleteTaskResponseToAcction {

//	public Tasks getTasks() {
//		return tasks;
//	}
//
//	public void setTasks(Tasks tasks) {
//		this.tasks = tasks;
//	}

	public class Tasks{
		
		private String orderID;
		private int taskID;
		private String status;
	
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
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}
	
	
	
}
