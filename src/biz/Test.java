package biz;
import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Test {
	public static void main(String[] args) throws InvalidFormatException, IOException{
		ImportNewInformationBiz importInfoBiz = ImportNewInformationBiz.getInstance();
		importInfoBiz.insertSetOfNewUser("E:\\数据库技术\\Excel数据\\01初始用户数据.xlsx");
		importInfoBiz.insertSetNewProject("E:\\数据库技术\\Excel数据\\02初始项目数据.xlsx");
		//User user = new User(123,"asd","asd",2);
		//UserDao.getInstance().userCreate(user);
	}
}
