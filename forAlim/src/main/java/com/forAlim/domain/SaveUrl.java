package com.forAlim.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SaveUrl {
	@GeneratedValue
	@Id
	private int id;
	@Column
	private String savedUrlPath;
	
	public SaveUrl() {}
	
	public SaveUrl(String savedUrlPath) {
		this.savedUrlPath = savedUrlPath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSavedUrlPath() {
		return savedUrlPath;
	}

	public void setSavedUrlPath(String savedUrlPath) {
		this.savedUrlPath = savedUrlPath;
	}
}
