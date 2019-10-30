import java.awt.Window;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChatServer {
	ArrayList<String> loginNames;
	ArrayList<Socket> clientSockets;

	ChatServer() throws IOException {
		ServerSocket server = new ServerSocket(1234);
		loginNames = new ArrayList<String>();
		clientSockets = new ArrayList<Socket>();
		while (true) {
			Socket clientSocket = server.accept();
			Client client = new Client(clientSocket);
		}
	}

	class Client extends Thread {

		Socket clientSocket;

		DataInputStream in;
		DataOutputStream out;

		int pos = 0;
		int i = 0;

		Client(Socket client) throws IOException {
			clientSocket = client;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());

			String loginName = in.readUTF();
			System.out.println("login name: " + loginName);
			loginNames.add(loginName);
			clientSockets.add(clientSocket);

			start();

		}

		public void run() {
			while (true) {
				try {
					String msgFromClient = in.readUTF();
					System.out.print(msgFromClient);
					StringTokenizer msgParts = new StringTokenizer(msgFromClient);

					String name = msgParts.nextToken();
					String msgType = msgParts.nextToken();

					StringBuffer messageBuffer = new StringBuffer();

					while (msgParts.hasMoreTokens()) {
						messageBuffer.append(" " + msgParts.nextToken());
					}
					final String message = messageBuffer.toString();

					switch (msgType) {
					case "NAME":
						clientSockets.forEach(socket -> {
							try {
								onlineUsers(socket, name);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});

						break;
					case "LOGIN":
						clientSockets.forEach(socket -> {
							try {
								notifyLogin(socket, name);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
						break;
					case "LOGOUT":
						clientSockets.forEach(socket -> {
							if (name.equals(loginNames.get(i++))) {
								pos = i - 1;
							}
							try {
								performLogout(socket, name);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							// TODO Auto-generated catch block
						});

						loginNames.remove(pos);
						clientSockets.remove(pos);

						break;

					default:
						clientSockets.forEach(socket -> {
							try {
								notifyMessage(socket, name, message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
					}

					if (msgType.equals("LOGOUT"))
						break;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public void onlineUsers(Socket socket, String name) throws IOException {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			try {
				for (int i = 0; i < loginNames.size(); i++)
					out.writeUTF("NAME" + loginNames.get(i) + "ENDNAME");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void performLogout(Socket socket, String name) throws IOException {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			try {
				out.writeUTF(name + " has logged out");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void notifyMessage(Socket socket, String name, String message) throws IOException {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			try {
				out.writeUTF(name + ": " + message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void notifyLogin(Socket socket, String name) throws IOException {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			try {
				out.writeUTF(name + "  " + " has logged in");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void main(String[] args) throws IOException {

		ChatServer chat = new ChatServer();

	}
}
