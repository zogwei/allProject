package com.ifinance.company.parse;

public class URLContantValue {

	public static final String REPLACE_STRING = "{STOCK_ID}";
	
	public static final String COMPANY_INFO_URL = "http://www.cninfo.com.cn/information/brief/szmb{STOCK_ID}.html";
	public static final String SHAREHOLDERS_URL = "http://www.cninfo.com.cn/information/shareholders/{STOCK_ID}.html";
	public static final String CIRCULATESHAREHOLDERS_URL = "http://www.cninfo.com.cn/information/circulateshareholders/{STOCK_ID}.html";
	public static final String DIVIDEND_URL = "http://www.cninfo.com.cn/information/dividend/szmb{STOCK_ID}.html";
	public static final String ALLOTMENT_URL = "http://www.cninfo.com.cn/information/allotment/szmb{STOCK_ID}.html";
}
