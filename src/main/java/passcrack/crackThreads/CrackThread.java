package passcrack.crackThreads;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import passcrack.DictionaryReader.DictionaryReader;
import passcrack.crack.Crack;
import passcrack.utils.Utils;
import passcrack.writer.Writer;

public class CrackThread {
	private Crack crack;
	
	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		crack = new Crack();
		DictionaryReader dictionaryReader = new DictionaryReader();
		Writer writer = new Writer();
		try {
			dictionaryReader.prepare(
					Utils.DICTIONARY_PATH + Utils.MEDIUM_BASE+Utils.TXT);
			writer.prepare(Utils.WYNIK_PATH+Utils.WYNIK_FILENAME +dateFormat.format(new Date()) + Utils.CSV);
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
