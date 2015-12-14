package biz;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Test {
	public static void main(String[] args) throws InvalidFormatException, IOException{
		//ImportNewInformationBiz importInfoBiz = ImportNewInformationBiz.getInstance();
		//importInfoBiz.insertSetOfNewUser("E:\\数据库技术\\Excel数据\\01初始用户数据.xlsx");
		//importInfoBiz.insertSetNewProject("E:\\数据库技术\\Excel数据\\02初始项目数据.xlsx");
		//User user = new User(123,"asd","asd",2);
		//UserDao.getInstance().userCreate(user);
		int id = 2015110001;
		String password = "110001";
		UserBiz userBiz = UserBiz.getInstance();
		boolean a = userBiz.userLogin(id, password);
		if(a == true){
			System.out.println("登录成功");
		}else{
			System.out.println("登录失败");
		}
		userBiz.userDelete(id);
	}
}
