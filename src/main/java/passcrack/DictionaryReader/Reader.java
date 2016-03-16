package passcrack.DictionaryReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

	private BufferedReader br;

	public void prepare(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		br = new BufferedReader(fileReader);
	}

	public String readLine() throws Exception{
		return br.readLine();
	}
	
	public boolean ready() throws IOException{
		return br.ready();
	}
	public void close() throws IOException{
		br.close();
	}
	public void skip(int skippedLines) throws IOException{
		for(int i=0;i<skippedLines&&br.ready();i++){
			br.readLine();
		}
	}
}
