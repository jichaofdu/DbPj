package entity;
import java.sql.Date;
import java.sql.Timestamp;

public class TripApplication {
	
	private int id;
	private int salesmanId;
	private int projectId;
	private Date date;
	private int numPeople;
	private int numDays;
	private String description;
	private Timestamp submissionTime;
	private int status;
	private Date approvalTime;
	
	public TripApplication(int newId,int newSalesmanId,int newProjectId,Date newDate,int newNumPeople,
			int newNumDays,String newDescription,Timestamp newTime,int newStatus,Date newApprovalTime){
		this.id = newId;
		this.salesmanId = newSalesmanId;
		this.projectId = newProjectId;
		this.date = newDate;
		this.numPeople = newNumPeople;
		this.numDays = newNumDays;
		this.description = newDescription;
		this.submissionTime = newTime;
		this.status = newStatus;
		this.approvalTime = newApprovalTime;
	}

	public Timestamp getSubmissionTime() {
		return submissionTime;
	}

	public void setSubmissionTime(Timestamp submissionTime) {
		this.submissionTime = submissionTime;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(int salesmanId) {
		this.salesmanId = salesmanId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public int getNumDays() {
		return numDays;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
