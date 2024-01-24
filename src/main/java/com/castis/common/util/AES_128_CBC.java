/*
 */
package com.castis.common.util;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;

public class AES_128_CBC {

	/*
	 *  AES128-CBC 암호화 알고리즘에 사용되는 초기 백터와 암호화 키
	 */
	// 초기 백터 (16Byte 크기를 가지며, 16Byte 단위로 암호화 시, 암호화 할 총 길이가 16Byte가 되지 못 하면 뒤에 추가하는 바이트)
	final static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
	
	//AES128-CBC 암호화 알고리즘에 사용되는 키 (16자리 문자만 가능하다.)
	//final static String key = "DLIVE_za0728hu00"; // 16글자가 필요하다. (영문 1글자당 1Byte)
	//-----------------------------------------------------------------------------------
	/* AES_128_CBC Key 생성 함수 */
	  public static Key getAESKey() throws Exception
	  {
	      Key keySpec;

	      String key = "DLIVE_za0728hu00";
	      byte[] keyBytes = new byte[16];

	      byte[] b = key.getBytes( "UTF-8" );

	      int len = b.length;
	      if ( len > keyBytes.length )
	      {
	          len = keyBytes.length;
	      } else if (len < keyBytes.length) {
				byte[] tmp = new byte[keyBytes.length];
				System.arraycopy(b, 0, tmp, 0, len);	
				b = tmp;
				len = b.length;
			}

	      System.arraycopy( b, 0, keyBytes, 0, len );
	      keySpec = new SecretKeySpec( keyBytes, "AES" );

	      return keySpec;
	  }
/*
	 *  AES128 CBC 암호화 함수
	 *  128은 암호화 키 길이를 의미한다. 
	 *  128bit = 16byte (1Byte=8bit * 16 = 128)
	 */
	public static String encAES128CBC(String str, String key)
		throws UnsupportedEncodingException, NoSuchAlgorithmException,NoSuchPaddingException,
		InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
	}

		
	/*
	 * AES128 CBC 복호화 함수
	 *  128은 암호화 키 길이를 의미한다. 
	 *  128bit = 16byte (1Byte=8bit * 16 = 128)
	 */
	public static String decAES128CBC(String str, String key)
		throws UnsupportedEncodingException, NoSuchAlgorithmException,NoSuchPaddingException,
		InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
	
		byte[] textBytes = Base64.decodeBase64(str);
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

		return new String(cipher.doFinal(textBytes), "UTF-8");
	}

}
