package com.forAlim.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forAlim.dao.SaveUrlDao;
import com.forAlim.domain.SaveUrl;
import com.forAlim.service.SaveUrlService;

@Service("saveUrlService")
public class SaveUrlServiceImpl implements SaveUrlService{

	@Autowired
	private SaveUrlDao saveUrlDao;
	
	public void create(String savedUrlPath) {
		saveUrlDao.create(new SaveUrl(savedUrlPath));
		
	}

	public List<SaveUrl> getAll() {
		return saveUrlDao.getAll();
	}

	public SaveUrl findById(String id) {
		return saveUrlDao.findById(Integer.parseInt(id));
	}

}
