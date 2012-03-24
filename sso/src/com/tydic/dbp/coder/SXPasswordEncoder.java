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

import com.tydic.dbp.utils.ThreeDesUtils;

/**
 * @author 
 *	cameron[曹志军]
 * @module  
 *	模块名： 加密
 * @description  
 *	描述： 陕西加密实现类
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2009-12-29 9:54:28 AM
 * @version 
 *	1.0
 **/
public class SXPasswordEncoder implements PasswordEncoder {

	public SXPasswordEncoder() {
		
	}
	
	/**
	 * 
	 *  @description: 
	 *   陕西 是用的ThreeDes加密，ThreeDes也是可以解密。
	 *   
	 *	@param password
	 *	@return
	 *  @see org.jasig.cas.authentication.handler.PasswordEncoder#encode(java.lang.String)
	 */
	public String encode(String password) {
		return ThreeDesUtils.encryptMode(password);
	}

}

