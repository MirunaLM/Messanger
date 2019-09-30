import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class LogIn {
	
	public static void startChat(JFrame login, String loginName) {
		try {
			ChatClient chatClient = new ChatClient(loginName);
			login.setVisible(false);
			login.dispose();
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		JFrame login = new JFrame("Login");
		JTextField loginName = new JTextField();
		JButton enter = new JButton("Login");
		
		loginName.setLocation(20, 20);
		loginName.setSize(250, 30);
		enter.setSize(70, 30);
		enter.setLocation(300, 20);
		
		login.setLayout(null);
		login.setSize(400, 150);
		login.add(enter);
		login.add(loginName);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		enter.addActionListener(event -> {
			startChat(login, loginName.getText());
		});
		
		loginName.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					startChat(login, loginName.getText());
			}
		} );

	}

}
