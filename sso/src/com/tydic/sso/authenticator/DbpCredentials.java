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
package com.tydic.sso.authenticator;

import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * @author 
 *	cameron[曹志军]
 * @module  
 *	模块名： 认证
 * @description  
 *	描述： TODO
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2009-12-29 9:23:16 AM
 * @version 
 *	1.0
 **/  
public class DbpCredentials extends UsernamePasswordCredentials {

	private static final long serialVersionUID = 4662039501821805666L;

	private String validated;

	public String getValidated() {
		return validated;
	}

	public void setValidated(String validated) {
		this.validated = validated;
	}

}

