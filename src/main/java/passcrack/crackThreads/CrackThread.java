package passcrack.crackThreads;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import passcrack.DictionaryReader.Reader;
import passcrack.crack.Crack;
import passcrack.utils.Utils;
import passcrack.writer.Writer;

public class CrackThread {
	private Crack crack;
	
	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		crack = new Crack();
		Reader dictionaryReader = new Reader();
		Writer writer = new Writer();
		//String dictionary = Utils.MEDIUM_BASE;
		String dictionary2 = Utils.GIGANT_BASE_PART+62;
		try {
			dictionaryReader.prepare(
					Utils.DICTIONARY_PATH + dictionary2+Utils.TXT);
//			dictionaryReader.prepare(
//					Utils.DICTIONARY_PATH + dictionary+Utils.TXT);
			//writer.prepare(Utils.WYNIK_PATH+Utils.WYNIK_FILENAME +dateFormat.format(new Date())+ "_used_dic_"+ dictionary+ Utils.CSV);
			writer.prepare(Utils.WYNIK_PATH+Utils.WYNIK_FILENAME +dateFormat.format(new Date())+ "_used_dic_"+ dictionary2+ Utils.CSV);
		} catch (IOException e) {
			e.printStackTrace();
		}
		crack.setDictionaryReader(dictionaryReader);
		crack.setWriter(writer);
	}

	public void execute() {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {
			executorService.submit(crack);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		CrackThread crackThreads = new CrackThread();
		crackThreads.init();
		crackThreads.execute();
	}
}
