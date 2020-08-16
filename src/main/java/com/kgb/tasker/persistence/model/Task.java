package com.kgb.tasker.persistence.model;

import com.kgb.tasker.persistence.TasksStatuses;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class Task extends AbstractBaseEntity {
	private String title;
	@Enumerated(EnumType.STRING)
	private TasksStatuses status;
	@ManyToOne
	private User user;

	public Task(String title, TasksStatuses status, User user) {
		this.title = title;
		this.status = status;
		this.user = user;
	}

	public Task(String title, TasksStatuses status) {
		this.title = title;
		this.status = status;
	}

	public Task() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public TasksStatuses getStatus() {
		return status;
	}

	public void setStatus(TasksStatuses status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
