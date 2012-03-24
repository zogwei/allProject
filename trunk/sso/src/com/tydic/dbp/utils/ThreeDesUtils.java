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

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;

/**
 *  @author 
 *     cameron[曹志军]
 *  @module  
 *     模块名： 加密
 *  @description  
 *     描述： ThreeDes门户密码
 *  @email 
 *     caozj@tydic.com,cameron6@163.com
 *  @date  
 *     Created On : Nov 9, 2009 6:42:36 PM
 *  @version 
 *     1.0
 **/
public class ThreeDesUtils {
	private static final String Algorithm = "DESede";   // 定义 加密算法,可用
														// DES,DESede,Blowfish
	private static final byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
		0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
		0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
		(byte) 0xE2 }; // 24字节的密钥
	
	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static String encryptMode(String Src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return Hex.encodeHexString(c1.doFinal(Src.getBytes()));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static String decryptMode(String srcStr) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keyBytes, Algorithm);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(Hex.decodeHex(srcStr.toCharArray())));
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
}