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
	
	public void write(String word){
		try {
			bw.write(word);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void write_(String word){
		try {
			bw.write(word);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void flush(){
		try {
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close(){
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void prepare(String fileName) throws IOException {
		this.file = new File(fileName);
		if(!file.exists()){
			file.createNewFile();
		}else{
			throw new IOException("File exists");
		}
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);
	}
}
