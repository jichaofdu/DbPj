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

	public DeveloperBiz() {
		tripRecordDao = TripRecordDao.getInstance();
		tripTaskDao = TripTaskDao.getInstance();
		projectDao = ProjectDao.getInstance();
		relationProjectDeveloperDao = RelationProjectDevelopertDao
				.getInstance();
	}

	public static DeveloperBiz getInstance() {
		if (developerBiz == null) {
			developerBiz = new DeveloperBiz();
		}
		return developerBiz;
	}

	/* Developer confirm his/her trip task */
	public boolean confirmTripTask(int taskId) {
		TripTask tripTask = tripTaskDao.getTripTaskById(taskId);
		tripTask.setStatus(2);
		tripTaskDao.tripTaskInfoModify(tripTask);
		return true;
	}

	/* Developer update his/her task record */
	public boolean uploadTripTaskRecord(int developerId, TripRecord tripRecord) {
		tripRecordDao.createTripRecord(tripRecord);
		int taskId = tripRecord.getTaskId();
		TripTask tripTask = tripTaskDao.getTripTaskById(taskId);
		tripTask.setStatus(3);
		tripTaskDao.tripTaskInfoModify(tripTask);
		return true;
	}

	/* Developer get his/her trip task */
	public ArrayList<TripTask> getOwnTripTask(int developerId) {
		ArrayList<TripTask> resultList = tripTaskDao
				.tripTaskGetByDeveloperId(developerId);
		return resultList;
	}

	/* Developer get his/her projects */
	public ArrayList<Project> getProjectsOfADeveloper(int developerId) {
		ArrayList<RelationProjectDeveloper> relationList = relationProjectDeveloperDao
				.relationGetByDeveloperId(developerId);
		ArrayList<Project> resultList = new ArrayList<Project>();
		for (RelationProjectDeveloper relation : relationList) {
			int projectId = relation.getProjectId();
			Project project = projectDao.projectGetById(projectId);
			resultList.add(project);
		}
		return resultList;
	}

}
