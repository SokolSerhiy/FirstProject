package main.model;

import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Access(AccessType.PROPERTY)
public class Member{
	
	private final StringProperty mainNumber = new SimpleStringProperty();
	
	private final StringProperty mobNumber = new SimpleStringProperty();
	
	private final StringProperty status = new SimpleStringProperty();
	
	private final StringProperty coment = new SimpleStringProperty();
	
	private final StringProperty nextWork = new SimpleStringProperty();

	public Member() {
	}
	
	public Member(String mainNumber, String mobNumber, String status,
			String coment, String nextWork) {
		this.mainNumber.set(mainNumber);
		this.mobNumber.set(mobNumber);
		this.status.set(status);
		this.coment.set(coment);
		this.nextWork.set(nextWork);
	}

	public Member(String mainNumber) {
		this.mainNumber.set(mainNumber);
		SupportClass.saveMember(this);
	}

	public void setAdditionalData(String mobNumber, String status,
			String coment, LocalDate nextWork) {
		if (mobNumber != null) {
			this.mobNumber.set(mobNumber);
		}
		if (status != null) {
			this.status.set(status);
		}
		if (coment != null){
			this.coment.set(coment);
		}
		this.nextWork.set(SupportClass.format(nextWork));
		SupportClass.updateMember(this);
	}
	
	public StringProperty asPropertyMainNumber() {
		return mainNumber;
	}
	@Id
	@Column(name = "Id")
	public String getMainNumber() {
		return mainNumber.get();
	}
	
	public void setMainNumber(String mainNumber) {
		this.mainNumber.set(mainNumber);
	}
	public StringProperty asPropertyMobNumber() {
		return mobNumber;
	}
	@Column(name = "MobNumber")
	public String getMobNumber() {
		return mobNumber.get();
	}
	
	
	public void setMobNumber(String mobNumber) {
		if (mobNumber != null) {
			this.mobNumber.set(mobNumber);
		}
	}
	@Column(name = "Status")
	public String getStatus() {
		return status.get();
	}
	
	public StringProperty asPropertyStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		if (status != null) {
			this.status.set(status);
		}
	}
	public StringProperty asPropertyComent() {
		return coment;
	}
	@Column(name = "Coment")
	public String getComent() {
		return coment.get();
	}
	
	
	public void setComent(String coment) {
		if (coment != null) {
			this.coment.set(coment);
		}
	}
	@Column(name = "NextWork")
	public String getNextWork() {
		return nextWork.get();
	}
	
	public StringProperty asPropertyNextWork() {
		return nextWork;
	}
	
	public void setNextWork(String nextWork) {
		this.nextWork.set(nextWork);
	}
	public void asPropertyNextWorkSet(LocalDate nextWork) {
		this.nextWork.set(SupportClass.format(nextWork));
	}
}