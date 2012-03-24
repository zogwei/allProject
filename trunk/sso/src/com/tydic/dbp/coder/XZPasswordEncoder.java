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

import com.tydic.dbp.utils.CoderUtils;

/**
 * @author 
 *	cameron[曹志军]
 * @module  
 *	模块名： 加密
 * @description  
 *	描述： 西藏版本用coder加密方式实现
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2009-12-29 9:54:38 AM
 * @version 
 *	1.0
 **/
public class XZPasswordEncoder implements PasswordEncoder {

	/**
	 * 
	 *  @description: 
	 *   西藏版本加密方式
	 *   
	 *	@param password
	 *	@return
	 *  @see org.jasig.cas.authentication.handler.PasswordEncoder#encode(java.lang.String)
	 */
	public String encode(String password) {
		return CoderUtils.encode(password);
	}
}

