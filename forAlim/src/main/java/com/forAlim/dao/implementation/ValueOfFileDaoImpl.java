package com.forAlim.dao.implementation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forAlim.dao.ValueOfFileDao;
import com.forAlim.entity.ValueOfFile;

@Repository
public class ValueOfFileDaoImpl extends GeneralDaoImpl<ValueOfFile, Integer>
		implements ValueOfFileDao {

	@PersistenceContext(unitName = "Primary")
	private EntityManager em;

	public ValueOfFileDaoImpl() {
		super(ValueOfFile.class);
	}

	@Transactional
	public ValueOfFile findByIdSavedFile(int savedFileId) {
		try {
			return (ValueOfFile) em
					.createNamedQuery(ValueOfFile.FIND_BY_ID_SAVED_FILE)
					.setParameter("savedFileId", savedFileId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
