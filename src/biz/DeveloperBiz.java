package biz;
import java.util.ArrayList;
import dao.ProjectDao;
import dao.RelationProjectDevelopertDao;
import dao.TripRecordDao;
import dao.TripTaskDao;
import entity.Project;
import entity.RelationProjectDeveloper;
import entity.TripRecord;
import entity.TripTask;

public class DeveloperBiz {
	private TripRecordDao tripRecordDao;
	private TripTaskDao tripTaskDao;
	private ProjectDao projectDao;
	private RelationProjectDevelopertDao relationProjectDeveloperDao;
	private static DeveloperBiz developerBiz;
	
	public DeveloperBiz(){
		tripRecordDao = TripRecordDao.getInstance();
		tripTaskDao = TripTaskDao.getInstance();
		projectDao = ProjectDao.getInstance();
		relationProjectDeveloperDao = RelationProjectDevelopertDao.getInstance();
	}
	public static DeveloperBiz getInstance(){
		if(developerBiz == null){
			developerBiz = new DeveloperBiz();
		}
		return developerBiz;
	}
	/*开发人员确认自己的出差任务*/
	public boolean confirmTripTask(int taskId){
		TripTask tripTask = tripTaskDao.getTripTaskById(taskId);
		tripTask.setStatus(2);
		tripTaskDao.tripTaskInfoChange(tripTask);
		return true;
	}
	/*开发人员上传自己的出差记录*/
	public boolean uploadTripTaskRecord(int developerId,TripRecord tripRecord){
		tripRecordDao.createTripRecord(tripRecord);
		int taskId = tripRecord.getTaskId();
		TripTask tripTask = tripTaskDao.getTripTaskById(taskId);
		tripTask.setStatus(3);
		tripTaskDao.tripTaskInfoChange(tripTask);
		return true;
	}
	/*开发人员获取自己的出差任务*/
	public ArrayList<TripTask> getOwnTripTask(int developerId){
		ArrayList<TripTask> resultList = tripTaskDao.tripTaskGetByDeveloperId(developerId);
		return resultList;
	}
	/*开发人员获取自己负责的项目的列表*/
	public ArrayList<Project> getProjectsOfADeveloper(int developerId){
		ArrayList<RelationProjectDeveloper> relationList = relationProjectDeveloperDao.relationGetByDeveloperId(developerId);
		ArrayList<Project> resultList = new ArrayList<Project>();
		for(RelationProjectDeveloper relation:relationList){
			int projectId = relation.getProjectId();
			Project project = projectDao.projectGetById(projectId);
			resultList.add(project);
		}
		return resultList;
	}
	

	
}
