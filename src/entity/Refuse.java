package entity;

public class Refuse {
	private int id;
	private int applicationId;
	private String reason;
	
	public Refuse(int newId,int newApplicationId,String newReason){
		this.id = newId;
		this.applicationId = newApplicationId;
		this.reason = newReason;
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
