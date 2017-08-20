package ua.lviv.lgs.service.implementation;

import static ua.lviv.lgs.mapper.UserMapper.mapUser;
import static ua.lviv.lgs.mapper.UserMapper.mapUserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.lviv.lgs.entity.Role;
import ua.lviv.lgs.entity.User;
import ua.lviv.lgs.model.ChangePasswordRequest;
import ua.lviv.lgs.model.ProfilePhotoRequest;
import ua.lviv.lgs.model.UserData;
import ua.lviv.lgs.model.UserRegistration;
import ua.lviv.lgs.repository.UserRepository;
import ua.lviv.lgs.service.PhotoSaver;
import ua.lviv.lgs.service.UserService;

@Service
public class UserSeviceImpl implements UserService{

	private final UserRepository repository;
	
	private final BCryptPasswordEncoder encoder;
	
	private final PhotoSaver photoSaver;
	
	@Autowired
	public UserSeviceImpl(UserRepository repository, BCryptPasswordEncoder encoder, PhotoSaver photoSaver) {
		this.repository = repository;
		this.encoder = encoder;
		this.photoSaver = photoSaver;
	}

	@Override
	public void save(UserRegistration registration) {
		User user = mapUser(registration);
		user.setRole(Role.ROLE_USER);
		user.setPassword(encoder.encode(user.getPassword()));
		repository.save(user);
	}

	@Override
	public boolean existsByUsername(String username) {
		return repository.existsByEmail(username);
	}

	@Override
	public UserData findByEmail(String email) {
		return mapUserData(repository.findByEmail(email));
	}

	@Override
	public String savePhoto(ProfilePhotoRequest request, String email) {
		String photoUrl = photoSaver.savePhoto(request);
		if (photoUrl!=null) {
			photoSaver.deletePreviousPhoto(repository.findPhotoUrlByEmail(email));
			repository.addPhoto(photoUrl, email);
		}
		return photoUrl;
	}

	@Override
	public boolean passwordMatch(String password, String email) {
		return encoder.matches(password, repository.findPasswordByEmail(email));
	}

	@Override
	public void changePassword(ChangePasswordRequest request, String email) {
		if(passwordMatch(request.getOldPassword(), email)){
			repository.changePassword(encoder.encode(request.getPassword()), email);
		}
	}

	@Override
	public boolean existsByUsername(String registeredEmail, String email) {
		User user = repository.findByEmail(registeredEmail);
		if (user==null) return false;
		return !user.getEmail().equals(email);
	}

	@Override
	public void changeData(UserData data, String email) {
		repository.updateUserData(email, data.getFirstName(), data.getLastName(), data.getEmail(), data.getPhoneNumber());
	}
}