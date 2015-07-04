import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Login extends JPanel implements ActionListener{
	
	//Variables to display
	JLabel userL = new JLabel("Username: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Password: ");
	JPasswordField passTF = new JPasswordField();
	JPanel loginP = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();
	JButton login = new JButton("Login");
	JButton register = new JButton("Register");
	CardLayout cl;
	
	Login(){
		setLayout(new CardLayout()); //Cardlayout allows panels to be swapped
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		
		login.addActionListener(this);
		register.addActionListener(this);
		loginP.add(login); //add buttons created to the action panel
		loginP.add(register);
		panel.add(loginP); //add loginpanel to panel
		add(panel, "login"); //adding to the cardlayout, you can have a key
		cl = (CardLayout) getLayout();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		add(new Register(), "register"); //make new Register with register Key
		cl.show(this, "register"); //Show register class
	}
	public static void main(String[] args){
		JFrame frame = new JFrame("Text Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}
}
