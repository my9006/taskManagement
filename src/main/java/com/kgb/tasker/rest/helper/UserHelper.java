package com.kgb.tasker.rest.helper;

import com.kgb.tasker.persistence.model.User;
import com.kgb.tasker.rest.model.UserRequestModel;

public class UserHelper {

	public static void updateUser(UserRequestModel updateUser, User user) {
		if (updateUser.getName() == null) {
			user.setName(user.getName());
		}else{
			user.setName(updateUser.getName());
		}
		if(updateUser.getSurname()==null){
			user.setSurname(user.getSurname());
		}else{
			user.setSurname(updateUser.getSurname());
		}
	}

}
