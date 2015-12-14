package entity;

public class TripTask {
	int id;
	int applicationId;
	int developerId;
	int status;
	
	public TripTask(int newId,int newApplicationId,int newDeveloperId,int newStatus){
		this.id = newId;
		this.applicationId = newApplicationId;
		this.developerId = newDeveloperId;
		this.status = newStatus;
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

	public int getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(int developerId) {
		this.developerId = developerId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
