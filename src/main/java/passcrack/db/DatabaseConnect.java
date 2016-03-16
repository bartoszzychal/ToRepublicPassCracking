package passcrack.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import passcrack.user.User;

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
				.prepareStatement("select user_id, username, email, password, salt from users_password where dpassword is null")) {
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
	
	public void updateUserData(final User user,final String password){
		Logger log = Logger.getLogger(getClass().getName());
		try (Connection con = DriverManager.getConnection(databaseConnectLink)) {
			prepareUpdate(user, password, log, con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void prepareUpdate(final User user, final String password, Logger log, Connection con) throws SQLException {
		try (PreparedStatement ps = con
				.prepareStatement("update users_password set dpassword=? where user_id=? and password=? and salt=?")) {
			prepareStatment(user, password, ps);
			int executeUpdate = ps.executeUpdate();
			if (executeUpdate==0) {
				log.warning(ps + " not execute!!!");
			}else{
				log.info(ps +" execute. OK");
			}
		}
	}
	private void prepareStatment(final User user, final String password, PreparedStatement ps) throws SQLException {
		ps.setString(1, password);
		ps.setLong(2, user.getId());
		ps.setString(3, user.getPassword());
		ps.setString(4, user.getSalt());
	}
}
