package com.ifinance.company.parse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifinance.company.parse.vo.AllotmentVo;
import com.ifinance.company.parse.vo.CompanyInfoVO;
import com.ifinance.company.parse.vo.DividendVo;
import com.ifinance.company.parse.vo.ShareholdersVo;


public class StockInfoCraw {

	private static final Logger LOG = LoggerFactory.getLogger(StockInfoCraw.class);
	/**
	 * 抓取当个公司所有数据的入口
	 * @param args
	 */
	public static void main(String[] args) {
		parseData("000004");
	}
	
	public static void parseData(String stockId){
		
		//company Info
		CompanyInfoVO companyInfoVO = CompanyInfoParse.parseFormURL(stockId);
		LOG.debug(companyInfoVO.toString());
		
		//Shareholders
		List<ShareholdersVo> shareholderList = ShareholdersParse.parseFormURL(stockId);
		LOG.debug(shareholderList.toString());
		
		//CirculateShareholders
		List<ShareholdersVo> circulateShareholdersList = CirculateShareholdersParse.parseFormURL(stockId);
		LOG.debug(circulateShareholdersList.toString());
		
		//Dividend
		List<DividendVo> dividendList = DividendParse.parseFormURL(stockId);
		LOG.debug(dividendList.toString());
		
		//Allotment
		List<AllotmentVo> allotmentList = AllotmentParse.parseFormURL(stockId);
		LOG.debug(allotmentList.toString());
		
	}

}
