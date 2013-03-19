package com.ifinance.company.parse.example;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Allotment {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseFormFile();

	}
	
	private static void parseFormFile(){
		File input = new File("e:///juchao///Allotment.htm");
		try {
			Document doc = Jsoup.parse(input, "GBK", "http://www.cninfo.com.cn/information/sz/mb/");
			Elements itemsNames = doc.select(".zx_left > .clear").select("tr");
			
			for(Element item : itemsNames){
				parseItem(item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void parseItem(Element item){
		Elements itemid = item.select("td");
		System.out.print(itemid.get(0).text()+"\t\t");
		System.out.print(itemid.get(1).text()+"\t\t\t\t\t\t\t");
		System.out.print(itemid.get(2).text()+"\t\t");
		System.out.print(itemid.get(3).text()+"\t\t");
		System.out.print(itemid.get(4).text()+"\t\t");
		System.out.print(itemid.get(5).text()+"\t\t");
		System.out.println(itemid.get(6).text()+"\t\t");
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