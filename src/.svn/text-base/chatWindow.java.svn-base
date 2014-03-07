import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class chatWindow extends JFrame implements ActionListener {
	
	
	JTextArea chatViewer;
	JTextArea typeMessage;
	JButton send;
	
	
	
		public chatWindow(){
		
			super ("Chat Window");
			
			setLayout(new GridLayout(3,1));
			
			Border roundedBorder = new LineBorder(Color.gray, 1, true); 
			

			
			
		this.chatViewer = new JTextArea();
		//chatViewer.setLineWrap(true);
		chatViewer.setBorder(roundedBorder);
		add(this.chatViewer);

		
		this.typeMessage = new JTextArea();		
		//chatViewer.setLineWrap(true);
		typeMessage.setBorder(roundedBorder);
		add(this.typeMessage);
		
		
		this.send = new JButton("Send");
		add(this.send);
		send.addActionListener(this);
		
	

		}
		
		 public void actionPerformed(ActionEvent e) {
				if (e.getSource() == send) {
				    
				    chatViewer.setText(chatViewer.getText() + ""+typeMessage.getText()+ "\n");
				    typeMessage.setText("");
				}
}
}
