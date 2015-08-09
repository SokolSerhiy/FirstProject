package com.forAlim.dao.implementation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.forAlim.dao.SaveUrlDao;
import com.forAlim.domain.SaveUrl;

@Repository
public class SaveUrlDaoImpl implements SaveUrlDao{

	@PersistenceContext(unitName="Primary")
	EntityManager em;
	
	
	public SaveUrl findById(int id) {
		try{
		return (SaveUrl)em.createQuery("SELECT s FROM SaveUrl s WHERE s.id = :id").setParameter("id", id).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	
	@Transactional
	public void create(SaveUrl saveUrl) {
		em.persist(saveUrl);
		
	}
	
	@Transactional
	public void update(SaveUrl saveUrl) {
		em.merge(saveUrl);
		
	}

	@Transactional
	public void delete(SaveUrl saveUrl) {
		em.remove(saveUrl);
		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<SaveUrl> getAll() {
		return em.createQuery("FROM SaveUrl").getResultList();
	}

}
