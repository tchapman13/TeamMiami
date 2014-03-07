//package Server;
import java.sql.*;
/**
 * class for connecting with database
 * @author lxz253
 *
 */
public class DBAccess {
	public Connection connectDB(){
		
		 Connection con=null;
			try {
				/**loading the driver of database**/
				 Class.forName( "org.postgresql.Driver" ).newInstance();  
				 
				 /**connection string**/
	             String url = "jdbc:postgresql://dbteach2.cs.bham.ac.uk:5432/";  
	             /**connect to database with connection string, database username and password**/
	             con = DriverManager.getConnection(url, "lxz253" , "zhangli" );  
	             
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return con;
	}
	/**
	 * close connection of database
	 * @param con
	 */
	public void closeConnect(Connection con){
		if(con!=null){
			try{
				con.close();
			}catch(Exception e){
				e.getMessage();
			}
		}
	}
}
