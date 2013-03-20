package com.ifinance.company.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifinance.company.parse.vo.DividendVo;

public class DividendParse {

	public static List<DividendVo> parseFormURL(String stockId){
		List<DividendVo> list = null;
		String url = getURL(stockId);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements items = doc.select(".zx_left > .clear").select("tr");
			if(items.size()>0){
				list = new ArrayList<DividendVo>();
				for(Element item : items){
					list.add(parseItem(item,stockId));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private static DividendVo parseItem(Element item,String stockId){
		DividendVo vo = new DividendVo();
		Elements itemid = item.select("td");
		
		int index = 0;
		vo.setStockId(stockId);
		vo.setDividendYear(itemid.get(index++).text());
		vo.setProposal(itemid.get(index++).text());
		vo.setRecordDate(itemid.get(index++).text());
		vo.setExRightDate(itemid.get(index++).text());
		vo.setListDate(itemid.get(index++).text());
		
		return vo;
	}
	
	private static String getURL(String stockId){
		return URLContantValue.DIVIDEND_URL.replace(URLContantValue.REPLACE_STRING, stockId);
	}
}