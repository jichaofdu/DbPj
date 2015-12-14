package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.Project;
import util.JdbcUtil;

public class ProjectDao {
	private JdbcUtil util;
	private static ProjectDao projectDao;
	private final String createNewProjectSql
		= "insert into project(project_id,project_manager_id,project_name,project_description) "
			+ "values(?,?,?,?)";
	private final String createNewProjectNoIdSql
		= "insert into project(project_manager_id,project_name,project_description) "
			+ "values(?,?,?)";
	private final String getProjectInfoByIdSql
	    = "select * from project where project_id = ?";
	private final String getProjectInfoByManagerIdSql
		= "select * from project where project_manager_id = ?";
	
	private ProjectDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static ProjectDao getInstance(){
		if(projectDao == null){
			projectDao = new ProjectDao();
		}
		return projectDao;
	}
	
	/**
	 * 解释：创建一个项目（ID由外界传入）
	 * @param project
	 * @return
	 */
	public boolean projectCreate(Project project){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewProjectSql);
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
	
	/**
	 * 解释：在数据库中新建一个项目（ID由数据库自动生成）
	 * @param project
	 * @return
	 */
	public boolean projectCreateNoId(Project project){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewProjectNoIdSql);
			ps.setInt(1, project.getManagerId());
			ps.setString(2, project.getName());
			ps.setString(3, project.getDescription());
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
	
	/**
	 * 解释：通过ID获得项目的信息
	 * @param userId
	 * @return
	 */
	public Project projectGetById(int projectId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getProjectInfoByIdSql);
			ps.setInt(1,projectId);
			rs = ps.executeQuery();
			if(rs.next()){
				int managerId = rs.getInt("project_manager_id");
				String projectName = rs.getString("project_name");
				String description = rs.getString("project_description");
				Project project = new Project(projectId,managerId,projectName,description);
				return project;
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
	 * 解释：根据负责经理的ID查询到项目的集合
	 * @param managerId
	 * @return
	 */
	public ArrayList<Project> projectGetByManagerId(int managerId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Project> list = new ArrayList<Project>();
		try {
			ps = conn.prepareStatement(this.getProjectInfoByManagerIdSql);
			ps.setInt(1,managerId);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("project_id");
				String name = rs.getString("project_name");
				String description = rs.getString("project_description");
				Project project = new Project(id,managerId,name,description);
				list.add(project);
			}
			return list;
		} catch (SQLException e) {
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
