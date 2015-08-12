package com.forAlim.dao.implementation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.forAlim.dao.SavedFileDao;
import com.forAlim.entity.SavedFile;

@Repository
public class SavedFileDaoImpl extends GeneralDaoImpl<SavedFile, Integer> implements SavedFileDao{

	@PersistenceContext(unitName = "Primary")
	private EntityManager em;
	
	public SavedFileDaoImpl() {
		super(SavedFile.class);
	}
	
}
