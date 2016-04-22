package passcrack.db.update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import passcrack.DictionaryReader.Reader;
import passcrack.db.DatabaseConnect;
import passcrack.user.User;
import passcrack.utils.Utils;

public class UpdateDatabase {
	private final DatabaseConnect database = new DatabaseConnect();
	private final Reader reader = new Reader();
	
	public UpdateDatabase() {
		database.preprareConnectingWithDatabase(Utils.COM_MYSQL_JDBC_DRIVER, Utils.JDBC_MYSQL_LOCALHOST_DATABASE, Utils.user, Utils.password);
	}
	
	public void execute() throws IOException{
		Files.walk(Paths.get(Utils.WYNIK_PATH)).forEach(filePath -> {
			
			if (Files.isRegularFile(filePath)) {
		    	try {
		    		System.out.println(filePath);
		        	reader.prepare(filePath.toString());
		        	reader.readLine();
		        	while (reader.ready()) {
		        		String[] data = reader.readLine().split(";",6);
						User user = new User(Integer.parseInt(data[1]), data[2], data[3], data[4], data[5]);
						String password = data[0];
						System.out.println(Integer.parseInt(data[1])+" "+data[2]+" "+ data[3]+" "+data[4]+" "+data[5]+" "+password);
						//database.updateUserData(user, password);
					}
		        } catch (Exception e) {
					e.printStackTrace();
				}
		    }
		});
	}
	
	public static void main(String []args){
		UpdateDatabase updateDatabase = new UpdateDatabase();
		try {
			updateDatabase.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
