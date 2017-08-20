package ua.lviv.lgs.service;

import ua.lviv.lgs.model.ChangePasswordRequest;
import ua.lviv.lgs.model.ProfilePhotoRequest;
import ua.lviv.lgs.model.UserData;
import ua.lviv.lgs.model.UserRegistration;

public interface UserService {

	void save(UserRegistration registration);
	
	boolean existsByUsername(String username);

	UserData findByEmail(String email);
	
	String savePhoto(ProfilePhotoRequest request, String email);

	boolean passwordMatch(String password, String email);

	void changePassword(ChangePasswordRequest request, String email);

	boolean existsByUsername(String registeredEmail, String email);

	void changeData(UserData data, String email);
}
