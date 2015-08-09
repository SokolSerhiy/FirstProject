package com.forAlim.service;

import java.util.List;

import com.forAlim.domain.SaveUrl;

public interface SaveUrlService {
	SaveUrl findById(String id);
	void create(String savedUrlPath);
	List<SaveUrl> getAll();
	
}
