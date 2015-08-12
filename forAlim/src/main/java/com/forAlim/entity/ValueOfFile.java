package com.forAlim.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({
	@NamedQuery(name = ValueOfFile.FIND_BY_ID_SAVED_FILE, query = ValueOfFile.FIND_BY_ID_SAVED_FILE_QUERY)
})
public class ValueOfFile {
	public static final String FIND_BY_ID_SAVED_FILE = "ValueOfFile.FindByIdSavedFile";
	public static final String FIND_BY_ID_SAVED_FILE_QUERY = "SELECT v FROM ValueOfFile v WHERE v.savedFile.id = :savedFileId";
	
	@Id
	@GeneratedValue
	private int id;
	@Column
	@Lob
	private byte[] bais;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private SavedFile savedFile;
	
	public ValueOfFile() {	}

	public ValueOfFile(byte[] bais) {
		this.bais = bais;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getBais() {
		return bais;
	}

	public void setBais(byte[] bais) {
		this.bais = bais;
	}

	public SavedFile getSavedFile() {
		return savedFile;
	}

	public void setSavedFile(SavedFile savedFile) {
		this.savedFile = savedFile;
	}
}
