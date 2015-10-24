package com.hou.upload;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	
	private String text;
    
	public MD5() {
		// TODO Auto-generated constructor stub
	}
    
    public String getMD5(String text) throws NoSuchAlgorithmException{
        
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(text.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        
        return hashtext;
    }
}
