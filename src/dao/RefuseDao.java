package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import entity.Refuse;
import util.JdbcUtil;

public class RefuseDao {
	private JdbcUtil util;
	private static RefuseDao refuseDao;
	private final String createNewRefuseSql
		= "insert into refuse(application_id,refuse_reason,refuse_time)"
			+ "values(?,?,?)";
	private final String refuseGetByIdSql
		= "select * from refuse where refuse_id = ?";
	private final String refuseGetByApplicationIdSql
		= "select * from refuse where application_id = ? order by refuse_time desc";
	
	public RefuseDao(){
		util = JdbcUtil.getInstance();
	}
	
	public RefuseDao getInstance(){
		if(refuseDao == null){
			refuseDao = new RefuseDao();
		}
		return refuseDao;
	}
	
	/**
	 * 解释：创建一个新的拒绝理由（ID由数据库自动产生）
	 * @param refuse
	 * @return
	 */
	public boolean createRefuse(Refuse refuse){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(this.createNewRefuseSql);
			ps.setInt(1, refuse.getApplicationId());
			ps.setString(2, refuse.getReason());
			ps.setTimestamp(3, refuse.getTime());
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
	public Refuse refuseGetById(int refuseId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(this.refuseGetByIdSql);
			ps.setInt(1,refuseId);
			rs = ps.executeQuery();
			if(rs.next()){
				String reason = rs.getString("refuse_reason");
				int applicationId = rs.getInt("application_id");
				Timestamp refuseTime = rs.getTimestamp("refuse_time");
				Refuse refuse = new Refuse(refuseId,applicationId,reason,refuseTime);
				return refuse;
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
	 * 解释：通过出差申请的ID来查询对应的一系列拒绝记录
	 * @param applicationId
	 * @return
	 */
	public ArrayList<Refuse> refuseGetByApplicationId(int applicationId){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Refuse> list = new ArrayList<Refuse>();
		try {
			ps = conn.prepareStatement(this.refuseGetByApplicationIdSql);
			ps.setInt(1,applicationId);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("refuse_id");
				String reason = rs.getString("refuse_reason");
				Timestamp time = rs.getTimestamp("refuse_time");
				Refuse refuse = new Refuse(id,applicationId,reason,time);
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
