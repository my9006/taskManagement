package com.kgb.tasker.rest.converter;

import com.kgb.tasker.persistence.model.User;
import com.kgb.tasker.rest.model.UserResponseModel;

public class UserConverter {

	public static UserResponseModel convertToUserResponseModel(User user) {
		return new UserResponseModel(user.getId(), user.getName(), user.getSurname());
	}
}