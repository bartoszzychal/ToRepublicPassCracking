package passcrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import passcrack.user.User;
import passcrack.writer.Writer;

public class DatabaseConnect {
	private final static String USER = "user=";
	private final static String PASSWORD = "&password=";
	private final static String SSL = "&verifyServerCertificate=true&useSSL=true";

	private String databaseConnectLink = null;
	private String driver = null;
	
	public void preprareConnectingWithDatabase(String driver, String DBaddress, String user, String password) {
		databaseConnectLink = DBaddress + USER + user + PASSWORD + password + SSL;
		this.driver = driver;
	}

	public List<User> getListOfUser() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		return getListOfUsersFromDatabase();
	}

	private List<User> getListOfUsersFromDatabase() throws SQLException {
		List<User> users = null;
		try (Connection con = DriverManager.getConnection(databaseConnectLink)) {
			users = query(con);
		}
		return users;
	}

	private List<User> query(Connection con) throws SQLException {
		List<User> users = null;
		try (PreparedStatement ps = con
				.prepareStatement("select id, username, email, password, salt from users where id!=1")) {
			ResultSet result = ps.executeQuery();
			users = getList2(result);
		}
		return users;
		
	}

	private List<User> getList2(ResultSet result) throws SQLException {
		List<User> users = new ArrayList<>();
		while (result.next()) {
			users.add(new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4),result.getString(5)));
		}
		return users;
	}
}
