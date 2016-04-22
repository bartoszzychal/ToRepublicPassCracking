package passcrack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IfTimeTest {

	private long start;
	
	@Before
	public void before(){
		start = System.nanoTime();
	}
	
	@Test
	public void test() {
		int counter = 0;
		while(counter<20000000){
			counter++;
			if(counter%20000 == 0){
			}
		}
	}
	
	@After
	public void after(){
		long end = (System.nanoTime()-start)/1000000;
		System.out.println(end);
	}
	

}
