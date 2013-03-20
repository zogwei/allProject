package com.ifinance.company.parse;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawAll {
	
	public static void getAll(){
	
		String[] temp = null;
		
		try {
			Document doc = Jsoup.connect("http://www.cninfo.com.cn/information/sz/mb/szmblclist.html").get();;
			Elements links = doc.select("a[href]");
			
			for(Element item : links){
				temp = item.html().split(" ");
				StockInfoCraw.parseData(temp[0]);
				System.out.println(item.html());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
