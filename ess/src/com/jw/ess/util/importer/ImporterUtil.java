package com.jw.ess.util.importer;

import java.util.ArrayList;
import java.util.List;

public class ImporterUtil {
	
	/**
	 * @author mcli
	 * 用于匹配Excel文件类型的常量
	 * @return List 
	 * */
	public static List<String> ExcelTypes()
	{
		List<String> imageTypes = new ArrayList<String>();
		imageTypes.add("xls");
		imageTypes.add("xlsx");
		return imageTypes;
	}
	
	public static boolean isExcelFile(String fileName) {
		// 获取文件后缀名
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1,		
				fileName.length());
		suffix = suffix.toLowerCase();
		List<String> imageTypes = ExcelTypes();							
		if(imageTypes.contains(suffix))
			return true;
		return false;
	}


}
