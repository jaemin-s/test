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
 * 2021-05-13
 */
package com.castis.common.ssl;

import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class CiX509TrustManager implements X509TrustManager{
	private X509TrustManager standardTrustManager = null;
	private boolean verifyCertificate = false;

	public CiX509TrustManager() {
		super();
	}

	public CiX509TrustManager(X509TrustManager standardTrustManager, boolean verifyCertificate) {
		super();
		this.standardTrustManager = standardTrustManager;
		this.verifyCertificate = verifyCertificate;
	}
	
	public CiX509TrustManager(Boolean verifyCertificate, String cacertsPath, String password) throws Exception{
		super();
		
		if(verifyCertificate==null) {this.verifyCertificate = false;}
		else {this.verifyCertificate = verifyCertificate.booleanValue();}
		
        if(this.verifyCertificate==false) {return;}
        
        FileInputStream cacerts_FileStream = new FileInputStream(new File(cacertsPath));
        
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        
        if(cacertsPath.endsWith(".crt") || cacertsPath.endsWith(".pem")) {
        	CertificateFactory cf = CertificateFactory.getInstance("X.509");
        	X509Certificate caCert = (X509Certificate)cf.generateCertificate(cacerts_FileStream);
        	keystore.load(null); // You don't need the KeyStore instance to come from a file.
        	keystore.setCertificateEntry("caCert", caCert);
        }else {
            keystore.load(cacerts_FileStream, password!=null ? password.toCharArray() : "changeit".toCharArray());
        }
        
        cacerts_FileStream.close();

        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keystore);
        javax.net.ssl.TrustManager trustmanagers[] = factory.getTrustManagers();
        if(trustmanagers.length == 0) { throw new NoSuchAlgorithmException("no trust manager found"); } 
        
        this.standardTrustManager = (X509TrustManager)trustmanagers[0];
	}



	@Override
	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		
        if(!verifyCertificate) { return; } 
        if(standardTrustManager==null) {return;}
        
        standardTrustManager.checkClientTrusted(certificates, authType);
	}

	@Override
	public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		
         if(!verifyCertificate) {    return;}
         if(standardTrustManager==null) {return;}
         
        standardTrustManager.checkServerTrusted(certificates, authType);
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		
		if(standardTrustManager!=null ) {return standardTrustManager.getAcceptedIssuers();}
		
		return new X509Certificate[]{};
	}

}
