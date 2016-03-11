package passcrack.data;

import java.io.IOException;

import passcrack.DictionaryReader.DictionaryReader;
import passcrack.utils.Utils;
import passcrack.writer.Writer;

public class DataSpliter {
	

	private static final int PART_20000000_WORDS = 20000000;
	private DictionaryReader dr = new DictionaryReader();
	private Writer wr = new Writer();
	
	public void splitTheBigFile() throws Exception{
		int counter = 1;
		dr.prepare(Utils.DICTIONARY_PATH+Utils.GIGANT_BASE+Utils.TXT);
		while (dr.ready()) {
			wr.prepare(Utils.DICTIONARY_PATH+Utils.GIGANT_BASE_PART+counter+Utils.TXT);
			counter=counter+1;
			for (int i = 0; i < PART_20000000_WORDS; i++) {
				wr.write_(dr.readWord());
			}
			wr.flush();
			wr.close();
		}
	}
	
	public static void main(String[] args) {
		DataSpliter dataSpliter = new DataSpliter();
		try {
			dataSpliter.splitTheBigFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
