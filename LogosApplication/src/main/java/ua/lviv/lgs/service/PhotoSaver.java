package ua.lviv.lgs.service;

import ua.lviv.lgs.model.ProfilePhotoRequest;

public interface PhotoSaver {

	String savePhoto(ProfilePhotoRequest request);

	void deletePreviousPhoto(String photoUrl);
}
