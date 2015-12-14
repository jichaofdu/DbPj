package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.RelationProjectDeveloper;
import util.JdbcUtil;

public class RelationProjectDevelopertDao {
	private JdbcUtil util;
	private static RelationProjectDevelopertDao relationProjectDevelopertDao;
	private final String createNewRelationSql
		= "insert into project_developer(project_id,developer_id)"
				+ "values(?,?)";
	private final String relationGetByProjectIdSql
		= "select * from project_developer where project_id = ?";
	private final String relationGetByDeveloperIdSql
		= "select * from project_developer where developer_id = ?";
	
	public RelationProjectDevelopertDao(){
		util = JdbcUtil.getInstance();
	}
	
	public static RelationProjectDevelopertDao getInstance(){
		if(relationProjectDevelopertDao == null){
			relationProjectDevelopertDao = new RelationProjectDevelopertDao();
		}
		return relationProjectDevelopertDao;
	}
	
	/**
	 * 解释：创建一个rp关系集元组
	 * @param rpd
	 * @return
	 */
	public boolean relationCreate(RelationProjectDeveloper rpd){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewRelationSql);
			ps.setInt(1,rpd.getProjectId());
			ps.setInt(2,rpd.getDeveloperId());
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
	 * 解释：根据项目ID查找对应的关系集合
	 * @param projectId
	 * @return
	 */
	public ArrayList<RelationProjectDeveloper> relationGetByProjectId(int projectId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RelationProjectDeveloper> list = new ArrayList<RelationProjectDeveloper>();
		try {
			ps = conn.prepareStatement(this.relationGetByProjectIdSql);
			ps.setInt(1,projectId);
			rs = ps.executeQuery();
			while(rs.next()){
				int developerId = rs.getInt("developerId");
				RelationProjectDeveloper rpd = new RelationProjectDeveloper(projectId,developerId);
				list.add(rpd);
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
	
	/**
	 * 解释：根据开发人员ID查找对应的关系集合
	 * @param projectId
	 * @return
	 */
	public ArrayList<RelationProjectDeveloper> relationGetByDeveloperId(int developerId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RelationProjectDeveloper> list = new ArrayList<RelationProjectDeveloper>();
		try {
			ps = conn.prepareStatement(this.relationGetByDeveloperIdSql);
			ps.setInt(1,developerId);
			rs = ps.executeQuery();
			while(rs.next()){
				int projectId = rs.getInt("projectId");
				RelationProjectDeveloper rpd = new RelationProjectDeveloper(projectId,developerId);
				list.add(rpd);
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
