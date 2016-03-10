package passcrack.crackThreads;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import passcrack.DictionaryReader.DictionaryReader;
import passcrack.crack.Crack;
import passcrack.writer.Writer;

public class CrackThread {

	private static final String WYNIK_PATH = "C:\\Users\\ZBARTOSZ\\workspace_db\\torepublic_code\\torepublicpasscrack\\wynik_";
	private static final String GIGANT_BASE_TXT = "gigant_base.txt";
	private static final String MEDIUM_BASE_TXT = "medium_base.txt";
	private static final String DATA_TXT = "data.txt";
	private static final String DIRECTORY_PATH = "C:\\Users\\ZBARTOSZ\\workspace_db\\torepublic_code\\torepublicpasscrack\\";
	private Crack crack;
	
	public void init() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		crack = new Crack();
		DictionaryReader dictionaryReader = new DictionaryReader();
		Writer writer = new Writer();
		try {
			dictionaryReader.prepare(
					DIRECTORY_PATH + DATA_TXT);
			writer.prepare(WYNIK_PATH +dateFormat.format(new Date()) + ".csv");
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
