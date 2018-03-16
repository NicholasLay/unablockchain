package com.una.core.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
//schema_example
@Entity
@Table(name = "table_tutorial", schema="public")
public class TestingBean implements Serializable{
	 private static final long serialVersionUID = 1L;

	public TestingBean() {
		
	}
	 
	public TestingBean(String employeeName, String employeeRole) {
		this.employeeName = employeeName;
		this.employeeRole = employeeRole;
	}


	public TestingBean(int employeeID, String employeeName, String employeeRole) {
		this.employeeID   = employeeID;
		this.employeeName = employeeName;
		this.employeeRole = employeeRole;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	public int employeeID;
	
	@Column(name = "employee_name")
	public String employeeName;
	
	@Column(name = "employee_role")
	public String employeeRole;
	
	
	//Setter Getter
	public String getEmployeeName() {
		return employeeName;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeRole() {
		return employeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		this.employeeRole = employeeRole;
	}

	
}
