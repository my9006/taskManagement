package com.kgb.tasker.rest.model;

import com.kgb.tasker.persistence.TasksStatuses;

public class TaskRequestModel {
	private String title;
	private Long userId;
	private TasksStatuses status;

	public TaskRequestModel() {
	}

	public TaskRequestModel(String title, Long userId, TasksStatuses status) {
		this.title = title;
		this.userId = userId;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public TasksStatuses getStatus() {
		return status;
	}

	public void setStatus(TasksStatuses status) {
		this.status = status;
	}
}
