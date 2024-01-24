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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

public class CiProperties extends  Properties{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CiProperties(String path) throws Exception {
       FileInputStream fis = null;
       try {
          fis = new FileInputStream(path);
          this.load(new BufferedInputStream(fis));
       } catch (Exception e) {
    	   CiLogger.error(e, "Fail to load properties.");
       } finally {
          fis.close();
       }
    }

	public void print() {
		Set<String> set = new TreeSet<String>();
		for( Object key : keySet()) {
			set.add((String) key);
		}
		
		for( String key : set) {
			CiLogger.info("%s = %s", key, getProperty((String) key));
		}
	}

}
