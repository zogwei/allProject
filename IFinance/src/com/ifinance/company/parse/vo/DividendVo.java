package com.ifinance.company.parse.vo;

public class DividendVo {
	
	//stock id
	private String stockId;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	//分红年度
	private String dividendYear;
	
	//分红方案
	private String proposal;
	
	//股权登记日
	private String recordDate;
	
	//除权基准日
	private String exRightDate;
	
	//红股上市日
	private String listDate;

	public String getDividendYear() {
		return dividendYear;
	}

	public void setDividendYear(String dividendYear) {
		this.dividendYear = dividendYear;
	}

	public String getProposal() {
		return proposal;
	}

	public void setProposal(String proposal) {
		this.proposal = proposal;
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

	public String getListDate() {
		return listDate;
	}

	public void setListDate(String listDate) {
		this.listDate = listDate;
	}
	
	@Override
	public String toString() {
		return "DividendVo [stockId=" + stockId + ", dividendYear="
				+ dividendYear + ", proposal=" + proposal + ", recordDate="
				+ recordDate + ", exRightDate=" + exRightDate + ", listDate="
				+ listDate + "]";
	}
}
