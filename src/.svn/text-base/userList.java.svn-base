
	import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

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

	public class userList extends JFrame {
		
		
		// Instance attributes used in this example
		private	JPanel	topPanel;
		private	JList		listbox;

		// Constructor of main frame
		public userList()
		{
			// Set the frame characteristics
			
			setTitle( "Users that are on Line" );
			setSize( 150, 200 );
			//setBackground( Color.gray );
			Border roundedBorder = new LineBorder(Color.gray, 3, true); 

			// Create a panel to hold all other components
			topPanel = new JPanel();
			topPanel.setLayout( new BorderLayout() );
			getContentPane().add( topPanel );
			topPanel.setBorder(roundedBorder);

			// Create some items to add to the list
			String	listData[] = // array from server / database
			{
				"  User 1",
				"  User 2",
				"  User 3",
				"  User 4"
			};

			// Create a new listbox control
			listbox = new JList( listData );
			topPanel.add( listbox, BorderLayout.CENTER );
		}

		// Main entry point for this example
		public static void main( String args[] )
		{
			// Create an instance of the test application
			userList onlineUsers	= new userList();
			
			onlineUsers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			onlineUsers.setVisible( true );
		}
	}


