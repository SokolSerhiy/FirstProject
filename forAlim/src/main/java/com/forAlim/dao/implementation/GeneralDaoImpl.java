package com.forAlim.dao.implementation;



import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.forAlim.dao.GeneralDao;


/**Параметризований абстрактний клас для класів: інгредієнт, країна, урла
 * пврвметри: K - клас, S - стрінг
 * Стрінг використовується лише для першого методу, оскільки назва повинна бути унікальна*/
public abstract class GeneralDaoImpl<K, N extends Number> implements GeneralDao<K, N>{
	private Class<K> entityClass;
	
	public GeneralDaoImpl(Class<K> entityClass) {
		this.entityClass = entityClass;
	}
	
	@PersistenceContext(unitName="Primary")
	EntityManager em;
	/**Цей метод виконує пошук за полем id вищезазначених класів
	 * повертає ссилку на клас якщо існує та нуль якщо ні*/
	@SuppressWarnings("unchecked")
	@Transactional
	public K findById (N id){
		try{
			return (K) em.createQuery("select k from "+entityClass.getSimpleName()
				+" k where k.id = :id").setParameter("id", id).getSingleResult();
		}catch(NoResultException e){
			return null;
		}
	}
	/**Цей метод створює сутність в базі данних
	 * повертає ссилку на обєкт з заповненим id(потрібно перевірити)*/
	@Transactional
	public K create (K entity){
		em.persist(entity);
		return entity;
	}
	/**Цей метод видаляє сутність з БД*/
	@Transactional
	public void delete (K entity){
		em.remove(em.merge(entity));
	}
	/**Цей метод оновлює сутність в БД*/
	@Transactional
	public void update (K entity){
		em.merge(entity);
	}
	/**Цей метод створює список сутностей та повертає його*/
	@SuppressWarnings("unchecked")
	@Transactional
	public List<K> getAll(){
		return em.createQuery("from "+entityClass.getSimpleName()).getResultList();
	}
}
