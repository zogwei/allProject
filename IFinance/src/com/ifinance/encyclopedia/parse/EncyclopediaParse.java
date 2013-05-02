package com.ifinance.encyclopedia.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifinance.company.parse.StockInfoCraw;

public class EncyclopediaParse {
	
	public static void main(String[] args) {
		getAllURL();
	}
	
	public static void getAllURL(){
		
		Elements subTypes = null;
		Elements subItems = null;
		String typeName = null;
		String subTypeName = null;
		String subTypeURL = null;
		String subTypePath = null;
		
		String typeDic = null;
		
		int typeIndex = 0;
		int subIndex = 0;
		
		//所有大类的父目录
		String typeParentPath = getParentFilePath();
		
		File input = new File("e:///temp///all.htm");
		
		try {
			Document doc = Jsoup.parse(input, "GBK", "http://www.cninfo.com.cn/information/sz/mb/");
			
//			Document doc = Jsoup.connect("http://stock.eastmoney.com/xuexiao.html").get();
			Elements types = doc.select(".c9").select(".unit");
			
			for(Element item : types){
				typeIndex++;
				subIndex = 0;
				// 可以控制 只显示一个大类
//				if(typeIndex!=8){
//					continue;
//				}
				//每个大类
				typeName = item.select("h3").html().substring(7);
				typeDic = typeParentPath+"/"+typeIndex+"_"+typeName;
				createFile(typeDic,true);
				
				//找每个小类列表页面url
				subTypes = item.select("a");
				for(Element subItem : subTypes){
					subIndex++;
					subItems = item.select("a");
					subTypeName =  subItem.html();
					subTypeURL = subItem.attr("href");
					
					//建立子类型目录
					subTypePath = typeDic +"/"+subIndex+"_" +subTypeName;
					createFile(subTypePath,true);
					
					handleSubtype(subTypeURL,subTypePath,"http://stock.eastmoney.com");
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void handleSubtype(String subtypeURL,String path,String parentURL){
		int index = 0;
		String contentName = null;
		String contentURL = null;
		String cotentURL = null;
		try {
			Document doc = Jsoup.connect(subtypeURL).get();;
			Elements items = doc.select("a");
			
			for(Element item : items){
				index++;
				contentName = index+"_"+item.html();
				contentURL = item.attr("href");
				// 如果是 期货  换 34
				cotentURL = subtypeURL.substring(0,36)+contentURL.substring(2);
				save(getContent(cotentURL),contentName,path);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String getContent(String contentURL){
		String returnStr = null;
		
		try {
			Document doc = Jsoup.connect(contentURL).get();
			Elements cotentElement = doc.select(".discuss");
			returnStr = cotentElement.html();
			System.out.println(returnStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return returnStr;
	}
	
	

	private static void save(String content,String fileName,String filePath){
		 FileOutputStream out = null;
		String path =  filePath +"/"+fileName+".txt";
		createFile(path,false);
		File file = new File(path);
		try{
			out=new FileOutputStream(file);
	         int c;
	         byte buffer[] =content.getBytes();
	             for(int i=0;i<buffer.length;i++)
	                 out.write(buffer[i]);        
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
		         out.close();
			}
			catch(Exception e){
				
			}

		}
		
	}

	private static String getParentFilePath(){
		URL path = EncyclopediaParse.class.getResource(""); 
		return path.getPath().substring(1);
	}
	
	
	//处理目录
	private static String handleDirectory(String typeParentPath,String subTypeURL){
		
		String[] tempPath = null; 
		String typePath = null;
		String subTypePath = null;
		
		
		tempPath = subTypeURL.split("/");
		int index = tempPath.length;
		typePath = typeParentPath+tempPath[1];
		subTypePath = typePath + tempPath[index-1].substring(0,tempPath[index-1].length()-5);
		//一个新的大类，需要建新的大类目录,存在时不创建 
		createFile(typePath,true);
		
		//创建 子类 目录
		createFile(subTypePath,true);
		
		return subTypePath;
	}
	
	private static void createFile(String filePath,boolean weatherDic){
		System.out.println(filePath);
		File file  = new File(filePath);
		if(file.exists()){
			return;
		}
		
		try{
			if(weatherDic){
				file.mkdir();
			}
			else{
				file.createNewFile();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	


}
