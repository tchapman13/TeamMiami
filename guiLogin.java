
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.*;
/**
 * 
 * @author Team Miami
 * @version 1.0 
 * @created 04/03/2014
 * 
 * This is the login screen GUI and associated methods for sending information to the
 * server + Database
 * 
 * IF THERE IS ANYTHING MORE TO ADD  OR MORE COMMENTS ARE NEEDED PLEASE LET ME KNOW !! 
 * 
 * 
 *
 */

	
	public class guiLogin extends JFrame implements ActionListener {
		
		
		/*
		 * Labels and text fields in the order they will appear in the GUI 
		 */
		
		JLabel NameTaken; 	// hidden from view but appear if message from server / database received
		JLabel User;
		JTextField UserName;	
		JLabel Pass;
		JTextField Password;	
		JButton CreateAccount;		
		JButton Login;
		
		Socket connection;
		ObjectOutputStream output;
		ObjectInputStream input;
		
		public guiLogin() throws IOException{
			
			super("Login Screen"); // title of window
			
			//System.out.println("BEfore the connectoin");
			connection=new Socket("localhost",50500);// NEED TO ADD ip address here and port !!!!
	
			System.out.println("Connection is ok ! ");
			
			output=new ObjectOutputStream(this.connection.getOutputStream()); // new output
			
			input=new ObjectInputStream(this.connection.getInputStream()); // new input 
			
			output.flush(); // dont need to flush input stream 
		
			setLayout(new GridLayout(7,1)); // Grid Layout at the moment but can be changed
			
			
			/*
			 * NameTaken will update/change if server / database sends message that username is taken !!
			 * 
			 */
			
			this.NameTaken = new JLabel(""); 
			
			
			// User name label and textfield	
			
			this.User = new JLabel("User Name");
			this.UserName = new JTextField();
			
			
			// username label and textfield
			
			this.Pass = new JLabel("Password");
			this.Password = new JTextField();
	
			
			// buttons login and create Account
			
			this.CreateAccount = new JButton("Create Account");
			this.Login = new JButton("Login");
			

			// Adds everything to the GUI
			add(this.NameTaken);			
			add(this.User);
			add(this.UserName);
			add(this.Pass);
			add(this.Password);			
			add(this.CreateAccount);
			add(this.Login);	
			CreateAccount.addActionListener(this);
		}

		
		@Override
		public void actionPerformed(ActionEvent e) {
	
			/*
			 * 
			 * Action listener for the create account button 
			 * when create account button is clicked and new instance of newUser object called u1 is created
			 * and is sent to the server. 
			 * 
			 * this sends user with boolean of true - this tells the server and database that this
			 * is a new user
			 * 
			 * 
			*/
		
			if (e.getSource()==this.CreateAccount)
			{
				newUser u1= new newUser(this.UserName.getText(), this.Password.getText(),true);
				try {
					this.output.writeObject(u1);
				} catch (IOException e1) {	
					e1.printStackTrace();
				}				
			}
			
			/* 
			 * 
			 * Same action listener but in an else if loop ---- 
			 * if the login button is pressed instead of create account button this happens
			 * 
			 * this sends the object with same values but boolean is false tellings databse + server 
			 * that this is not a new user 
			 * 
			 *
			 */
			
			else if (e.getSource()==this.Login)
			{
				newUser u1= new newUser(this.UserName.getText(), this.Password.getText(),false);
				try {
					this.output.writeObject(u1);	
				} catch (IOException e1) {			
					e1.printStackTrace();
				}
			}
		
			/*
			 * 
			 * This bit is waiting for for a message from the database / server to see if user name 
			 * is taken....
			 * 
			 * if taken JLabel is updated to say " This user name is taken try another !!" 
			 * 
			 *
			 */
			try {
				String res=(String) this.input.readObject();
				this.NameTaken.setText("User Name taken try a new one !! ");
			} catch (ClassNotFoundException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		public static void main(String[] args) throws IOException {
			
			JFrame Login = new guiLogin();	
			Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			Login.setSize(200, 400);	
			Login.setVisible(true);	
		
	}
		
	}
