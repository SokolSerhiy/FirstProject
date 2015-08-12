package com.forAlim.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SavedFile {
	@Id
	@GeneratedValue
	private int id;
	@Column
	private String name;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "savedFile")
	private ValueOfFile valueOfFile;
	
	public SavedFile() {	}

	public SavedFile(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ValueOfFile getValueOfFile() {
		return valueOfFile;
	}

	public void setValueOfFile(ValueOfFile valueOfFile) {
		this.valueOfFile = valueOfFile;
	}
	
	
}
