package passcrack;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.*;
import passcrack.encoder.Encoder;

public class EncoderTest {

	@Test
	public void testEncode() {
		String encode = Encoder.encode("ala");
		System.out.println(encode);
		assertEquals("c6a378510e0ec1d7809694ebf1d5579f37b1642e",encode);
	}
}
