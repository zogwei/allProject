package com.ifinance.company.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifinance.company.parse.vo.CompanyInfoVO;


public class MainCraw {

	private static final Logger LOG = LoggerFactory.getLogger(MainCraw.class);
	/**
	 * 抓取当个公司所有数据的入口
	 * @param args
	 */
	public static void main(String[] args) {
		parseData("000001");
	}
	
	public static void parseData(String stockId){
		
		//company Info
		CompanyInfoVO companyInfoVO = CompanyInfo.parseFormURL(stockId);
		LOG.debug(companyInfoVO.toString());
	}

}
