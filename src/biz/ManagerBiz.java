package biz;

public class ManagerBiz {
	
	private static ManagerBiz managerBiz;
	
	
	public static ManagerBiz getInstance(){
		if(managerBiz == null){
			managerBiz = new ManagerBiz();
		}
		return managerBiz;
	}
}
