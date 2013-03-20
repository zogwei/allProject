package com.ifinance.company.parse.vo;

public class AllotmentVo {
	
	//stock id
	private String stockId;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	//配股年度
	private String allotmentYear;
	
	//配股方案
	private String proposal;
	
	//配股价
	private String price;
	
	//股权登记日
	private String recordDate;
	
	//除权基准日
	private String exRightDate;
	
	//配股交款起止日
	private String payDate;
	
	//配股可流通部分上市日
	private String listDate;

	public String getAllotmentYear() {
		return allotmentYear;
	}

	public void setAllotmentYear(String allotmentYear) {
		this.allotmentYear = allotmentYear;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getExRightDate() {
		return exRightDate;
	}

	public void setExRightDate(String exRightDate) {
		this.exRightDate = exRightDate;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}

	@Override
	public String toString() {
		return "AllotmentVo [stockId=" + stockId + ", allotmentYear="
				+ allotmentYear + ", proposal=" + proposal + ", price=" + price
				+ ", recordDate=" + recordDate + ", exRightDate=" + exRightDate
				+ ", payDate=" + payDate + ", listDate=" + listDate + "]";
	}
	
	
}
