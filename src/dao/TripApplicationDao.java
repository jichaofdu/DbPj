package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import entity.TripApplication;
import util.JdbcUtil;

public class TripApplicationDao {
	private JdbcUtil util;
	private static TripApplicationDao tripApplicationDao;
	private final String createNewTripApplicationSql
		= "insert into trip_application(salesman_id,project_id,date,numOfPeople,numDays,description,submissionTime,status,approvalTime)"
				+ "values(?,?,?,?,?,?,?,?,?)";
	private final String getTripApplicationByIdSql
		= "select * from trip_application where id = ?";
	private final String changeTripApplicationInfoSql
		="update trip_task set salesman_id = ?,project_id = ?,date = ?,numOfPeople = ?,numDays = ?,description = ?,submissionTime = ?,status = ?,approvalTime = ? where id = ?";
	
	
	public TripApplicationDao(){
		util = JdbcUtil.getInstance();
	}
	
	public TripApplicationDao getInstance(){
		if(tripApplicationDao == null){
			tripApplicationDao = new TripApplicationDao();
		}
		return tripApplicationDao;
	}
	
	/**
	 * 解释：创建一个出差申请计划（ID由数据库自动生成）
	 * @param newTripTask
	 * @return
	 */
	public boolean createTripApplication(TripApplication newTripApplication){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewTripApplicationSql);
			ps.setInt(1,newTripApplication.getSalesmanId());
			ps.setInt(2,newTripApplication.getProjectId());
			ps.setDate(3,newTripApplication.getDate());
			ps.setInt(4,newTripApplication.getNumPeople());
			ps.setInt(5,newTripApplication.getNumDays());
			ps.setString(6,newTripApplication.getDescription());
			ps.setTimestamp(7,newTripApplication.getSubmissionTime());
			ps.setInt(8,newTripApplication.getStatus());
			ps.setDate(9,newTripApplication.getApprovalTime());
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
	 * 解释：通过ID获取到某个出差申请
	 * @param tripTaskId
	 * @return
	 */
	public TripApplication getTripApplicationById(int tripApplicationId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getTripApplicationByIdSql);
			ps.setInt(1,tripApplicationId);
			rs = ps.executeQuery();
			if(rs.next()){
				int salesmanId = rs.getInt("application_id");
				int projectId = rs.getInt("developer_id");
				Date date = rs.getDate("date");
				int numOfProple = rs.getInt("developer_id");
				int numOfDays = rs.getInt("developer_id");
				String description = rs.getString("description");
				Timestamp submissionTime = rs.getTimestamp("submissionTime");
				int status = rs.getInt("status");
				Date approvalTime = rs.getDate("approval_time");
				TripApplication tripApplication = new TripApplication(tripApplicationId,salesmanId,
						projectId,date,numOfProple,numOfDays,description,submissionTime,status,approvalTime);
				return tripApplication;
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
	 * 解释：修改出差申请的信息
	 * @param task
	 * @return
	 */
	public boolean userTripTaskChange(TripApplication newTripApplication){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.changeTripApplicationInfoSql);
			ps.setInt(1,newTripApplication.getSalesmanId());
			ps.setInt(2,newTripApplication.getProjectId());
			ps.setDate(3,newTripApplication.getDate());
			ps.setInt(4,newTripApplication.getNumPeople());
			ps.setInt(5,newTripApplication.getNumDays());
			ps.setString(6,newTripApplication.getDescription());
			ps.setTimestamp(7,newTripApplication.getSubmissionTime());
			ps.setInt(8,newTripApplication.getStatus());
			ps.setDate(9,newTripApplication.getApprovalTime());
			ps.setInt(10,newTripApplication.getId());
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
