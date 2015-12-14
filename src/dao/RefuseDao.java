package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import entity.Refuse;
import util.JdbcUtil;

public class RefuseDao {
	private JdbcUtil util;
	private static RefuseDao refuseDao;
	private final String CREATE_NEW_REFUSE_SQL = "insert into refuse(application_id,refuse_reason,refuse_time)"
			+ "values(?,?,?)";
	private final String REFUSE_GET_BY_ID_SQL = "select * from refuse where refuse_id = ?";
	private final String REFUSE_GET_BY_APPLICATION_ID_SQL = "select * from refuse where application_id = ? order by refuse_time desc";

	private RefuseDao() {
		util = JdbcUtil.getInstance();
	}

	public RefuseDao getInstance() {
		if (refuseDao == null) {
			refuseDao = new RefuseDao();
		}
		return refuseDao;
	}

	/* Create a refuse */
	public boolean createRefuse(Refuse refuse) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_REFUSE_SQL);
			ps.setInt(1, refuse.getApplicationId());
			ps.setString(2, refuse.getReason());
			ps.setTimestamp(3, refuse.getTime());
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

	/* Get a refuse by refuse_id */
	public Refuse refuseGetById(int refuseId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.REFUSE_GET_BY_ID_SQL);
			ps.setInt(1, refuseId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String reason = rs.getString("refuse_reason");
				int applicationId = rs.getInt("application_id");
				Timestamp refuseTime = rs.getTimestamp("refuse_time");
				Refuse refuse = new Refuse(refuseId, applicationId, reason,
						refuseTime);
				return refuse;
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

	/* Ger refuses by application_id */
	public ArrayList<Refuse> refuseGetByApplicationId(int applicationId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Refuse> list = new ArrayList<Refuse>();
		try {
			ps = conn.prepareStatement(this.REFUSE_GET_BY_APPLICATION_ID_SQL);
			ps.setInt(1, applicationId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("refuse_id");
				String reason = rs.getString("refuse_reason");
				Timestamp time = rs.getTimestamp("refuse_time");
				Refuse refuse = new Refuse(id, applicationId, reason, time);
				list.add(refuse);
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
