package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import entity.TripApplication;
import util.JdbcUtil;

public class TripApplicationDao {
	private JdbcUtil util;
	private static TripApplicationDao tripApplicationDao;
	private final String createNewTripApplicationSql
		= "insert into trip_application(salesman_id,project_id,"
				+ "application_trip_date,application_number_of_people,"
				+ "application_number_of_days,application_work_description,"
				+ "application_submission_time,application_status,"
				+ "application_approval_time)"
				+ "values(?,?,?,?,?,?,?,?,?)";
	private final String getTripApplicationByIdSql
		= "select * from trip_application where application_id = ?";
	private final String getTripApplicationByProjectIdSql
		= "select * from trip_application where project_id = ? order by application_submission_time desc";
	private final String getTripApplicationBySalesmanIdSql
		= "select * from trip_application where salesman_id = ? order by application_submission_time desc";	
	private final String changeTripApplicationInfoSql
		="update trip_application set salesman_id = ?,"
				+ "project_id = ?,application_trip_date = ?,"
				+ "application_number_of_people = ?,"
				+ "application_number_of_days = ?,"
				+ "application_work_description = ?,"
				+ "application_submission_time = ?,"
				+ "application_status = ?,"
				+ "application_approval_time = ? "
				+ "where application_id = ?";
	
	
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
				int salesmanId = rs.getInt("salesman_id");
				int projectId = rs.getInt("project_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs.getString("application_work_description");
				Timestamp submissionTime = rs.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
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
	public boolean tripTaskInfoChange(TripApplication newTripApplication){
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
	
	/**
	 * 解释：根据项目ID查询到其对应的出差申请组
	 * @param projectId
	 * @return
	 */
	public ArrayList<TripApplication> tripApplicationGetByProjectId(int projectId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripApplication> list = new ArrayList<TripApplication>();
		try {
			ps = conn.prepareStatement(this.getTripApplicationByProjectIdSql);
			ps.setInt(1,projectId);
			rs = ps.executeQuery();
			while(rs.next()){
				int tripApplicationId = rs.getInt("application_id");
				int salesmanId = rs.getInt("salesman_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs.getString("application_work_description");
				Timestamp submissionTime = rs.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
				TripApplication tripApplication = new TripApplication(tripApplicationId,salesmanId,
						projectId,date,numOfProple,numOfDays,description,submissionTime,status,approvalTime);
				list.add(tripApplication);
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
	 * 解释：根据销售人员的ID查询对应的出差申请的集合
	 * @param salesmanId
	 * @return
	 */
	public ArrayList<TripApplication> tripApplicationGetBySalesmanId(int salesmanId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<TripApplication> list = new ArrayList<TripApplication>();
		try {
			ps = conn.prepareStatement(this.getTripApplicationBySalesmanIdSql);
			ps.setInt(1,salesmanId);
			rs = ps.executeQuery();
			while(rs.next()){
				int tripApplicationId = rs.getInt("application_id");
				int projectId = rs.getInt("project_id");
				Date date = rs.getDate("application_trip_date date");
				int numOfProple = rs.getInt("application_number_of_people");
				int numOfDays = rs.getInt("application_number_of_days");
				String description = rs.getString("application_work_description");
				Timestamp submissionTime = rs.getTimestamp("application_submission_time");
				int status = rs.getInt("application_status");
				Date approvalTime = rs.getDate("application_approval_time");
				TripApplication tripApplication = new TripApplication(tripApplicationId,salesmanId,
						projectId,date,numOfProple,numOfDays,description,submissionTime,status,approvalTime);
				list.add(tripApplication);
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
