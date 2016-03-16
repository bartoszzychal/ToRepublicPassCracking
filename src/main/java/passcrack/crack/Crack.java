package passcrack.crack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import passcrack.DictionaryReader.Reader;
import passcrack.db.DatabaseConnect;
import passcrack.encoder.Encoder;
import passcrack.user.User;
import passcrack.utils.Utils;
import passcrack.writer.Writer;

public class Crack implements Runnable {

	private static final int WORD_DICTIONARY = 20000000;
	
	private List<User> pass_users;

	private final DatabaseConnect database = new DatabaseConnect();
	private Reader dictionaryReader = null;
	private Writer writer = null;

	public Crack() {
		database.preprareConnectingWithDatabase(Utils.COM_MYSQL_JDBC_DRIVER, Utils.JDBC_MYSQL_LOCALHOST_DATABASE, Utils.user, Utils.password);
		try {
			pass_users = database.getListOfUser();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void decode() throws IOException, Exception {
		long word_cunter = 0;
		long start = System.currentTimeMillis();
		writer.write("Password;User id;username;email;hash sha1(salt+sha1(Password));salt");
		int skipped_word = (int)(0.55*WORD_DICTIONARY);
		dictionaryReader.skip(skipped_word);
		word_cunter=+skipped_word;
		while (dictionaryReader.ready()) {
			String word = dictionaryReader.readLine();
			word_cunter= word_cunter+1;
			if(word_cunter%200000 == 0){
				System.out.println((word_cunter*100/WORD_DICTIONARY)+"%"+" "+(System.currentTimeMillis()-start));
			}
			String sha1 = Encoder.encode(word);			
			pass_users.parallelStream()
			.forEach((user)->{
				String encodeSaltSha1 = Encoder.encode(user.getSalt().concat(sha1));
				if (user.getPassword().equals(encodeSaltSha1)) {
					database.updateUserData(user, word);
					writer.write(word, user);
				}
			});
			
		}
		dictionaryReader.close();
	}

	public void setWriter(Writer wr) {
		this.writer = wr;
	}

	public void setDictionaryReader(Reader dr) {
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
