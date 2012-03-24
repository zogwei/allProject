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

import java.security.MessageDigest;

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
public class MD5Utils {
	
	/**
	 * 
	 * @param s
	 * @return
	 * MD5
	 */
	public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}

