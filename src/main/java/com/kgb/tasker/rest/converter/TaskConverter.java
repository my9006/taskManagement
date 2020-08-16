package com.kgb.tasker.rest.converter;

import com.kgb.tasker.persistence.model.Task;
import com.kgb.tasker.rest.model.TaskResponseModel;

import java.util.List;
import java.util.stream.Collectors;

public class TaskConverter {

	public static TaskResponseModel convertToTaskResponseModel(Task task) {
		return new TaskResponseModel(task.getId(), task.getTitle(), task.getUser(), task.getStatus());
	}

	public static List<TaskResponseModel> convertToTaskResponseModel(List<Task> tasks) {
		return tasks.stream().map(TaskConverter::convertToTaskResponseModel).collect(Collectors.toList());
	}
}
