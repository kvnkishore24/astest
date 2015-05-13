/**
 * 
 */
package utils;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

/**
 * @author kishorekalapala
 * @project astest 
 * @class Base.java
 */
public class Base {
	public static String API_URL = "http://localhost/alphastreet/api/public/api/1.0";
	public static String USER_NAME = "kishorekalapala@digital-nirvana.com";
	public static String PASS_WORD = "123456";
	
	public String authorizationHeader(final String username, final String password) {
		final String auth = username + ":" + password;
		final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
		final String authHeader = "Basic " + new String(encodedAuth);
		return authHeader;
	}

}
