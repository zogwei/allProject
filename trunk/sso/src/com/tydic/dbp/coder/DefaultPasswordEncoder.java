/*
 * Copyrights © 2009，Tianyuan DIC Computer Co., Ltd. 数据大门户  All rights reserved. 
 * See license distributed with this available online at 
 *
 *      http://www.tydic.com/en/html/product/default.aspx
 *
 * Address: 3/F,T3 Building, South 7th Road, South Area, Hi-tech Industrial park, Shenzhen, P.R.C.
 * Email: webmaster@tydic.com　
 * Tel: +86 755 26745688 
 */
package com.tydic.dbp.coder;

import org.jasig.cas.authentication.handler.PasswordEncoder;


public class DefaultPasswordEncoder implements PasswordEncoder {

	public DefaultPasswordEncoder()
	{
		
	}
	
	/**
	 * 
	 *  @description: 
	 *   默认不加密
	 *   
	 *	@param password
	 *	@return
	 *  @see org.jasig.cas.authentication.handler.PasswordEncoder#encode(java.lang.String)
	 */
	public String encode(String password) {
		return password;
	}
}

