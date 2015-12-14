package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entity.TripTask;
import util.JdbcUtil;

public class TripTaskDao{
	private JdbcUtil util;
	private static TripTaskDao tripTaskDao;
	private final String createNewTripTaskSql
		= "insert into trip_task(application_id,developer_id,task_status)"
			+ "values(?,?,?)";
	private final String getTripTaskByIdSql
		= "select * from trip_task where task_id = ?";
	private final String changeTripTaskInfoSql
		="update trip_task set application_id = ?,"
				+ "developer_id = ?,task_status = ? "
				+ "where task_id = ?";
	private final String getTripTaskByApplicationIdSql
		= "select * from trip_task where application_id = ?";
	private final String getTripTaskByDeveloperIdSql
		= "select * from trip_task where developer_id = ?";
	
	public TripTaskDao() {
		util = JdbcUtil.getInstance();
	}
	
	public static TripTaskDao getInstance(){
		if(tripTaskDao == null){
			tripTaskDao = new TripTaskDao();
		}
		return tripTaskDao;
	}
	
	/**
	 * 解释：创建一个出差计划（ID由数据库自动生成）
	 * @param newTripTask
	 * @return
	 */
	public boolean createTripTask(TripTask newTripTask){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewTripTaskSql);
			ps.setInt(1,newTripTask.getApplicationId() );
			ps.setInt(2,newTripTask.getDeveloperId());
			ps.setInt(3,newTripTask.getStatus());
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
	 * 解释：通过ID获取到某个出差任务
	 * @param tripTaskId
	 * @return
	 */
	public TripTask getTripTaskById(int tripTaskId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getTripTaskByIdSql);
			ps.setInt(1,tripTaskId);
			rs = ps.executeQuery();
			if(rs.next()){
				int applicationId = rs.getInt("application_id");
				int developerId = rs.getInt("developer_id");
				int status = rs.getInt("task_status");
				TripTask tripTask = new TripTask(tripTaskId,applicationId,developerId,status);
				return tripTask;
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
	 * 解释：修改出差任务的信息
	 * @param task
	 * @return
	 */
	public boolean tripTaskInfoChange(TripTask tripTask){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.changeTripTaskInfoSql);
			ps.setInt(1,tripTask.getApplicationId());
			ps.setInt(2,tripTask.getDeveloperId());
			ps.setInt(3,tripTask.getStatus());
			ps.setInt(4,tripTask.getId());
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
	 * 解释：根据申请查询对应的出差任务
	 * @param applicationId
	 * @return
	 */
	public ArrayList<TripTask> tripTaskGetByApplicationId(int applicationId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripTask> list = new ArrayList<TripTask>();
		try {
			ps = conn.prepareStatement(this.getTripTaskByApplicationIdSql);
			ps.setInt(1,applicationId);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("task_id");
				int developerId = rs.getInt("developer_id");
				int status = rs.getInt("task_status");
				TripTask refuse = new TripTask(id,applicationId,developerId,status);
				list.add(refuse);
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
	 * 解释：根据开发人员查询对应的出差任务
	 * @param developerId
	 * @return
	 */
	public ArrayList<TripTask> tripTaskGetByDeveloperId(int developerId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripTask> list = new ArrayList<TripTask>();
		try {
			ps = conn.prepareStatement(this.getTripTaskByDeveloperIdSql);
			ps.setInt(1,developerId);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("task_id");
				int applicationId = rs.getInt("application_id");
				int status = rs.getInt("task_status");
				TripTask refuse = new TripTask(id,applicationId,developerId,status);
				list.add(refuse);
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
