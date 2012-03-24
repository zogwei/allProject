package com.jw.ess.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供了用于获取classpath之外资源路径的方法
 * 
 * @author j&w
 * 
 */
public class PathUtil {
	// web工程根路径
	private static final String WEB_ROOT;

	// web工程下的WEB-INF路径
	private static final String WEB_INF_PATH;

	// 定义web工程下的保存网页图片的文件夹名称
	private static final String WEB_ROOT_IMG_FOLDER_NAME = "img";

	// 定义web工程下的保存地板图片的文件夹名称
	private static final String WEB_ROOT_FLOOR_IMG_FOLDER_NAME = "floor";
	
	private static Log logger = LogFactory.getLog(PathUtil.class);

	static {
		WEB_INF_PATH = chopLastSection(getClassPath());
		WEB_ROOT = chopLastSection(WEB_INF_PATH);
	}

	/**
	 * 获取web工程根路径，该路径以文件分隔符结束
	 * 
	 * @return web工程根路径
	 */
	public static String getWebRoot() {
		return WEB_ROOT;
	}

	/**
	 * 获取web工程下的WEB-INF路径，该路径以文件分隔符结束
	 * 
	 * @return web工程下的WEB-INF路径
	 */
	public static String getWebInfPath() {
		return WEB_INF_PATH;
	}

	private static String chopLastSeparator(String src) {
		if (src.endsWith(File.separator)) {
			src = src.substring(0, src.lastIndexOf(File.separator));
		}
		return src;
	}

	private static String chopLastSection(String src) {
		String temp = chopLastSeparator(src);
		return temp.substring(0, temp.lastIndexOf(File.separator) + 1);
	}

	/**
	 * 获取classpath
	 * 
	 * @return classpath
	 */
	public static String getClassPath() {
		String url = Thread.currentThread().getContextClassLoader().getResource("").toString()
				.replace("/", File.separator);
		try {
			url = URLDecoder.decode(url, Charset.defaultCharset().toString());
			String prefix = "file:".concat(File.separator);
			if (url.startsWith(prefix)) {
				url = url.substring(prefix.length());
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("The system should always have the platform default");
		}
		return url;
	}

	public static String getAbsolutePath(Class<?> clazz) {
		String className = clazz.getName();
		String parent = className.substring(0, className.lastIndexOf(".")).replace(".", File.separator);
		return getClassPath() + parent + File.separator;
	}
	
	/**
	 * 获取用于保存地板图片的绝对路径
	 * @return
	 */
	public static String getWebRootImg() {
		return getWebRoot() + WEB_ROOT_IMG_FOLDER_NAME + File.separator 
			+ WEB_ROOT_FLOOR_IMG_FOLDER_NAME + File.separator;
	}

	public static void main(String[] args) {
		System.out.println(PathUtil.getClassPath());
		System.out.println(PathUtil.getWebInfPath());
		System.out.println(PathUtil.getWebRoot());
		System.out.println(PathUtil.getWebRootImg());
	}
}
