import java.io.*;


/**
 * @author Team Miami
 * @version 1.0 
 * @created 04/03/2014
 *  
 * This is the class that creates user objects, this will create and instance of user 
 * that is passed via the server to the Database. 
 * 
 * 
 * 
 * LET ME KNOW IF THERES MORE TO ADD OF MORE COMMENTS ARE NEEDED
 *
 */
public class newUser implements Serializable{
	
	
	/*
	 * private static final long serialVersionUID = 1L;
	 * 
	 * 
	 * This is the extra line that we might need it is recommend by eclipse and auto added !
	 */
	
	String userName;
	String password;
	boolean existing; // tells server + Database user state TRUE = new user FALSE = existing user
	
	public newUser (String userName, String password, boolean existing) {
		
		
		this.userName = userName;
		this.password = password;
		this.existing = existing; // new or existing user is defined here by clicking login or create account 

}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isExisting() {
		return existing;
	}

	public void setExisting(boolean existing) {
		this.existing = existing;
	}
}