package entity;

public class RelationProjectDeveloper {
	private int projectId;
	private int developerId;
	
	public RelationProjectDeveloper(int projectId,int developerId){
		this.projectId = projectId;
		this.developerId = developerId;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getDeveloperId() {
		return developerId;
	}
	public void setDeveloperId(int developerId) {
		this.developerId = developerId;
	}
	
	
}
