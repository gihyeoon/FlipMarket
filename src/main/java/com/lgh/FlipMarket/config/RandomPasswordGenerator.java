package com.lgh.FlipMarket.config;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

	private static final String CHAR_AND_NUMBER = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";
	private static final SecureRandom RANDOM = new SecureRandom();
	
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		
		for (int i = 0; i < length; i++) {
			int idx = RANDOM.nextInt(CHAR_AND_NUMBER.length());
			sb.append(CHAR_AND_NUMBER.charAt(idx));
		}
		
		return sb.toString();
	}
	
}
