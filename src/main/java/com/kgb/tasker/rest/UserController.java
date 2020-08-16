package com.kgb.tasker.rest;

import com.kgb.tasker.persistence.model.User;
import com.kgb.tasker.persistence.repository.UserRepository;
import com.kgb.tasker.rest.converter.UserConverter;
import com.kgb.tasker.rest.helper.UserHelper;
import com.kgb.tasker.rest.model.UserRequestModel;
import com.kgb.tasker.rest.model.UserResponseModel;
import org.aspectj.apache.bcel.classfile.SourceFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
	private final UserRepository userRepository;

	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping(value = "user")
	public ResponseEntity<UserResponseModel> createUser(@RequestBody UserRequestModel userRequest) {
		User user = new User(userRequest.getName(), userRequest.getSurname());
		User newUser = userRepository.save(user);
		return ResponseEntity.ok(UserConverter.convertToUserResponseModel(newUser));
	}

	@PutMapping(value = "user/{id}")
	public ResponseEntity<UserResponseModel> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserRequestModel updateUserRequest) {
		User user = userRepository.findById(id).get();
		UserHelper.updateUser(updateUserRequest, user);
		User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(UserConverter.convertToUserResponseModel(updatedUser));
	}

	@GetMapping(value = "user/{id}")
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable(value = "id") Long id) {
		User user = null;
		try{
		 user= userRepository.findById(id).get();
		}catch (Exception e){
			System.out.printf("Ախպերս, մեր մոտ ըտենց աշխատող չկա, կողքի բազայում հարցրու մի հատ");
		}
		return ResponseEntity.ok(UserConverter.convertToUserResponseModel(user));
	}

	@GetMapping(value = "user")
	public ResponseEntity<List<UserResponseModel>> getAllUsers() {
		return ResponseEntity.ok(userRepository.findAll().stream().map(UserConverter::convertToUserResponseModel).collect(Collectors.toList()));
	}

	@DeleteMapping(value = "user/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable(value = "id") Long id) {
		try{
			userRepository.deleteById(id);
		}catch (Exception e){
			System.out.printf("Սֆթուց բերեք %d-ին գործի տեղավորեք, հետո ներ ասեք որ իրեն հանեմ", id);
		}
		return ResponseEntity.ok(!userRepository.existsById(id));
	}
}
