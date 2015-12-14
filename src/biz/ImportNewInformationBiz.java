package biz;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import dao.ProjectDao;
import dao.SystemManagerDao;
import dao.UserDao;
import entity.Project;
import entity.SystemManager;
import entity.User;

public class ImportNewInformationBiz {
	private UserDao userDao;
	private ProjectDao projectDao;
	private SystemManagerDao systemManagerDao;
	private static ImportNewInformationBiz importInfoBiz;
	
	private ImportNewInformationBiz(){
		userDao = UserDao.getInstance();
		projectDao = ProjectDao.getInstance();
		systemManagerDao = SystemManagerDao.getInstance();
	}
	
	public static ImportNewInformationBiz getInstance(){
		if(importInfoBiz == null){
			importInfoBiz = new ImportNewInformationBiz();
		}
		return importInfoBiz;
	}
	
	public boolean insertSetOfNewUser(String path) throws IOException, InvalidFormatException{
			InputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0); 
			int rowNum = sheet.getLastRowNum();
	        String[][] values = new String[rowNum - 1][4];
	        String[] attributes = new String[4];
	        Row firstRow = sheet.getRow(0);
	        for(int i = 0;i < 4;i++){
	        	Cell cell = firstRow.getCell(i);
	        	attributes[i] = cell.getStringCellValue();
	        }
			for(int i = 1;i < rowNum;i++){
				Row row = sheet.getRow(i);
				if(row == null){
					break;
				}
		        for (int j = 0;j < 4;j++){ 
		        	Cell cell = row.getCell(j); 
		        	values[i-1][j] = cell.toString();
		        } 
			}
			for(int i = 0;i < values.length;i++){
				int type = -1;
				switch(values[i][3]){
					case "产品经理": type = 1;break;
					case "销售": type = 2;break;
					case "开发": type = 3;break;
				}
				if(type == -1){
					String name = values[i][1];
					String password = values[i][2];
					SystemManager newSystemManager = new SystemManager(-1,password,name);
					systemManagerDao.createSystemManager(newSystemManager);
					System.out.println("[Tip] 创建了一个管理员");
				}else{
					int newId = Integer.parseInt(values[i][0]);
					String name = values[i][1];
					String password = values[i][2];
					User tempUser = new User(newId,password,name,type);
					userDao.userCreate(tempUser);
					System.out.println("[Tip] 创建了一个新用户");
				}

			}
		    inp.close(); 
			return true;
	}
	
	public void insertSetNewProject(String path) throws InvalidFormatException, IOException{
		InputStream inp = new FileInputStream(path);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0); 
		int rowNum = sheet.getLastRowNum();
        String[][] values = new String[rowNum - 1][4];
        String[] attributes = new String[4];
        Row firstRow = sheet.getRow(0);
        System.out.println(rowNum);
        for(int i = 0;i < 4;i++){
        	Cell cell = firstRow.getCell(i);
        	attributes[i] = cell.getStringCellValue();
        }
		for(int i = 1;i < rowNum;i++){
			Row row = sheet.getRow(i);
			if(row.equals(null)){
				break;
			}
	        for (int j = 0;j < 4;j++){ 
	        	Cell cell = row.getCell(j);  
	        	values[i-1][j] = cell.toString();
	        	System.out.println(values[i-1][j]);
	        } 
		}
		//调用Dao将数据插入
		for(int i = 0;i < values.length;i++){
			int pjId = Integer.parseInt(values[i][0]);
			String pjName = values[i][1];
			System.out.println(pjName);
			String pjDescription = values[i][2];
			int manager = Integer.parseInt(values[i][3]);
			Project tempProject = new Project(pjId,manager,pjName,pjDescription);
			projectDao.projectCreate(tempProject);
			System.out.println("[Tip] 创建了一个新项目");
		}
	    inp.close(); 
	}
	

}
