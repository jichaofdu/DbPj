package entity;

public class User {
	private int id;
	private String password;
	private String name;
	private int type;
	private boolean disabled;
	
	public User(int newID,String newPassword,String newName,int newType,boolean newDisabled){
		this.id = newID;
		this.password = newPassword;
		this.name = newName;
		this.type = newType;
		this.disabled = newDisabled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
