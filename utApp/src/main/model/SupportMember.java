package main.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SupportMember {
	@Id
	private long mainNumber;
	private long mobNumber;
	private String status;
	private String coment;
	private String nextWork;

	public SupportMember() {
	}
	
	public SupportMember(long mainNumber){
		this.mainNumber = mainNumber;
		this.mobNumber = 0;
		this.status = null;
		this.coment = null;
		this.nextWork = null;
	}
	
	public SupportMember(long mainNumber, long mobNumber, String status,
			String coment, String nextWork) {
		this.mainNumber = mainNumber;
		this.mobNumber = mobNumber;
		this.status = status;
		this.coment = coment;
		this.nextWork = nextWork;
	}

	public long getMainNumber() {
		return mainNumber;
	}

	public void setMainNumber(long mainNumber) {
		this.mainNumber = mainNumber;
	}

	public long getMobNumber() {
		return mobNumber;
	}

	public void setMobNumber(long mobNumber) {
		this.mobNumber = mobNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public String getNextWork() {
		return nextWork;
	}

	public void setNextWork(String nextWork) {
		this.nextWork = nextWork;
	}
}
