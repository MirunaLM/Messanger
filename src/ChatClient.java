import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

public class ChatClient extends JFrame implements Runnable {

	String loginName;

	JTextArea messages;
	JTextField sendMessage;

	JButton send;
	JButton logout;
	
	DefaultListModel  loginNames;
	JList onlineUsers = new JList();
	
	DataInputStream in;
	DataOutputStream out;
	
	private void logout() {
		try {
		out.writeUTF(loginName + " LOGOUT");
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	System.exit(1);
	}
	
	private void send() {
		if(sendMessage.getText().length() > 0) {
			try {
				out.writeUTF(loginName + " DATA: " + sendMessage.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sendMessage.setText("");
		}
	}

	public ChatClient(String loginName) throws UnknownHostException, IOException {
		super(loginName);
		this.loginName = loginName;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				logout();
			}
		});

		messages = new JTextArea(18, 50);
		sendMessage = new JTextField(50);

		send = new JButton("Send");
		logout = new JButton("Logout");
		
		send.addActionListener(event -> {
			send();
		});
		
		logout.addActionListener(event -> {
				if(sendMessage.getText().length() > 0) {
					logout();
				}
		});
		
		sendMessage.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}
					
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		} );
		
		Socket socket = new Socket("127.0.0.1", 5219);
		
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF(loginName);
		out.writeUTF(loginName +" LOGIN");
		//out.writeUTF(loginName +" NAME");

		setup();

	}

	private void setup() {
		setLayout(null);
		setSize(800, 400);

		messages.setSize(600, 270);
		messages.setLocation(0, 2);
		messages.setEditable(false);

		logout.setSize(80, 30);
		logout.setLocation(500, 300);

		send.setSize(80, 30);
		send.setLocation(410, 300);

		sendMessage.setSize(370, 30);
		sendMessage.setLocation(20, 300);
		
		onlineUsers.setSize(200, 200);
		onlineUsers.setLocation(620, 100);
		onlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineUsers.setSelectedIndex(0);
		onlineUsers.setVisibleRowCount(5);
		JScrollPane listScrollPane = new JScrollPane(onlineUsers);

		
		add(new JScrollPane(messages));
		add(messages);
		add(sendMessage);
		add(send);
		add(logout);
		add(onlineUsers);
		
		new Thread(this).start();
		setVisible(true);

	}
	
	public JList getOnlineUsers() {
		return onlineUsers;
	}

	@Override
	public void run() {
		while(true) {
			try {
//				loginNames = new DefaultListModel();
				messages.append("\n" + in.readUTF());
//				String[] strArray1 = in.readUTF().split(" ");
//				for (String item : strArray1) { // Cycle through all the pieces
//					if (item.startsWith("NAME")) {
//						String target = item.substring(item.indexOf("NAME") + 4, item.indexOf("ENDNAME")); // Your desired String
//						loginNames.addElement(target);
//					}
//				}
//				onlineUsers.setModel(loginNames);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
