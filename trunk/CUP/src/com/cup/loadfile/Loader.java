package com.cup.loadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Loader {
	private static final com.cup.log.logging.Log log=
	        com.cup.log.logging.LogFactory.getLog( Loader.class );
	
	public static void main(String[] args) {
		InputStream is= null;
		Properties properties = null;
		
		
		try {
			/**
			 * 以下通过 getResourceAsStream取得文件
			 */
			properties = new Properties();
			//不以’/'开头时默认是从此类所在的包下取资源
			is = Loader.class.getResourceAsStream("loader.properties");
			properties.load(is);
			
			properties = new Properties();
			//以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。 
			//E:\myDoc\project2\CUP\bin\loader-1.properties
			is = Loader.class.getResourceAsStream("/loader-1.properties");
			properties.load(is);
			
			//错误，无法取得文件
//			properties = new Properties();
//			//以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。 
//			is = Loader.class.getResourceAsStream("E:/myDoc/project2/CUP/bin/loader-1.properties");
//			properties.load(is);
			
			properties = new Properties();
			//默认则是从ClassPath根下获取，path不能以’/'开头，最终是由ClassLoader获取资源。 
			is = Loader.class.getClassLoader().getResourceAsStream("loader-1.properties");
			properties.load(is);
			
			//错误，无法取得文件
//			properties = new Properties();
//			//以’/'开头则是从ClassPath根下获取。其只是通过path构造一个绝对路径，最终还是由ClassLoader获取资源。 
//			is = Loader.class.getClassLoader().getResourceAsStream("E:/myDoc/project2/CUP/bin/loader-1.properties");
//			properties.load(is);
			
			
			File file = null;
			/**
			 * 以下通过文件系统取得文件
			 */
			properties = new Properties();
			//j绝对路径 
			file = new File("E:/myDoc/project2/CUP/bin/loader-1.properties");
			is = new FileInputStream(file);
			properties.load(is);
			
			properties = new Properties();
			//System.getProperty("user.dir",".") 为E:/myDoc/project2/CUP，
			file = new File(System.getProperty("user.dir",".")+"/bin","loader-1.properties");
			is = new FileInputStream(file);
			properties.load(is);
			
			/**
			 * 以下web工程特有的取得文件
			 */
			// ServletContext.getResourceAsStream(String path)：默认从WebAPP根目录下取资源，Tomcat下path是否以’/'开头无所谓，当然这和具体的容器实现有关。 
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		

	}

}
