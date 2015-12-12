package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.SystemManager;
import util.JdbcUtil;

public class SystemManagerDao {
	private JdbcUtil util;
	private static SystemManagerDao systemManagerDao;
	private final String createNewUserSql
	= "insert into system_manager(system_manager_id,system_manager_password,system_manager_name)"
			+ "values(?,?,?)";
	
	private SystemManagerDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static SystemManagerDao getInstance(){
		if(systemManagerDao == null){
			systemManagerDao = new SystemManagerDao();
		}
		return systemManagerDao;
	}
	
	public boolean createSystemManager(SystemManager newSystemManager){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewUserSql);
			ps.setString(1, newSystemManager.getId());
			ps.setString(2, newSystemManager.getPassword());
			ps.setString(3, newSystemManager.getName());
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
