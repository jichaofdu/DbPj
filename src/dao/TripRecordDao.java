package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.TripRecord;
import util.JdbcUtil;

public class TripRecordDao {
	private JdbcUtil util;
	private static TripRecordDao tripRecordDao;
	private final String CREATE_NEW_TRIP_RECORD_SQL = "insert into trip_record(task_id,"
			+ "trip_record_actual_trip_date,"
			+ "trip_record_actual_number_of_days,"
			+ "trip_record_work_description)" + "values(?,?,?,?)";
	private final String GET_TRIP_RECORD_BY_ID_SQL = "select * from trip_record where trip_record_id = ?";
	private final String GET_TRIP_RECORD_BY_TASK_ID_SQL = "select * from trip_record where task_id = ?";

	private TripRecordDao() {
		util = JdbcUtil.getInstance();
	}

	public static TripRecordDao getInstance() {
		if (tripRecordDao == null) {
			tripRecordDao = new TripRecordDao();
		}
		return tripRecordDao;
	}

	/* Create a trip record */
	public boolean createTripRecord(TripRecord tripRecord) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_TRIP_RECORD_SQL);
			ps.setInt(1, tripRecord.getTaskId());
			ps.setDate(2, tripRecord.getActualTripDate());
			ps.setInt(3, tripRecord.getActualNumberOfDays());
			ps.setString(4, tripRecord.getWorkContent());
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

	/* Get a trip record by trip_record_id  */
	public TripRecord tripRecordGetById(int tripRecordId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.GET_TRIP_RECORD_BY_ID_SQL);
			ps.setInt(1, tripRecordId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int taskId = rs.getInt("task_id");
				Date actualDate = rs.getDate("trip_record_actual_trip_date");
				int numOfDays = rs.getInt("trip_record_actual_number_of_days");
				String workContent = rs
						.getString("trip_record_work_description");
				TripRecord tripRecord = new TripRecord(tripRecordId, taskId,
						actualDate, numOfDays, workContent);
				return tripRecord;
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

	/* Get a trip record by task_id */
	public TripRecord getTripRecordByTaskId(int taskId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.GET_TRIP_RECORD_BY_TASK_ID_SQL);
			ps.setInt(1, taskId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("trip_record_id");
				Date actualDate = rs.getDate("trip_record_actual_trip_date");
				int numOfDays = rs.getInt("trip_record_actual_number_of_days");
				String workContent = rs
						.getString("trip_record_work_description");
				TripRecord tripRecord = new TripRecord(id, taskId, actualDate,
						numOfDays, workContent);
				return tripRecord;
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

}
