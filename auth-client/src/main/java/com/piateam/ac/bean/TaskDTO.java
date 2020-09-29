package com.piateam.ac.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class TaskDTO {
	private String	taskName;
	private Date	dueDate;
	private String	status;

	public TaskDTO() {
	}

	public TaskDTO(Task task) {
		this.setTaskName(task.getTaskname());
		this.setDueDate(task.getDueDate());
		this.setStatus(task.getStatus());
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
