package com.kgb.tasker.rest.helper;

import com.kgb.tasker.persistence.model.Task;
import com.kgb.tasker.persistence.repository.UserRepository;
import com.kgb.tasker.rest.model.TaskRequestModel;

public class TaskHelper {

	public static void updateTask(TaskRequestModel request, Task task, UserRepository userRepository) {
		if (request.getStatus() == null) {
			task.setStatus(task.getStatus());
		} else {
			task.setStatus(request.getStatus());
		}
		if (request.getUserId() == null) {
			task.setUser(task.getUser());
		} else {
			try {
				task.setUser(userRepository.findById(request.getUserId()).get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


}
