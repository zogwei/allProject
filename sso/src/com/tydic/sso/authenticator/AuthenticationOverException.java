/**
 * Copyrights @ 2009，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：EMCD
 * All rights reserved. 
 * Filename：AuthenticationOverException.java 
 * Description：文件描述
 * version:1.1
 * user:zhr
 * History:2010-2010-3-5-下午05:24:00
 */
package com.tydic.sso.authenticator;

import org.jasig.cas.authentication.handler.AuthenticationException;

public class AuthenticationOverException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4959102407470621639L;

	public AuthenticationOverException(String code) {
		super(code);
	}

}

