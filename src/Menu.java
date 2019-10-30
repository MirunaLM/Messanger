import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Menu extends JFrame {
	JButton chat = new JButton("Chat");
	JButton play = new JButton("Play");
	public String loginName;

	public Menu(String loginName, JFrame login) {
		this.loginName = loginName;
		setSize(400, 150);
		setVisible(true);
		setLayout(null);

		chat.setSize(90, 30);
		chat.setLocation(50, 30);

		play.setSize(90, 30);
		play.setLocation(220, 30);

		add(chat);
		add(play);

		login.setVisible(false);

		startChat();
	}

	public void startChat() {
		chat.addActionListener(event -> {
			try {
				startChat(loginName);
				setVisible(false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private static void startChat(String loginName) throws IOException {
		ChatClient chatClient = new ChatClient(loginName);
	}

}
