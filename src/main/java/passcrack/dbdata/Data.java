package passcrack.dbdata;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import passcrack.db.DatabaseConnect;
import passcrack.user.User;
import passcrack.writer.Writer;

public class Data {

	private final static String COM_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private final static String JDBC_MYSQL_LOCALHOST_DATABASE = "jdbc:mysql://localhost/forum?";
	private final static String user = "root";
	private final static String password = "capgemini";

	DatabaseConnect database = new DatabaseConnect();
	Writer writer = new Writer();

	private void init() {
		database.preprareConnectingWithDatabase(COM_MYSQL_JDBC_DRIVER, JDBC_MYSQL_LOCALHOST_DATABASE, user, password);
		try {
			writer.prepare("C:\\Users\\ZBARTOSZ\\workspace_db\\torepublic_code\\torepublicpasscrack\\data.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getAllPossibleWords() {
		try {
			List<User> listOfUser = database.getListOfUser();
			List<String> strings = new ArrayList<>();
			Consumer<User> c1 = user -> strings.add(user.getUsername());
			Consumer<User> c2 = user -> strings.add(user.getEmail().split("@", 2)[0]);
			listOfUser.parallelStream().forEach(c1.andThen(c2));
			List<String> collect = strings.parallelStream().distinct().collect(Collectors.toList());

			collect.forEach(s -> {
				if (s != null) {
					writer.write(s);
				}
			});
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println("End");
		}
	}

	public static void main(String[] args) {
		Data data = new Data();
		data.init();
		data.getAllPossibleWords();
	}

}
