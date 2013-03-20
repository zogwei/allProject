package com.ifinance.company.parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifinance.company.parse.vo.DividendVo;
import com.ifinance.company.parse.vo.ShareholdersVo;

public class CirculateShareholdersParse {

	public static List<ShareholdersVo> parseFormURL(String stockId){
		List<ShareholdersVo> list = null;
		String url = getURL(stockId);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements items = doc.select(".clear").select("tr");
			if(items.size()>0){
				list = new ArrayList<ShareholdersVo>();
				
				String tempYear = "";
				ShareholdersVo vo = null;
				for(Element item : items){
					int index = 0;
					vo = new ShareholdersVo();
					Elements itemid = item.select("td");
					if(itemid.size()>4){
						tempYear =itemid.get(0).text();
						index++;
					}
					vo.setStockId(stockId);
					vo.setDeadline(tempYear);
					vo.setShareholderName(itemid.get(index++).text());
					vo.setSharesNumber(itemid.get(index++).text());
					vo.setSharesRatio(itemid.get(index++).text());
					vo.setShareType(itemid.get(index++).text());
					
					list.add(vo);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	private static String getURL(String stockId){
		return URLContantValue.CIRCULATESHAREHOLDERS_URL.replace(URLContantValue.REPLACE_STRING, stockId);
	}
}