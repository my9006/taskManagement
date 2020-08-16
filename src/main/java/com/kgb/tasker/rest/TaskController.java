package com.kgb.tasker.rest;

import com.kgb.tasker.persistence.model.Task;
import com.kgb.tasker.persistence.model.User;
import com.kgb.tasker.persistence.repository.TaskRepository;
import com.kgb.tasker.persistence.repository.UserRepository;
import com.kgb.tasker.rest.converter.TaskConverter;
import com.kgb.tasker.rest.helper.TaskHelper;
import com.kgb.tasker.rest.model.TaskRequestModel;
import com.kgb.tasker.rest.model.TaskResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.kgb.tasker.persistence.TasksStatuses.*;

@RestController
public class TaskController {
	private final TaskRepository taskRepository;
	private final UserRepository userRepository;

	public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.userRepository = userRepository;
	}

	@PostMapping(value = "task")
	public ResponseEntity<TaskResponseModel> createTask(@RequestBody TaskRequestModel taskRequest) {
		User user = null;
		try {
			user = userRepository.findById(taskRequest.getUserId()).get();
		} catch (NoSuchElementException e) {
			System.out.printf("Ընգերս %d տեղում չէ, կըլնի՞ ուրիշի վրա գցես, որ ինքը անի %s\n", taskRequest.getUserId(), taskRequest.getTitle().toLowerCase());
		}
		return ResponseEntity.ok(TaskConverter.convertToTaskResponseModel(taskRepository.save(new Task(taskRequest.getTitle(), NEW, user))));
	}

	@PutMapping(value = "task/{id}")
	public ResponseEntity<TaskResponseModel> updateTask(@PathVariable(value = "id") Long id, @RequestBody TaskRequestModel updatedTask) {
		Task task = null;
		try {task = taskRepository.findById(id).get();
		}catch (NoSuchElementException e){
			System.out.printf("Ատեց ջան, ոնց որ խառնել ես, մեր հիմնարկությունում %d համարով գործ չկա, որ մի հատ էլ բան փոխենք մի երկու կոպեկ ավել ստանանք", id);
		}
		TaskHelper.updateTask(updatedTask, task, userRepository);
		return ResponseEntity.ok(TaskConverter.convertToTaskResponseModel(taskRepository.save(task)));
	}

	@GetMapping(value = "task/{id}")
	public ResponseEntity<TaskResponseModel> getTaskById(@PathVariable(value = "id") Long id) {
		Task task = null;
		try {
			task=taskRepository.findById(id).get();
		}catch (NoSuchElementException e){
			System.out.printf("Չէ հորոխպէր, ես %d չունեմ, բայց եթե ձև ունես put-ին ասա թող էդ համարով գործ սարքի, մենք էլ քեզ տանք, արանքն էլ մի բան աշխատենք", id);
		}
		return ResponseEntity.ok(TaskConverter.convertToTaskResponseModel(task));
	}

	@GetMapping(value = "tasks")
	public ResponseEntity<List<TaskResponseModel>> getAllTasks() {
		return ResponseEntity.ok(taskRepository.findAll().stream().map(TaskConverter::convertToTaskResponseModel).collect(Collectors.toList()));
	}

	@DeleteMapping(value = "task/{id}")
	public ResponseEntity<Boolean> deleteTaskById(@PathVariable(value = "id") Long id) {
		try{taskRepository.deleteById(id);
		}catch (Exception e){
			System.out.printf("Առաջումից ես պտի %d համարով գործ ունենամ, որ ջնջեմ, ուզում եք փողս կտրեք չգիտեք ո՞նց", id);
		}
		return ResponseEntity.ok(!taskRepository.existsById(id));
	}

	@PutMapping(value = "task/bulkAssign")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<List<TaskResponseModel>> assignToTask(@RequestBody Map<String, List<Long>> userTasks) {
		//		{"UserId":[1],
		//		"TasksIds":"[1,2,3,4,5,6]"}
		List<Task> assignedTasks = null;
		try {
			User user = userRepository.findById(userTasks.get("UserId").get(0)).get();
			assignedTasks = taskRepository.findAllById(userTasks.get("TasksIds"));
			assignedTasks.forEach(task -> task.setUser(user));
		} catch (Exception e) {
			System.out.println("Ըտենց լավ չի, մի հատ էլ ֆանառիկ դրեք գլխիս գիշերն էլ պախատ անեմ, վերջ, ես չկամ");
		}
		return ResponseEntity.ok(TaskConverter.convertToTaskResponseModel(taskRepository.saveAll(assignedTasks)));
	}
}