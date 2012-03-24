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
package com.tydic.sso.entry.action;

import org.jasig.cas.ticket.TicketException;

/**
 * @author 
 *	cameron[曹志军]
 * @email 
 *	caozj@tydic.com,cameron6@163.com
 * @date  
 *	Created On : 2010-01-21 4:30:26 PM
 * @version 
 *	1.0
 **/
public class TokenException extends TicketException{

	private static final long serialVersionUID = -4012464682894478881L;

	public TokenException(String code) {
		super(code);
	}
}

