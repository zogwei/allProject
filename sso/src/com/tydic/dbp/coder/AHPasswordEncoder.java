
package com.tydic.dbp.coder;

import org.jasig.cas.authentication.handler.PasswordEncoder;

public class AHPasswordEncoder implements PasswordEncoder {


	public String encode(String arg0) {
		return MD5Encrypt.MD5Encode(arg0);
	}

}

