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
	private final String CREATE_NEW_PROJECT_SQL = "insert into project(project_id,project_manager_id,project_name,project_description) "
			+ "values(?,?,?,?)";
	private final String CREATE_NEW_PROJECT_NO_ID_SQL = "insert into project(project_manager_id,project_name,project_description) "
			+ "values(?,?,?)";
	private final String GET_PROJECT_INFO_BY_ID_SQL = "select * from project where project_id = ?";
	private final String GET_PROJECT_INFO_BY_MANAGER_ID_SQL = "select * from project where project_manager_id = ?";

	private ProjectDao() {
		util = JdbcUtil.getInstance();
	}

	public static ProjectDao getInstance() {
		if (projectDao == null) {
			projectDao = new ProjectDao();
		}
		return projectDao;
	}

	/* Create a new project(project_id is given) */
	public boolean projectCreate(Project project) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_PROJECT_SQL);
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

	/* Create a project (project_id is generate by database automatic) */
	public boolean projectCreateNoId(Project project) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.CREATE_NEW_PROJECT_NO_ID_SQL);
			ps.setInt(1, project.getManagerId());
			ps.setString(2, project.getName());
			ps.setString(3, project.getDescription());
			System.out.println(ps.toString());
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

	/* Get the project by project_id */
	public Project projectGetById(int projectId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(this.GET_PROJECT_INFO_BY_ID_SQL);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			if (rs.next()) {
				int managerId = rs.getInt("project_manager_id");
				String projectName = rs.getString("project_name");
				String description = rs.getString("project_description");
				Project project = new Project(projectId, managerId,
						projectName, description);
				return project;
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

	/* Get projects by manager_id */
	public ArrayList<Project> projectGetByManagerId(int managerId) {
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Project> list = new ArrayList<Project>();
		try {
			ps = conn.prepareStatement(this.GET_PROJECT_INFO_BY_MANAGER_ID_SQL);
			ps.setInt(1, managerId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("project_id");
				String name = rs.getString("project_name");
				String description = rs.getString("project_description");
				Project project = new Project(id, managerId, name, description);
				list.add(project);
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
