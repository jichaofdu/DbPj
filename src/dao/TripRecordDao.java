package dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.TripRecord;
import util.JdbcUtil;

public class TripRecordDao {
	private JdbcUtil util;
	private static TripRecordDao tripRecordDao;
	private final String createNewTripRecordSql
		= "insert into trip_record(task_id,actual_date,actual_time,work_description)"
				+ "values(?,?,?,?)";
	private final String getTripRecordById 
		= "select * from trip_record where id = ?";
	
	public TripRecordDao() {
		util = JdbcUtil.getInstance();
	}
	
	public TripRecordDao getInstance(){
		if(tripRecordDao == null){
			tripRecordDao = new TripRecordDao();
		}
		return tripRecordDao;
	}
	
	/**
	 * 解释：创建一个出差记录，ID由系统自动生成
	 * @param tripRecord
	 * @return
	 */
	public boolean createTripRecord(TripRecord tripRecord){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewTripRecordSql);
			ps.setInt(1, tripRecord.getTaskId());
			ps.setDate(2, tripRecord.getActualTripDate());
			ps.setInt(3, tripRecord.getActualNumberOfDays());
			ps.setString(4, tripRecord.getWorkContent());
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
	 * 解释：通过ID获取到某次出差任务的出差记录
	 * @param tripRecordId
	 * @return
	 */
	public TripRecord tripRecordGetById(int tripRecordId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.getTripRecordById);
			ps.setInt(1,tripRecordId);
			rs = ps.executeQuery();
			if(rs.next()){
				int taskId = rs.getInt("task_id");
				Date actualDate = rs.getDate("date");
				int numOfDays = rs.getInt("numOfDay");
				String workContent = rs.getString("work_content");
				TripRecord tripRecord = new TripRecord(tripRecordId,taskId,actualDate,numOfDays,workContent);
				return tripRecord;
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
