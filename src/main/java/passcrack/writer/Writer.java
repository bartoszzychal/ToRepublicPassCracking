package passcrack.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import passcrack.user.User;

public class Writer {
	File file;
	FileWriter fw;
	BufferedWriter bw;
	
	public void write(String word, User user){
		try {
			bw.write(word+";"+user);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void prepare(String fileName) throws IOException {
		this.file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
	}
}
