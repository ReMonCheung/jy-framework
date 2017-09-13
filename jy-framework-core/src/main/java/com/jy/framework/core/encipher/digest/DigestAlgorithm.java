package com.jy.framework.core.encipher.digest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.RandomStringGenerator;

/**
 * 摘要算法(MD5\SHA)
 * @author zhanglj
 *
 */
public class DigestAlgorithm {
	
	public static String generateSalt(String arg) {
		RandomStringGenerator generator = new RandomStringGenerator.Builder().build();
		generator.generate(20);
//		String arg0 = a.format(new Date());
		arg = StringUtils.isBlank(arg) ? RandomStringUtils
				.randomAlphanumeric(20) : arg.trim();
		return Base64.encodeBase64String(a(arg.getBytes(), arg.getBytes()));
	}
	
	public static byte[] md5(String data) {
		return DigestUtils.md5(data);
	}
	
	public static byte[] md5(InputStream data) throws IOException{
		return DigestUtils.md5(data); 
	}
	
	public static byte[] md5(String salt,String data) {
		return DigestUtils.md5(a(salt.getBytes(),data.getBytes()));
	}
	
	public static String md5Hex(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	public static String md5Hex(InputStream data) throws IOException {
		return DigestUtils.md5Hex(data);
	}
	
	public static String md5Hex(String salt,String data) {
		return DigestUtils.md5Hex(a(salt.getBytes(),data.getBytes()));
	}
	
	public static byte[] sha1(String data) {
		return DigestUtils.sha1(data);
	}
	
	public static byte[] sha1(InputStream data) throws IOException {
		return DigestUtils.sha1(data);
	}
	
	public static byte[] sha1(String salt,String data) {
		return DigestUtils.sha1(a(salt.getBytes(),data.getBytes()));
	}
	
	public static String sha1Hex(String data) {
		return DigestUtils.sha1Hex(data);
	}
	
	public static String sha1Hex(InputStream data) throws IOException {
		return DigestUtils.sha1Hex(data);
	}
	
	public static String sha1Hex(String salt,String data) {
		return DigestUtils.sha1Hex(a(salt.getBytes(),data.getBytes()));
	}
	
	public static String sha256Hex(String data) {
		return DigestUtils.sha256Hex(data);
	}
	
	public static String sha256Hex(InputStream data) throws IOException {
		return DigestUtils.sha256Hex(data);
	}
	
	public static String sha256Hex(String salt,String data) {
		return DigestUtils.sha256Hex(a(salt.getBytes(),data.getBytes()));
	}
	
	public static String sha384Hex(String data) {
		return DigestUtils.sha384Hex(data);
	}
	
	public static String sha384Hex(InputStream data) throws IOException {
		return DigestUtils.sha384Hex(data);
	}
	
	public static String sha384Hex(String salt,String data) {
		return DigestUtils.sha384Hex(a(salt.getBytes(),data.getBytes()));
	}
	
	public static String sha512Hex(String data) {
		return DigestUtils.sha512Hex(data);
	}
	
	public static String sha512Hex(InputStream data) throws IOException {
		return DigestUtils.sha512Hex(data);
	}
	
	public static String sha512Hex(String salt,String data) {
		return DigestUtils.sha512Hex(a(salt.getBytes(),data.getBytes()));
	}
	
	private static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
		byte[] arrayOfByte = new byte[paramArrayOfByte1.length
				+ paramArrayOfByte2.length];
		int i = 0;
		int j = 0;
		for (int k = 0; k < arrayOfByte.length; k++)
			if ((i == paramArrayOfByte1.length)
					|| ((j < i) && (j < paramArrayOfByte2.length)))
				arrayOfByte[k] = paramArrayOfByte2[(j++)];
			else if ((j == paramArrayOfByte2.length) || (i <= j))
				arrayOfByte[k] = paramArrayOfByte1[(i++)];
		return arrayOfByte;
	}
	
	public static void main(String[] args) {
//		RandomStringGenerator generator = new RandomStringGenerator.Builder().build();
//		RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('A', 'z').build();
//		System.out.println(generator.generate(20));
//		UniformRandomProvider rng = RandomSource.create(...);
//		RandomStringGenerator gen = new RandomStringGenerator.Builder()
//		     .usingRandom(rng::nextInt)
//		     .build();

	}
}
