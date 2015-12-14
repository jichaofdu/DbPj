package biz;
import dao.UserDao;
import entity.User;

public class UserBiz {
	private UserDao userDao;
	private static UserBiz userBiz;
	private UserBiz(){
		userDao = UserDao.getInstance();
	}
	public static UserBiz getInstance(){
		if(userBiz == null){
			userBiz = new UserBiz();
		}
		return userBiz;
	}
	/*用户登录逻辑*/
	public boolean userLogin(int userId,String password){
		User user = userDao.userGetById(userId);
		if(user != null && user.getPassword().equals(password)){
			return true;
		}else{
			return false;
		}
	}
	/*通过id获取到用户的信息*/
	public User userGetByUserId(int userId){
		User user = userDao.userGetById(userId);
		return user;
	}
	
	/*删除用户*/
	public boolean userDelete(int userId){
		User user = userDao.userGetById(userId);
		if(user != null){
			user.setDisabled(true);
			boolean result = userDao.userInfoChange(user);
			return result;
		}else{
			return true;
		}
	}
	
	
	
	
}
