package com.ifinance.company.parse.example;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CompanyInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseFormFile();

	}
	
	private static void parseFormFile(){
		File input = new File("e:///juchao///CompanyInfo.htm");
		try {
			Document doc = Jsoup.parse(input, "GBK", "http://www.cninfo.com.cn/information/sz/mb/");
			Elements itemsNames = doc.select(".zx_data");
			Elements itemsValues = doc.select(".zx_data2");
			int i = -1;
			for(Element item : itemsNames){
				i++;
				System.out.println(item.html()+itemsValues.get(i).html());
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void parseFormURL(){
		try {
			Document doc = Jsoup.connect("http://www.cninfo.com.cn/information/sz/mb/szmblclist.html").get();;
			Elements links = doc.select("a[href]");
			for(Element item : links){
				System.out.println(item.html());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
