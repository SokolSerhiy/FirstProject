package com.forAlim.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.forAlim.entity.SavedFile;
import com.forAlim.entity.ValueOfFile;

public interface SavedFileService {
	
	public SavedFile findById(String id);
	public List<SavedFile> getAll();
	public void create(MultipartFile file) throws IOException;
	public void delete(String id);
	public ValueOfFile findByIdSavedFile(String idSavedFile);
}
