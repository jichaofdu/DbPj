package entity;
import java.sql.Timestamp;

public class Refuse {
	private int id;
	private int applicationId;
	private String reason;
	private Timestamp time;
	
	public Refuse(int newId,int newApplicationId,String newReason, Timestamp newTime){
		this.id = newId;
		this.applicationId = newApplicationId;
		this.reason = newReason;
		this.time = newTime;
	}

	public  Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
