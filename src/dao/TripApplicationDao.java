package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import entity.TripApplication;
import util.JdbcUtil;

public class TripApplicationDao {
	private JdbcUtil util;
	private static TripApplicationDao tripApplicationDao;
	private final String CREATE_NEW_TRIP_APPLICATION_SQL = "insert into trip_application(salesman_id,project_id,"
			+ "application_trip_date,application_number_of_people,"
			+ "application_number_of_days,application_work_description,"
			+ "application_submission_time,application_status,"
			+ "application_approval_time)" + "values(?,?,?,?,?,?,?,?,?)";
	private final String GET_TRIP_APPLICATION_BY_ID_SQL = "select * from trip_application where application_id = ?";
	private final String GET_TRIP_APPLICATION_BY_PROJECT_ID_SQL = "select * from trip_application where project_id = ? order by application_submission_time desc";
	private final String GET_TRIP_APPLICATION_BY_SALESMAN_ID_SQL = "select * from trip_application where salesman_id = ? order by application_submission_time desc";
	private final String CHANGE_TRIP_APPLICATION_INFO_SQL = "update trip_application set salesman_id = ?,"
			+ "project_id = ?,application_trip_date = ?,"
			+ "application_number_of_people = ?,"
			+ "application_number_of_days = ?,"
			+ "application_work_description = ?,"
			+ "application_submission_time = ?,"
			+ "application_status = ?,"
			+ "application_approval_time = ? " + "where application_id = ?";

	private TripApplicationDao() {
		util = JdbcUtil.getInstance();
	}

	public TripApplicationDao getInstance() {
		if (tripApplicationDao == null) {
			tripApplicationDao = new TripApplicationDao();
		}
		return tripApplicationDao;
	}

	/* Create a trip application */
	public boolean createTripApplication(TripApplication newTripApplication) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_TRIP_APPLICATION_SQL);
			ps.setInt(1, newTripApplication.getSalesmanId());
			ps.setInt(2, newTripApplication.getProjectId());
			ps.setDate(3, newTripApplication.getDate());
			ps.setInt(4, newTripApplication.getNumPeople());
			ps.setInt(5, newTripApplication.getNumDays());
			ps.setString(6, newTripApplication.getDescription());
			ps.setTimestamp(7, newTripApplication.getSubmissionTime());
			ps.setInt(8, newTripApplication.getStatus());
			ps.setDate(9, newTripApplication.getApprovalTime());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Get a trip application by application_id */
	public TripApplication getTripApplicationById(int tripApplicationId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.GET_TRIP_APPLICATION_BY_ID_SQL);
			ps.setInt(1, tripApplicationId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int salesmanId = rs.getInt("salesman_id");
				int projectId = rs.getInt("project_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs
						.getString("application_work_description");
				Timestamp submissionTime = rs
						.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
				TripApplication tripApplication = new TripApplication(
						tripApplicationId, salesmanId, projectId, date,
						numOfProple, numOfDays, description, submissionTime,
						status, approvalTime);
				return tripApplication;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Modify the task information */
	public boolean tripTaskInfoModify(TripApplication newTripApplication) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CHANGE_TRIP_APPLICATION_INFO_SQL);
			ps.setInt(1, newTripApplication.getSalesmanId());
			ps.setInt(2, newTripApplication.getProjectId());
			ps.setDate(3, newTripApplication.getDate());
			ps.setInt(4, newTripApplication.getNumPeople());
			ps.setInt(5, newTripApplication.getNumDays());
			ps.setString(6, newTripApplication.getDescription());
			ps.setTimestamp(7, newTripApplication.getSubmissionTime());
			ps.setInt(8, newTripApplication.getStatus());
			ps.setDate(9, newTripApplication.getApprovalTime());
			ps.setInt(10, newTripApplication.getId());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Get trip applications by project id */
	public ArrayList<TripApplication> tripApplicationGetByProjectId(
			int projectId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripApplication> list = new ArrayList<TripApplication>();
		try {
			ps = conn
					.prepareStatement(this.GET_TRIP_APPLICATION_BY_PROJECT_ID_SQL);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int tripApplicationId = rs.getInt("application_id");
				int salesmanId = rs.getInt("salesman_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs
						.getString("application_work_description");
				Timestamp submissionTime = rs
						.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
				TripApplication tripApplication = new TripApplication(
						tripApplicationId, salesmanId, projectId, date,
						numOfProple, numOfDays, description, submissionTime,
						status, approvalTime);
				list.add(tripApplication);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* Get trip application by salesman id */
	public ArrayList<TripApplication> tripApplicationGetBySalesmanId(
			int salesmanId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripApplication> list = new ArrayList<TripApplication>();
		try {
			ps = conn
					.prepareStatement(this.GET_TRIP_APPLICATION_BY_SALESMAN_ID_SQL);
			ps.setInt(1, salesmanId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int tripApplicationId = rs.getInt("application_id");
				int projectId = rs.getInt("project_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs
						.getString("application_work_description");
				Timestamp submissionTime = rs
						.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
				TripApplication tripApplication = new TripApplication(
						tripApplicationId, salesmanId, projectId, date,
						numOfProple, numOfDays, description, submissionTime,
						status, approvalTime);
				list.add(tripApplication);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
