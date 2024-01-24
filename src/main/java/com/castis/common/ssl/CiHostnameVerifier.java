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
package com.castis.common.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CiHostnameVerifier implements HostnameVerifier{
	private boolean bVerify = false;

	public CiHostnameVerifier() {
		super();
	}

	public CiHostnameVerifier(Boolean verify) {
		super();
		
		if(verify==null) {this.bVerify = false; return;}
		
		this.setbVerify(bVerify);
	}

	@Override
	public boolean verify(String hostname, SSLSession session) {
		
		if(isbVerify()==false) {return true;}
		
		try {
			InetAddress.getByName(hostname);
		} catch (UnknownHostException e) {
			return false;
		}
		
		try {
			X509Certificate[] certs = session.getPeerCertificateChain();
			if ((certs == null) || (certs.length == 0)) { return false;}
			
			boolean dnSearchOK = false;
			for (int num = 0; num < certs.length; num++) {
				String dn = certs[num].getSubjectDN().getName();
				String cn = getCN(dn);
				boolean alternativeNameSearchValue = false;
			    if (hostname.equalsIgnoreCase(cn)) { dnSearchOK = true; }
			    else
			    {
			    	if(cn.startsWith("*.")==true) {
				    	 int index = hostname.indexOf(".");
				    	 if (index > 0 && hostname.substring(index+1).equals(cn.substring(2))) {
				    			 dnSearchOK = true; 
				    	 }
			    	}
			    	
			      if (!dnSearchOK)
			      {
			        alternativeNameSearchValue = getSubjectAlternativeNameSearch(certs[num].toString(), hostname);
			        if (alternativeNameSearchValue) {  dnSearchOK = true; }
			      }
			      
			      if (!dnSearchOK) {return false;}
			    }
			    
			    if (dnSearchOK) {  break;  }
			}
		} catch (SSLPeerUnverifiedException e) {
			return false;
		}
		
		return true;
	}

	public boolean isbVerify() {
		return bVerify;
	}

	public void setbVerify(boolean bVerify) {
		this.bVerify = bVerify;
	}
	
	private String getCN(String dn){
	    int i = 0;
	    i = dn.indexOf("CN=");
	    if(i == -1){return null;}

	    dn = dn.substring(i + 3);

	    char dncs[] = dn.toCharArray();
	    for(i = 0; i < dncs.length; i++) {  if(dncs[i] == ',' && i > 0 && dncs[i - 1] != '\\') {  break;}  }

	    return dn.substring(0, i);
	}
	private boolean getSubjectAlternativeNameSearch(String certs, String hostnameTemp)
	{
	    int npos = 0;int fpos = 0;int epos = 0;int chkDNSNamePos = 0;
	    String DNSName = "";
	    String searchDNSName = "";
	    boolean retValue = false;
	    npos = certs.indexOf("CN=");
	    
	    if(npos == -1){return retValue;}
	    
	    npos = certs.indexOf("SubjectAlternativeName");
	    
	    if(npos == -1){ return retValue;}
	    
	    fpos = certs.indexOf("[", npos);
	    epos = certs.indexOf("]", npos);
	    DNSName = certs.substring(fpos + 1, epos);
	    searchDNSName = DNSName.toLowerCase();
	    chkDNSNamePos = searchDNSName.indexOf(hostnameTemp.toLowerCase());
	    
	    if(chkDNSNamePos == -1){retValue = false;} else {    retValue = true;}
	    
	    return retValue;
	}
}
