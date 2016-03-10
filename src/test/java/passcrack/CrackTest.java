package passcrack;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import passcrack.DictionaryReader.DictionaryReader;
import passcrack.crack.Crack;
import passcrack.writer.Writer;

public class CrackTest {

	private long start;

	@Before
	public void start() {
		start = System.currentTimeMillis();
	}

	@Test
	public void test() throws Exception {
		Crack crack = new Crack();
		DictionaryReader dictionaryReader = new DictionaryReader();
		dictionaryReader.prepare(
				"C:\\Users\\ZBARTOSZ\\workspace_db\\torepublic_code" + "\\torepublicpasscrack\\medium_base.txt");
		Writer writer = new Writer();
		writer.prepare("C:\\Users\\ZBARTOSZ\\workspace_db\\torepublic_code" + "\\torepublicpasscrack\\wynik.txt");
		crack.setDictionaryReader(dictionaryReader);
		crack.setWriter(writer);
		crack.decode();
	}

	@After
	public void end() {
		System.out.println(System.currentTimeMillis() - start);
	}

}
