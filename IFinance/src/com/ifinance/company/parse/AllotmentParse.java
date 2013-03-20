package com.ifinance.company.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifinance.company.parse.vo.AllotmentVo;
import com.ifinance.company.parse.vo.CompanyInfoVO;

public class AllotmentParse {

	public static List<AllotmentVo> parseFormURL(String stockId){
		List<AllotmentVo> list = null;
		String url = getURL(stockId);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements items = doc.select(".zx_left > .clear").select("tr");
			if(items.size()>0){
				list = new ArrayList<AllotmentVo>();
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
	
	private static AllotmentVo parseItem(Element item,String stockId){
		AllotmentVo vo = new AllotmentVo();
		Elements itemid = item.select("td");
		
		int index = 0;
		vo.setStockId(stockId);
		vo.setAllotmentYear(itemid.get(index++).text());
		vo.setProposal(itemid.get(index++).text());
		vo.setPrice(itemid.get(index++).text());
		vo.setRecordDate(itemid.get(index++).text());
		vo.setExRightDate(itemid.get(index++).text());
		vo.setPayDate(itemid.get(index++).text());
		vo.setListDate(itemid.get(index++).text());
		
		return vo;
	}
	
	private static String getURL(String stockId){
		return URLContantValue.ALLOTMENT_URL.replace(URLContantValue.REPLACE_STRING, stockId);
	}
}