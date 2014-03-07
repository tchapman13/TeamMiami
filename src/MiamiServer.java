//package server;



import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
//import user.newUser;

public class MiamiServer extends JFrame {
	//SQL variables
	private PreparedStatement pstmt;//statement to send to database
	private ResultSet rs=null;//result set we will get back
	Connection con=null;//SQL connection
	
	//streams
	private ObjectOutputStream output;
	private ObjectInputStream input;	


	
	
	//variables sent by client
	private String clientName;
	private String clientPassword;
	private boolean clientExisting; 
	
	//variables retrieved from database
	private String passwordOnDatabase;
	private String userOnDatabase;
	private boolean databaseIsLoggedIn;
	
	//boolean based on value from databaseIsLoggedIn
	private boolean isLogin;

	
	//sockets
	private ServerSocket miamiSocket;
	private Socket connection;
	
	//message to be sent to client
	private ArrayList<String> message;
	
	//user object to be set to the object sent by client
	private newUser user;
	
	//array list for getting list of online users
	private ArrayList<String> usersOnline = new ArrayList<String>();
	
	
	public MiamiServer(int port) throws IOException, ClassNotFoundException {
		//Create server socket
		miamiSocket = new ServerSocket(port);

		//Listen for connection
		while (true) {
			
			System.out.println("Listening for connection on port " + port);
			//accept incoming connection
			connection = miamiSocket.accept();
			System.out.println("Connected to " + connection.getInetAddress().getHostName() + "\n");
    		
			System.out.println("Setting up stream objects");
			//set up streams
			output = new ObjectOutputStream(connection.getOutputStream());
    		output.flush();
    		input = new ObjectInputStream(connection.getInputStream());
			System.out.println("Stream object set up");            
    		
    		//attempt to connect to database
    		try {
    			System.out.println("Attempting to create object from input stream");
            	//create user object from input stream sent by client
            	user =  (newUser) input.readObject();
    			System.out.println("created new user," +user.getUserName() + ", from input stream.");           	
            	clientName=user.getUserName();
            	clientPassword=user.getPassword();
            	clientExisting=user.isExisting();
    			System.out.println("Got fields from newUser object");
            	System.out.println("User name received from client = " + clientName);
            	System.out.println("Password received from client = " + clientPassword);           	
            	System.out.println("Is this an existing user? " + clientExisting);    			
    			
    			//connect to database
    			System.out.println("Connecting to database . . . ");
    			//DBAccessHome db=new DBAccessHome();//Comment this out when at uni. Uncomment when at home
    			DBAccess db=new DBAccess();//use this code when at Uni. comment out when at home
            	
            	con=db.connectDB();
            	System.out.println(". . . and we are connected");
            	//check the database first , whether username exists or not.
            	PreparedStatement check=con.prepareStatement("select * from user1 where username=?");
    			check.setString(1, clientName);
    			
    			//check if user name is in database
    			
    				rs = check.executeQuery();
    				
    				//Check if client is trying to create an account that already exists
    				
        	
    				
    				
	    			//if user name does not exist ,then insert new record
	        		if(!rs.next() && clientExisting == false){
    					
	        			//Client is trying to create new account
	        			System.out.println("Client is trying to create new account");
	        			System.out.println(clientName + " is not registered. We must add " + clientName + " to the database.");
	        			//update database
	        			PreparedStatement insertUser=con.prepareStatement("insert into user1 values (?,?,?)");
	        			//get user name and password from client
	        			insertUser.setString(1, clientName);
	        			insertUser.setString(2, clientPassword);
	        			insertUser.setBoolean(3, true);
	        	
	        			if(insertUser.executeUpdate()==1){
	        				System.out.println(clientName + " successfully inserted into database");
	        				output.writeObject(clientName + " successfully inserted into database");
	        				//Get a list of users who are online with getUsersOnline() method
	        				message = getUsersOnline(con, usersOnline);
	        				//Get results of method call and send to client view output stream
	        				output.writeObject("Users online: " + message);
	        				
	        				
	        				
	        			} else {
	        				//If there is an SQL error, shut everything down
	        				System.out.println("sql update error");
	        				db.closeConnect(con);
	        				miamiSocket.close();
	        			}
	        			
	        		}
	        		else if (!rs.next() && clientExisting == true) {
	        			System.out.println("Attempting to log in existing account.");
	        			
	        			//Client is trying to log into an existing account and account exists
	        			//search database
	        			PreparedStatement selectUser=con.prepareStatement("select * from user1 where username=?");
	        			
	        			selectUser.setString(1, clientName);
	        			
	        		
	        			
	        			rs=selectUser.executeQuery();
	        			System.out.println("Select query executed.");
	        			//if the database has clientName then execute while(rs.next) 
	        			if(rs.next()){
		        			System.out.println("Record exists on databse.");

	        				userOnDatabase=rs.getString(1);
	        				passwordOnDatabase=rs.getString(2);
	        				isLogin=rs.getBoolean(3);
	        				//Check the password matches that on database
	        				if(clientPassword.equals(passwordOnDatabase)){
			        			System.out.println("Checking passwords match.");

			        			//remove code queries are in databse and server invokes the meothds.....
			        			
	        					//Check user is not already logged in on another computer
	        					if(isLogin==false){
				        			System.out.println("Checking client is not logged in elsewhere.");
	        						PreparedStatement update=con.prepareStatement("update user1 set islogin=true where username=?");
	        						update.setString(1, clientName);
	            					update.executeUpdate();
	            					output.writeObject("You are connected to the server!");
	            					System.out.println("Passwords match, client is not logged in elsewhere: connected!");
	    	        				message = getUsersOnline(con, usersOnline);
	    	        				output.writeObject("Users online: " + message);
	        					} 
	        					//report that client is logged in elsewhere.
	        					else{
	        						System.out.println(clientName + " is already logged in");
	            					output.writeObject(clientName + " is already logged in");

	        						db.closeConnect(con);
		        					connection.close();
	        					}
	        					
	        					
	        				//report error if passwords do not match
	        				}else{
	        					System.out.println("Password error. Connection refused");
            					output.writeObject("Password error. Connection refused");

	        					connection.close();
	            				db.closeConnect(con);
	        				
	        			}
        		
        			} else {
        				//Client is attempting to log into an account that doesn't exist.
        				System.out.println("Username not recognised. Connection refused");
    					output.writeObject("Username not recognised. Connection refused");
        				connection.close();
        				db.closeConnect(con);      				
        			}
	        			
		
	        		}else {
	        			
	        			//Client is trying to log into an account, but account does not exist
	        			System.out.println("Username already taken");
    					output.writeObject("Username already taken. Connection refused");
	        			
    					connection.close();
        				db.closeConnect(con);
       			
        		}
	        		
	        		
            	
            	
            	
            	 
            	
            
            	
            	
			} catch (EOFException eofexception) {
				
				System.out.println("Stream ended unexpectedly!");				
				System.out.println("Connection terminated!");
				eofexception.printStackTrace();
			}
				
			 catch (SQLException e) {
				System.out.println("SQL error");
				e.printStackTrace();
				connection.close();
				
			}

			
			
		}
		
		

	}
	
	
	
	
	/** 
	 * @param Takes a connection and arraylist<String> as paramenters
	 * @return returns a string, built from strings stored in arraylist
	 * 
	 * Method to get a list of users that are already logged in
	 * */
	private ArrayList<String> getUsersOnline(Connection c, ArrayList<String> a) {
		String allUsers = "";
		try {
			//SQL statement to get all users
			PreparedStatement getUsers=c.prepareStatement("select username from user1");
			ResultSet results = getUsers.executeQuery();;
			String name = "";
			while (results.next()) {
				//get name, convert to string, add to array list
				name = (String) results.getObject(1) + ", ";
				a.add(name);
				
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//return list of users in string form
		return a;
		
	}
	
	//main method
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		//create new connection
		new MiamiServer(50500);
		
		
		
	}

	
	
	
}
