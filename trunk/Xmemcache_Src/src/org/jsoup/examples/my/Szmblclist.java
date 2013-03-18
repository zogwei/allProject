package org.jsoup.examples.my;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Szmblclist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		parseFormURL();

	}
	
	private static void parseFormFile(){
		File input = new File("e:///juchao///Szmblclist.htm");
		try {
			Document doc = Jsoup.parse(input, "GBK", "http://www.cninfo.com.cn/information/sz/mb/");
			Elements links = doc.select("a[href]");
			for(Element item : links){
				System.out.println(item.html());
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
