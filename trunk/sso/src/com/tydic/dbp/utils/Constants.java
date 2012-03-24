/**
 * Copyrights @ 2007，Tianyuan DIC Computer Co., Ltd.
 * 项目名称：sso
 * All rights reserved. 
 * Filename：Constants.java 
 * Description：文件描述
 * version:
 * user:zhr
 * History:
 */
package com.tydic.dbp.utils;

import java.io.InputStream;
import java.util.Properties;

public class Constants
{    
    // 加载属性文件
    public static Properties reportProperties = null;
	static {
		if(reportProperties == null) {
			try {
				Properties props = new Properties();
				InputStream is = Constants.class.getClassLoader().getResourceAsStream("configInfo.properties");
			    props.load(is);
			    reportProperties = props;				
			} catch (Exception e) {
				System.out.println("sso 加载属性文件出错： "+e);
			}
		}
	}
}
