package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.SystemManager;
import util.JdbcUtil;

public class SystemManagerDao {
	private JdbcUtil util;
	private static SystemManagerDao systemManagerDao;
	private final String createNewUserSql
		= "insert into system_manager(system_manager_password,system_manager_username)"
			+ "values(?,?)";
	private final String getSystemManagerInfoSql
		= "select * from system_manager where system_manager_id = ?";
	
	private SystemManagerDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static SystemManagerDao getInstance(){
		if(systemManagerDao == null){
			systemManagerDao = new SystemManagerDao();
		}
		return systemManagerDao;
	}
	
	/**
	 * 解释：创建系统管理员（ID由系统自动生成）
	 * @param newSystemManager
	 * @return
	 */
	public boolean createSystemManager(SystemManager newSystemManager){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewUserSql);;
			ps.setString(1, newSystemManager.getPassword());
			ps.setString(2, newSystemManager.getName());
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
	 * 解释：通过ID获得管理员的信息
	 * @param userId
	 * @return
	 */
	public SystemManager systemManagerGetById(int systemManagerId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getSystemManagerInfoSql);
			ps.setInt(1,systemManagerId);
			rs = ps.executeQuery();
			if(rs.next()){
				String systemManagerPassword = rs.getString("system_manager_password");
				String systemManagerName = rs.getString("system_manager_username");
				int id = rs.getInt("system_manager_id");
				SystemManager systemManager = new SystemManager(id,systemManagerPassword,systemManagerName);
				return systemManager;
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
}
