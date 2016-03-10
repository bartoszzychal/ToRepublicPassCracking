package passcrack.encoder;

import org.apache.commons.codec.digest.DigestUtils;

public class Encoder {
	public static String encode(String password){
		return DigestUtils.sha1Hex(password);
	}
}
