package com.kgb.tasker.rest.model;

import com.kgb.tasker.persistence.TasksStatuses;
import com.kgb.tasker.persistence.model.User;

public class TaskResponseModel {
	private Long id;
	private String title;
	private User assignee;
	private TasksStatuses status;

	public TaskResponseModel(Long id, String title, User assignee, TasksStatuses status) {
		this.id = id;
		this.title = title;
		this.assignee = assignee;
		this.status = status;
	}

	public TaskResponseModel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getAssignee() {
		return assignee;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public TasksStatuses getStatus() {
		return status;
	}

	public void setStatus(TasksStatuses status) {
		this.status = status;
	}
}
