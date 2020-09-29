package com.piateam.ac.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "Task")
@Table(name = "task")
public class Task implements Serializable {
	public Task() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int		id;
	@Column(name = "taskname", nullable = false)
	private String	taskname;
	@Column(name = "duedate")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date	dueDate;
	@Column(name = "status")
	private String	status;

	@Override
	public String toString() {
		return "Task = { id = " + id + ", taskname = " + taskname + ", duedate = " + dueDate + ", status = " + status + "}";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
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
