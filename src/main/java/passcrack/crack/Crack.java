package passcrack.crack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import passcrack.DictionaryReader.DictionaryReader;
import passcrack.db.DatabaseConnect;
import passcrack.encoder.Encoder;
import passcrack.user.User;
import passcrack.writer.Writer;

public class Crack implements Runnable {

	private final static String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String JDBC_MYSQL_LOCALHOST_DATABASE = "jdbc:mysql://localhost/forum?";
	private final static String user = "root";
	private final static String password = "capgemini";
	private List<User> pass_users;

	private final DatabaseConnect database = new DatabaseConnect();
	private DictionaryReader dictionaryReader = null;
	private Writer writer = null;

	public Crack() {
		database.preprareConnectingWithDatabase(COM_MYSQL_JDBC_DRIVER, JDBC_MYSQL_LOCALHOST_DATABASE, user, password);
		try {
			pass_users = database.getListOfUser();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void decode() throws IOException, Exception {
		long start = System.currentTimeMillis();
		writer.write("Password;User id;username;email;hash sha1(salt+sha1(Password));salt");
		while (dictionaryReader.ready()) {
			String word = dictionaryReader.readWord();
			String sha1 = Encoder.encode(word);			
			pass_users.parallelStream()
			.filter(u -> !u.isDecode())
			.forEach((user)->{
				String encodeSaltSha1 = Encoder.encode(user.getSalt().concat(sha1));
				if (user.getPassword().equals(encodeSaltSha1)) {
					System.out.println(word+";"+user+" "+(System.currentTimeMillis()-start));
					writer.write(word, user);
					user.setDecode(true);
				}
			});
			
		}
	}

	public void setWriter(Writer wr) {
		this.writer = wr;
	}

	public void setDictionaryReader(DictionaryReader dr) {
		this.dictionaryReader = dr;
	}

	@Override
	public void run() {
		try {
			decode();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean equals(final String s1, final String s2) {
		return s1 != null && s2 != null && s1.hashCode() == s2.hashCode()
		    && s1.equals(s2);
		}

}
