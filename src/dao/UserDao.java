package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.User;
import util.JdbcUtil;

public class UserDao {

	private JdbcUtil util;
	private static UserDao userDao;
	private final String CREATE_NEW_USER_SQL = "insert into user(user_id,user_password,user_name,user_type,user_disabled) "
			+ "values(?,?,?,?,?)";
	private final String CREATE_NEW_USER_NO_ID_SQL = "insert into user(user_password,user_name,user_type,user_disabled) "
			+ "values(?,?,?,?)";
	private final String GET_USER_INFO_SQL = "select * from user where user_id = ? and user_disabled = false";
	private final String CHANGE_USER_INFO_SQL = "update user set user_password = ?,user_name = ?,user_type = ?,user_disabled = ? where user_id = ?";

	private UserDao() {
		util = JdbcUtil.getInstance();
	}

	public static UserDao getInstance() {
		if (userDao == null) {
			userDao = new UserDao();
		}
		return userDao;
	}

	/* Create a new user （user_id is given)*/
	public boolean userCreate(User user) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_USER_SQL);
			ps.setInt(1, user.getId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getType());
			ps.setBoolean(5, user.isDisabled());
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

	/* Create a new user(user_id is generate by database automatic */
	public boolean userCreateNoId(User user) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_USER_NO_ID_SQL);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getType());
			ps.setBoolean(4, user.isDisabled());
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

	/* Get user information by user_id */
	public User userGetById(int userId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.GET_USER_INFO_SQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			if (rs.next()) {
				String userPassword = rs.getString("user_password");
				String userName = rs.getString("user_name");
				int userType = rs.getInt("user_type");
				boolean user_disabled = rs.getBoolean("user_disabled");
				User user = new User(userId, userPassword, userName, userType,
						user_disabled);
				return user;
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

	/* Modify the user information */
	public boolean userInfoModify(User user) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(CHANGE_USER_INFO_SQL);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getType());
			ps.setBoolean(4, user.isDisabled());
			ps.setInt(5, user.getId());
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
}
