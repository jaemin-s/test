package com.castis.pvs.security.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class GenerateUser {

	private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
	private static final String IV = "0987654321654321";

	public static void main(String[] args) throws Exception {
		String value = encryptAES("3bbtv-kt2@3bbtv.co.th", "castis");
		System.out.println(value);
	}

	// Encrypt
	private static String encryptAES(String id, String systemCode) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String str = "id=" + id + "&pwd=" + systemCode;

		Key keySpec = getAESKey();
		Cipher c = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
		c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8)));
		byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
		String enStr = new String(Base64.encodeBase64(encrypted));

		return enStr;
	}

	/*
	 * id=sysadmin&pwd=castis key=3BBTV_za0728hu00 result=1gpvV9C/ssxZUyasbKPi8+R43eG4T++HbUE/6qtpW54=
	 */
	/* AES_128_CBC Key Create function */
	private static Key getAESKey() {
		Key keySpec;

		String key = "3BBTV_za0728hu00";
		// iv = key.substring(0, 16);
		byte[] keyBytes = new byte[16];
		byte[] byteArray = key.getBytes(StandardCharsets.UTF_8);

		int len = byteArray.length;
		if (len > keyBytes.length) {
			len = keyBytes.length;
		}

		System.arraycopy(byteArray, 0, keyBytes, 0, len);
		keySpec = new SecretKeySpec(keyBytes, "AES");

		return keySpec;
	}
}
