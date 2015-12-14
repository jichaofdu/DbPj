package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.Refuse;
import util.JdbcUtil;

public class RefuseDao {
	private JdbcUtil util;
	private static RefuseDao refuseDao;
	private final String createNewRefuseSql
		= "insert into refuse(application_id,refuse_reason)"
			+ "values(?,?)";
	private final String refuseGetByIdSql
		= "select * from refuse where id = ?";
	
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
			ps.setString(2, refuse.getReason());;
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
				String reason = rs.getString("reason");
				int applicationId = rs.getInt("application_id");
				Refuse refuse = new Refuse(refuseId,applicationId,reason);
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
}
