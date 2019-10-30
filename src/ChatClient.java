import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements Runnable {

	String loginName;

	JTextArea messages;
	JTextField sendMessage;

	JButton send;
	JButton logout;

	DataInputStream in;
	DataOutputStream out;

	private void logout() {
		try {
			out.writeUTF(loginName + " LOGOUT");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(1);
	}

	private void send() {
		if (sendMessage.getText().length() > 0) {
			try {
				out.writeUTF(loginName + " DATA: " + sendMessage.getText());
			} catch (IOException e1) {
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
			if (sendMessage.getText().length() > 0) {
				logout();
			}
		});

		sendMessage.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					send();
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

		});

		Socket socket = new Socket("127.0.0.1", 1234);

		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());

		out.writeUTF(loginName);
		out.writeUTF(loginName + " LOGIN");

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

		add(new JScrollPane(messages));
		add(messages);
		add(sendMessage);
		add(send);
		add(logout);

		new Thread(this).start();
		setVisible(true);

	}

	@Override
	public void run() {
		while (true) {
			try {
				messages.append("\n" + in.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
