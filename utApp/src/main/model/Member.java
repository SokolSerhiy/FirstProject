package main.model;


import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Member {

	private final StringProperty mainNumber;
	private final StringProperty mobNumber;
	private final StringProperty status;
	private final StringProperty coment;
	private final StringProperty nextWork;

	public Member() {
		this(null);
	}
	
	public Member(String mainNumber, String mobNumber, String status, String coment, String nextWork){
		this.mainNumber = new SimpleStringProperty(mainNumber);
		this.mobNumber = new SimpleStringProperty(mobNumber);
		this.status = new SimpleStringProperty(status);
		this.coment = new SimpleStringProperty(coment);
		this.nextWork = new SimpleStringProperty(nextWork);
	}

	public Member(String mainNumber) {
		this.mainNumber = new SimpleStringProperty(mainNumber);
		this.mobNumber = new SimpleStringProperty();
		this.status = new SimpleStringProperty();
		this.coment = new SimpleStringProperty();
		this.nextWork = new SimpleStringProperty();
	}

	public StringProperty getMainNumber() {
		return mainNumber;
	}

	public String getMainNumberAsString() {
		return mainNumber.get();
	}

	public void setMainNumber(String mainNumber) {
		this.mainNumber.set(mainNumber);
	}

	public StringProperty getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(String mobNumber) {
		if (mobNumber != null) {
			this.mobNumber.set(mobNumber);
		}
	}

	public StringProperty getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status != null) {
			this.status.set(status);
		}
	}

	public StringProperty getComent() {
		return coment;
	}

	public void setComent(String coment) {
		if (coment != null) {
			this.coment.set(coment);
		}
	}

	public StringProperty getNextWork() {
		return nextWork;
	}

	public void setNextWork(LocalDate nextWork) {
		this.nextWork.set(SupportClass.format(nextWork));
	}
}