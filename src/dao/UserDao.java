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
	private final String createNewUserSql
		= "insert into user(user_id,user_password,user_name,user_type,user_disabled) "
				+ "values(?,?,?,?,?)";
	private final String createNewUserNoIdSql
		= "insert into user(user_password,user_name,user_type,user_disabled) "
				+ "values(?,?,?,?)";
	private final String getUserInfoSql
		="select * from user where user_id = ?";
	private final String changeUserInfoSql
		="update user set user_password = ?,user_name = ?,user_type = ?,user_disabled = ? where user_id = ?";
	
	private UserDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static UserDao getInstance(){
		if(userDao == null){
			userDao = new UserDao();
		}
		return userDao;
	}
	
	/**
	 * 创建一个新用户（ID由外界传入）
	 * @param user
	 * @return
	 */
	public boolean userCreate(User user){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewUserSql);
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
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解释：创建新用户（ID由数据库自动给出）
	 * @param user 传入数据库的数据
	 * @return
	 */
	public boolean userCreateNoId(User user){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewUserNoIdSql);
			ps.setString(1, user.getPassword());
			ps.setString(2, user.getName());
			ps.setInt(3, user.getType());
			ps.setBoolean(4, user.isDisabled());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解释：通过ID获得用户的信息
	 * @param userId
	 * @return
	 */
	public User userGetById(int userId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getUserInfoSql);
			ps.setInt(1,userId);
			rs = ps.executeQuery();
			if(rs.next()){
				String userPassword = rs.getString("user_password");
				String userName = rs.getString("user_name");
				int userType = rs.getInt("user_type");
				boolean user_disabled = rs.getBoolean("user_disabled");
				User user = new User(userId,userPassword,userName,userType,user_disabled);
				return user;
			}else{
				return null;
			}
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 解释：修改用户的信息
	 * @param user
	 * @return
	 */
	public boolean userInfoChange(User user){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(changeUserInfoSql);
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
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
}
  
