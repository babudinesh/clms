package com.Ntranga.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Mahendra Reddy
 * 
 */
public class PasswordGenerator {

	public static String generateRandom(int noChars) {
		char[] password = new char[noChars];
		int seedChar = 'A';
		int randomInt = 0;
		for (int i = 0; i < noChars; i++) {
			randomInt = (int) (Math.random() * 3);
			switch (randomInt) {
			case 0:
				seedChar = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				seedChar = '0' + (int) (Math.random() * 10);
				break;
			case 2:
				seedChar = '0' + (int) (Math.random() * 10);
				break;			
			}
			password[i] = (char) seedChar;
		}
		return new String(password);
	}

	public static String generateRandomUnique(int noChars) {
		char[] password = new char[noChars];
		int seedChar = 'A';
		int randomInt = 0;
		for (int i = 0; i < noChars; i++) {
			randomInt = (int) (Math.random() * 3);
			switch (randomInt) {
			case 0:
				seedChar = '0' + (int) (Math.random() * 10);
				break;
			case 1:
				seedChar = 'A' + (int) (Math.random() * 26);
				break;
			}
			password[i] = (char) seedChar;
		}
		return new String(password);
	}
	
	public static String md5Encryption(String password)  {
		StringBuffer sb = new StringBuffer();
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	
	        byte byteData[] = md.digest();
	
	        //convert the byte to hex format method 1
	        
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	        // System.out.println("Digest(in hex format):: " + sb.toString());
	        
	        
	        /*//convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	System.out.println("Digest(in hex format):: " + hexString.toString());*/
	        
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new String(sb.toString());
	}
	
	
	
	
	public static void main(String ...args){

		System.out.println(generateRandomUnique(6));
		System.out.println(md5Encryption("amit"));
	}
}
