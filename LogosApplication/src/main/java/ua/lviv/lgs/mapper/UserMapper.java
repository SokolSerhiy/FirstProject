package ua.lviv.lgs.mapper;

import ua.lviv.lgs.entity.User;
import ua.lviv.lgs.model.UserData;
import ua.lviv.lgs.model.UserRegistration;

public interface UserMapper {

	public static User mapUser(UserRegistration registration){
		User user = new User();
		user.setPassword(registration.getPassword());
		user.setEmail(registration.getEmail());
		return user;
	}
	
	public static UserData mapUserData(User user){
		UserData data = new UserData();
		data.setEmail(user.getEmail());
		data.setFirstName(user.getFirstName());
		data.setLastName(user.getLastName());
		data.setPhoneNumber(user.getPhoneNumber());
		data.setPhotoUrl(user.getPhotoUrl());
		return data;
	}
}
