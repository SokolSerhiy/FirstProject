package com.forAlim.service.implementation;

import java.io.IOException;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.forAlim.dao.SavedFileDao;
import com.forAlim.dao.ValueOfFileDao;
import com.forAlim.entity.SavedFile;
import com.forAlim.entity.ValueOfFile;
import com.forAlim.service.SavedFileService;

@Service("savedFileService")
public class SavedFileServiceImpl implements SavedFileService {

	@Autowired
	private SavedFileDao savedFileDao;
	@Autowired
	private ValueOfFileDao valueOfFileDao;

	public SavedFile findById(String id) {
		try {
			return savedFileDao.findById(Integer.parseInt(id));
		} catch (NoResultException e) {
			return null;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public List<SavedFile> getAll() {
		return savedFileDao.getAll();
	}

	public void create(MultipartFile file) throws IOException {
		// отримуємо байтовий масив з файлу
		byte[] bytes = file.getBytes();
		// створюємо ентіті клас який зберігає фактично сам файл
		ValueOfFile valueOfFile = new ValueOfFile(bytes);
		// створюємо ентіті клас який зберігає імя файлу та
		// має звязок з класом вище
		SavedFile savedFile = new SavedFile(file.getOriginalFilename());
		savedFileDao.create(savedFile);
		valueOfFile.setSavedFile(savedFile);
		valueOfFileDao.create(valueOfFile);
	}

	public void delete(String id) {
		try {
			SavedFile savedFile = savedFileDao.findById(Integer.parseInt(id));
			ValueOfFile valueOfFile = valueOfFileDao.findByIdSavedFile(Integer
					.parseInt(id));
			if (valueOfFile != null) {
				valueOfFileDao.delete(valueOfFile);
			}
			if (savedFile != null) {
				savedFileDao.delete(savedFile);
			}
		} catch (NumberFormatException e) {
		}
	}

	public ValueOfFile findByIdSavedFile(String idSavedFile) {
		try {
			return valueOfFileDao.findByIdSavedFile(Integer
					.parseInt(idSavedFile));
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
