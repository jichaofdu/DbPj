package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import entity.Project;
import util.JdbcUtil;

public class ProjectDao {
	private JdbcUtil util;
	private static ProjectDao projectDao;
	private final String createNewUserSql
	= "insert into project(project_id,project_manager_id,project_name,project_description) "
			+ "values(?,?,?,?)";
	
	private ProjectDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static ProjectDao getInstance(){
		if(projectDao == null){
			projectDao = new ProjectDao();
		}
		return projectDao;
	}
	
	public boolean projectCreate(Project project){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewUserSql);
			ps.setInt(1, project.getId());
			ps.setInt(2, project.getManagerId());
			ps.setString(3, project.getName());
			ps.setString(4, project.getDescription());
			System.out.println(ps.toString());
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
