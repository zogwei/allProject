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
package com.tydic.dbp.utils;

/**
 *  @author 
 *     cameron[曹志军]
 *  @module  
 *     模块名： 加密
 *  @description  
 *     描述： 门户密码
 *  @email 
 *     caozj@tydic.com,cameron6@163.com
 *  @date  
 *     Created On : Nov 9, 2009 6:42:36 PM
 *  @version 
 *     1.0
 **/
public class CoderUtils {

	/**
	 * 
	 * @description: 
	 *      加密      
	 * @param password
	 * @return
	 */
	public static String encode(String password) {
		if (password == null) {
			return new String("");
		}
		byte[] pass = password.getBytes();
		for (int i = 0; i < pass.length; i++) {
			pass[i] = (byte) ((int) pass[i] ^ 13);
		}
		String newpassword = new String(pass);
		return newpassword;
	}

	/**
	 * 
	 * @description: 
	 *      解密      
	 * @param password
	 * @return
	 */
	public static String decode(String password) {
		try {
			if (password == null) {
				return new String("");
			}
			byte[] pass = password.getBytes();
			for (int i = 0; i < pass.length; i++) {
				pass[i] = (byte) ((int) pass[i] ^ 13);
			}
			String newpassword = new String(pass);
			return newpassword;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
