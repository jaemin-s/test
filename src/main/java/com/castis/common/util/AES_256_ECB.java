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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AES_256_ECB {
	private SecretKeySpec secretKey;

	public AES_256_ECB(String reqSecretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		//String reqSecretKey = "Hcnottrestfulapiservice20221111s";
		//바이트 배열로부터 SecretKey를 구축
		this.secretKey = new SecretKeySpec(reqSecretKey.getBytes("UTF-8"), "AES");
	}


	//AES ECB PKCS5Padding 암호화(Hex | Base64)
	public String AesECBEncode(String plainText) throws Exception {

		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

		//Cipher 객체 초기화
		c.init(Cipher.ENCRYPT_MODE, secretKey);

		//Encrpytion/Decryption
		byte[] encrpytionByte = c.doFinal(plainText.getBytes("UTF-8"));

		//Hex Encode
		//return Hex.encodeHexString(encrpytionByte);

		//Base64 Encode
		return Base64.encodeBase64String(encrpytionByte);
	}


	//AES ECB PKCS5Padding 복호화(Hex | Base64)
	public String AesECBDecode(String encodeText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");

		//Cipher 객체 초기화
		c.init(Cipher.DECRYPT_MODE, secretKey);

		//Decode Hex
		//byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());

		//Decode Base64
		byte[] decodeByte = Base64.decodeBase64(encodeText);

		return new String(c.doFinal(decodeByte), "UTF-8");
	}

	public String enc_aes_object(Object obj) throws Exception {
		String jsonString = null;
		GsonBuilder gb = new GsonBuilder();
		gb = gb.setDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		gb.disableHtmlEscaping();
		Gson gson = gb.create();
		jsonString = gson.toJson(obj);
		if(jsonString==null || jsonString.isEmpty()) { throw new CiException(CiResultCode.GENERAL_ERROR,"Fail to ENCRYPT."); }
		return AesECBEncode(jsonString);
	}

	public String dec_aes_object(String obj) throws Exception {

		if(obj==null || obj.isEmpty()) { throw new CiException(CiResultCode.GENERAL_ERROR,"Fail to ENCRYPT."); }

		return AesECBDecode(obj);
	}

	public String dec_aes(String data) throws NoSuchPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		return AesECBDecode(data);
	}

	public Map<String,String> dec_aes_map(String data) throws Exception {
		String decStr = dec_aes_object(data);
		if(decStr==null || decStr.isEmpty()) {return null;}

		Map<String,String> map = new HashMap<String,String>();
		Gson gson = new GsonBuilder().create();
		map = gson.fromJson(decStr, HashMap.class);

		return map;
	}
}
