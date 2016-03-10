package passcrack.DictionaryReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryReader {

	private BufferedReader br;

	public void prepare(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		br = new BufferedReader(fileReader);
	}

	public String readWord() throws Exception{
		if(br == null){
			throw new Exception("DictionaryReader not prepare. First use prepare(String fileName) method.");
		}
		String word = null;
		if(br.ready()){
			word = br.readLine();
		}else{
			br.close();
			System.out.println("End file");
		}
		return word;
	}
	
	public boolean ready() throws IOException{
		return br.ready();
	}
}
