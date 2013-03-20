package com.ifinance.company.parse;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ifinance.company.parse.vo.CompanyInfoVO;

public class CompanyInfoParse {
	
	public static CompanyInfoVO parseFormURL(String stockId){
		CompanyInfoVO vo = null;
		String url = getURL(stockId);
		try {
			Document doc = Jsoup.connect(url).get();
			Elements items = doc.select(".zx_data2");
			if(items.size()>0){
				vo = new CompanyInfoVO();
				int index = 0;
				
				vo.setStockId(stockId);
				vo.setCompanyChinaName(items.get(index++).html());
				vo.setCompanyEnglishName(items.get(index++).html());
				vo.setRegistereAddress(items.get(index++).html());
				vo.setCompanyShotName(items.get(index++).html());
				vo.setLegalRepresentative(items.get(index++).html());
				vo.setSecretaryBoard(items.get(index++).html());
				vo.setRegisteredCapital(items.get(index++).html());
				vo.setIndustryTypes(items.get(index++).html());
				vo.setPostCode(items.get(index++).html());
				vo.setCompanyTelephone(items.get(index++).html());
				vo.setFax(items.get(index++).html());
				vo.setWebSite(items.get(index++).html());
				vo.setTimeToMarket(items.get(index++).html());
				vo.setProspectusTime(items.get(index++).html());
				vo.setIssuedNumber(items.get(index++).html());
				vo.setPrice(items.get(index++).html());
				vo.setEarnings(items.get(index++).html());
				vo.setDistributionMethods(items.get(index++).html());
				vo.setLeadUnderwriter(items.get(index++).html());
				vo.setRecommender(items.get(index++).html());
				vo.setSponsorAgencies(items.get(index++).html());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	private static String getURL(String stockId){
		return URLContantValue.COMPANY_INFO_URL.replace(URLContantValue.REPLACE_STRING, stockId);
	}
}
