import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	private Connection con;
	
	public void DataBase() throws ClassNotFoundException, SQLException {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "TarZan13");
	}
	
	public void insertInDataBase(String username, String password) throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "TarZan13");
		String query = " insert into accounts (username, password, id)" + " values (?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
	    preparedStmt.setString (1, username);
	    preparedStmt.setString (2, password);
	    preparedStmt.setInt(3, getMaxID() + 1);
	    preparedStmt.execute();
	    con.close();
	}

	public int getMaxID() throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT ID FROM login.accounts WHERE ID = (SELECT MAX(id) FROM login.accounts);");
		while(rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}
	
	public Boolean checkIfExists(String username) throws SQLException {
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "TarZan13");
		ResultSet rs = con.createStatement().executeQuery("SELECT username FROM login.accounts;");
		while(rs.next()) {
			if(rs.getString(1).equals(username)) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean checkAccount(String username, String password) throws SQLException {
		
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login", "root", "TarZan13");
		ResultSet rsUsername = con.createStatement().executeQuery("SELECT username FROM login.accounts;");
		int contor = 0;
		while(rsUsername.next()) {
			if(rsUsername.getString(1).equals(username)) {
				contor++;
			}
		}
		ResultSet rsPassword = con.createStatement().executeQuery("SELECT password FROM login.accounts;");
		while(rsPassword.next()) {
			if(rsPassword.getString(1).equals(password)) {
				contor++;
			}
		}
		
		if(contor > 1) {
			return true;
		}
		
		return false;
	}
	
}
