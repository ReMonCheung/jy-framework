package com.jy.framework.core.test;

import java.io.UnsupportedEncodingException;

import com.jy.framework.core.encipher.base64.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Test {

	public static void main(String[] args) {
		String src = "base64 test 我们记得咖啡碱的";
		try {
			String encode = Base64.getEncoder().encodeToString(src.getBytes("UTF-8"));
			System.out.println(encode);
			String decode = new String(Base64.getDecoder().decode(encode));
			System.out.println(decode);
			
			encode = new BASE64Encoder().encode(src.getBytes("UTF-8"));
			System.out.println(encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
