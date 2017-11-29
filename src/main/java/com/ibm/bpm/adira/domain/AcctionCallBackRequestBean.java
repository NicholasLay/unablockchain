package com.ibm.bpm.adira.domain;

import java.util.ArrayList;
import java.util.List;

import com.ibm.bpm.adira.domain.AcctionCallBackRequestBean.CurrentTask;
import com.ibm.bpm.adira.domain.CompleteTaskResponseBean.NexTaskId;
import com.ibm.bpm.adira.domain.CompleteTaskResponseToAcction.Tasks;

public class AcctionCallBackRequestBean {
	
	private CurrentTask currentTask;
	private List<NexTaskId> tasks;

	public CurrentTask getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(CurrentTask currentTask) {
		this.currentTask = currentTask;
	}
	public List<NexTaskId> getTasks() {
		return tasks;
	}
	public void setTasks(List<NexTaskId> tasks) {
		this.tasks = tasks;
	}


public class CurrentTask{
	
		public String orderID;
		public int processID;
		public int taskID;
		
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

