package com.forAlim.dao;

import java.util.List;

public interface GeneralDao <K, N extends Number>{
	
	K findById(N id);
	K create (K entity);
	void delete (K entity);
	void update (K entity);
	List <K> getAll();
}
