package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.TripTask;
import util.JdbcUtil;

public class TripTaskDao{
	private JdbcUtil util;
	private static TripTaskDao tripTaskDao;
	private final String createNewTripTaskSql
		= "insert into trip_task(application_id,developer_id,status)"
			+ "values(?,?,?)";
	private final String getTripTaskByIdSql
		= "select * from trip_task where id = ?";
	private final String changeTripTaskInfoSql
		="update trip_task set application_id = ?,developer_id = ?,status = ? where id = ?";
	
	public TripTaskDao() {
		util = JdbcUtil.getInstance();
	}
	
	public TripTaskDao getInstance(){
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
				int status = rs.getInt("status");
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
	public boolean userTripTaskChange(TripTask tripTask){
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
}
