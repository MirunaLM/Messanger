import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Login {

	public static void main(String[] args) {
		JFrame login = new JFrame("Login");
		JTextField loginName = new JTextField();
		JTextField password = new JTextField();
		JButton enter = new JButton("Login");
		JButton register = new JButton("Register");
		JCheckBox checkBox = new JCheckBox("Teacher", true);

		password.setLocation(70, 50);
		password.setSize(200, 30);

		loginName.setLocation(70, 20);
		loginName.setSize(200, 30);

		enter.setSize(90, 30);
		enter.setLocation(280, 20);

		register.setSize(90, 30);
		register.setLocation(280, 50);

		checkBox.setSize(20, 20);
		checkBox.setLocation(250, 80);

		login.setLayout(null);
		login.setSize(400, 150);
		login.add(enter);
		login.add(register);
		login.add(loginName);
		login.add(password);
		login.add(checkBox);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		register.addActionListener(event -> {
			try {
				createAccount(loginName.getText(), password.getText(), checkBox.isSelected());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		enter.addActionListener(event -> {
			try {
				checkAccount(loginName.getText(), password.getText());
				Menu menu = new Menu(loginName.getText(), login);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		loginName.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Menu menu = new Menu(loginName.getText(), login);
				}
			}
		});

	}

	private static void checkAccount(String username, String password) throws SQLException {
		DataBase database = new DataBase();
		if (database.checkAccount(username, password)) {
			System.out.println("Nice");
		} else {
			System.out.println("Huaa");
		}

	}

	private static void createAccount(String username, String password, Boolean isTeacher) throws SQLException {
		DataBase database = new DataBase();
		if (database.checkIfExists(username) == true) {
			database.insertInDataBase(username, password);
			System.out.println("Succesfull");
		} else {
			System.out.println("Failed");
		}
	}
}
