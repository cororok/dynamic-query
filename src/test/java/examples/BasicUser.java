package examples;

/**
 * POJO bean, doesn't have to be matched a specific table.
 * 
 * @author songduk.park cororok@gmail.com
 *
 */
public class BasicUser {
	private String id;
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
