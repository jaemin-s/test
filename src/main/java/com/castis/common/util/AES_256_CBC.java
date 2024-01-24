/*
 *     Copyright (c) 2021 chonkk@castis.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 2021-05-03
 */
package com.castis.common.util;

import com.castis.common.constants.CiResultCode;
import com.castis.common.exception.CiException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class AES_256_CBC {
	/* AES_128_CBC Key 생성 함수 */
	  public static Key getAESKey() throws Exception
	  {
	      Key keySpec;

	      String key = "DLIVE_za0728hu00";
	      byte[] keyBytes = new byte[32];
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

	  // 암호화
	  public static String encAES( String orgStr ) throws Exception
	  {

	      Key keySpec = getAESKey();
	      String iv = "0987654321765432";
	      Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
	      c.init( Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec( iv.getBytes( "UTF-8" ) ) );
	      byte[] encrypted = c.doFinal( orgStr.getBytes( "UTF-8" ) );
	      String enStr = new String( Base64.encodeBase64( encrypted ) );

	      /* URL Encoding */
	      enStr = URLEncoder.encode( enStr, "UTF-8" );

	      return enStr;
	  }

	  // 복호화
	  public static String decAES( String enStr ) throws Exception
	  {
	      Key keySpec = getAESKey();
	      Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
	      String iv = "0987654321765432";
	      c.init( Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec( iv.getBytes( "UTF-8" ) ) );

	      /* URL Decoding */
	      enStr = URLDecoder.decode( enStr, "UTF-8" );

	      byte[] byteStr = Base64.decodeBase64( enStr.getBytes( "UTF-8" ) );
	      String decStr = new String( c.doFinal( byteStr ), "UTF-8" );

	      return decStr;
	  }

	  // 복호화
	  public static String decode( String enStr , Boolean bUrlDecode) throws Exception
	  {
	      Key keySpec = getAESKey();
	      Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
	      String iv = "0987654321765432";
	      c.init( Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec( iv.getBytes( "UTF-8" ) ) );

	      /* URL Decoding */
	      if(bUrlDecode!=null && bUrlDecode) {
		      enStr = URLDecoder.decode( enStr, "UTF-8" );
	      }

	      byte[] byteStr = Base64.decodeBase64( enStr.getBytes( "UTF-8" ) );
	      String decStr = new String( c.doFinal( byteStr ), "UTF-8" );

	      return decStr;
	  }
	  
	  public static Map<String, String> decodeQueryToMap(String encQuery, Boolean bUrlDecode) throws Exception
	  {
		  String query = decode(encQuery, bUrlDecode);
	      Map<String, String> result = new HashMap<String, String>();
	      for (String param : query.split("&")) {
	          String pair[] = param.split("=");
	          if (pair.length>1) {
	              result.put(pair[0], pair[1]);
	          }else{
	              result.put(pair[0], "");
	          }
	      }
	      return result;
	  }
	  
	  public static String enc_aes_object(Object obj) throws Exception {
		  
			String jsonString = null;
			GsonBuilder gb = new GsonBuilder();
			gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Gson gson = gb.create();
			jsonString = gson.toJson(obj);
			
			if(jsonString==null || jsonString.isEmpty()) { throw new CiException(CiResultCode.GENERAL_ERROR,"Fail to ENCRYPT."); }
			
		  return encAES(jsonString);
	  }

	  // 복호화
	  public static String dec_aes_queryString( String enStr ) throws Exception
	  {
	      Key keySpec = getAESKey();
	      Cipher c = Cipher.getInstance( "AES/CBC/PKCS5Padding" );
	      String iv = "0987654321765432";
	      c.init( Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec( iv.getBytes( "UTF-8" ) ) );

	      /* URL Decoding */
	      enStr = URLDecoder.decode( enStr, "UTF-8" );

	      byte[] byteStr = Base64.decodeBase64( enStr.getBytes( "UTF-8" ) );
	      String decStr = new String( c.doFinal( byteStr ), "UTF-8" );

	      return decStr;
	  }
	  
	  @SuppressWarnings("unchecked")
	public static Map<String,?> dec_aes_map(String data) throws Exception {
		  String decStr = dec_aes_queryString(data);
		  if(decStr==null || decStr.isEmpty()) {return null;}
				  
		  Map<String,String> map = new HashMap<String,String>();
			Gson gson = new GsonBuilder().create();
			map = gson.fromJson(decStr, HashMap.class);
			
		  return map;
	  }

	public static boolean match(String encoded, String decoded) throws Exception {
		if(encoded==null || decoded==null) {
			return false;
		}
		
		return encoded.equals(encAES(decoded));
	}

	public static String substring(String encoded, int beginIndex) throws Exception {
		if(encoded==null || encoded.isEmpty()) {return "";}
		
		String decoded = decAES(encoded);
		
		return encAES(decoded.substring(beginIndex));
	}

	public static String substring(String encoded, int beginIndex, int endIndex) throws Exception{
		if(encoded==null || encoded.isEmpty()) {return "";}
		
		String decoded = decAES(encoded);
		
		return encAES(decoded.substring(beginIndex, endIndex));
	}
}
