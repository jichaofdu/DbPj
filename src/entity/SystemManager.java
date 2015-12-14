package entity;

public class SystemManager {
	private int id;
	private String password;
	private String name;
	
	public SystemManager(int newId,String newPassword,String name){
		this.id = newId;
		this.password = newPassword;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
