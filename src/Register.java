import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import javax.swing.*;


public class Register extends JPanel implements ActionListener{
	JLabel userL = new JLabel("Choose a Username: ");
	JTextField userTF = new JTextField();
	JLabel passL = new JLabel("Password");
	JPasswordField passTF = new JPasswordField();
	JLabel passLC = new JLabel("Confirm Password");
	JPasswordField passC = new JPasswordField(); //password confirm
	
	JButton register = new JButton("Register");
	JButton back = new JButton("Back");
	
	public Register(){
		JPanel loginP = new JPanel();
		loginP.setLayout(new GridLayout(4,2));
		loginP.add(userL);
		loginP.add(userTF);
		loginP.add(passL);
		loginP.add(passTF);
		loginP.add(passLC);
		loginP.add(passC);
		loginP.add(register);
		loginP.add(back);
		register.addActionListener(this);
		back.addActionListener(this);
		add(loginP);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == register && passTF.getPassword().length > 0 && userTF.getText().length() > 0){ //Make sure form is filled and passowrd matched
			String pass = new String(passTF.getPassword());
			String confirm = new String(passC.getPassword());
			if(pass.equals(confirm)){ //test whether password exists
				try {
					BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
					String line = input.readLine();
					while(line != null){
						StringTokenizer st = new StringTokenizer(line); //So we can just get the username
						
						if(userTF.getText().equals(st.nextToken())){
							System.out.println("User already exists.");
							return;
						}
						line = input.readLine();
					}
					input.close();
					//A a byte format of the password and convert to string
					MessageDigest md = MessageDigest.getInstance("SHA-256");//hashes our password
					md.update(pass.getBytes());
					byte byteData[] = md.digest();
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i < byteData.length; i++){
						//String buffer obtaining new password
						sb.append(Integer.toString((byteData[i] & 0xFF) + 0x100,16).substring(1));
					}	
					//Write out password
					BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt", true));
					//User information
					output.write(userTF.getText() + " " + sb.toString() + "\n");
					output.close();						//Go back to login
					Login login = (Login) getParent();
					login.cl.show(login, "login");


				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//Back Button
		if(e.getSource() == back){
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
	}
}
