package com.forAlim.dao;

import java.util.List;

import com.forAlim.domain.SaveUrl;

public interface SaveUrlDao {
	
	SaveUrl findById(int id);
	void create(SaveUrl saveUrl);
	void update(SaveUrl saveUrl);
	void delete(SaveUrl saveUrl);
	List<SaveUrl> getAll();
}
