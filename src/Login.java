import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == login){
			try {
				BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
				String pass = null;
				String line = input.readLine();
				while(line != null){
					//if user is matched, the next token will be the password
					StringTokenizer st = new StringTokenizer(line);
					if(userTF.getText().equals(st.nextToken()))
						pass = st.nextToken();
						line = input.readLine();
				} 
				input.close();
				
				//Rerun the hash on the user password to compare to the register. 
				//compare sb to password in file
				//A a byte format of the password and convert to string
				MessageDigest md = MessageDigest.getInstance("SHA-256");//hashes our password
				md.update(new String(passTF.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < byteData.length; i++){
					//String buffer obtaining new password
					sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100,16).substring(1));
				} if(pass.equals(sb.toString()))
					System.out.println("You have logged in");
					add(new FileBrowser(userTF.getText()), "fb");
					cl.show(this, "fb");
				
			} catch (FileNotFoundException e1) {
				
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				e1.printStackTrace();
			}
		}
		
		
		if(e.getSource() == register){
			add(new Register(), "register"); //make new Register with register Key
			cl.show(this, "register"); //Show register class
		}
		
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
