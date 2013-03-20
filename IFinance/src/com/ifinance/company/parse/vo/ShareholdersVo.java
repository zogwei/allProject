package com.ifinance.company.parse.vo;

public class ShareholdersVo {
	
	//stock id
	private String stockId;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	//截止时间
	private String deadline;
	
	//股东名称
	private String shareholderName;
	
	//持股数量(股)
	private String sharesNumber;
	
	//持股比例（%）
	private String sharesRatio;
	
	//股份性质
	private String shareType;

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getShareholderName() {
		return shareholderName;
	}

	public void setShareholderName(String shareholderName) {
		this.shareholderName = shareholderName;
	}

	public String getSharesNumber() {
		return sharesNumber;
	}

	public void setSharesNumber(String sharesNumber) {
		this.sharesNumber = sharesNumber;
	}

	public String getSharesRatio() {
		return sharesRatio;
	}

	public void setSharesRatio(String sharesRatio) {
		this.sharesRatio = sharesRatio;
	}

	public String getShareType() {
		return shareType;
	}

	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	@Override
	public String toString() {
		return "ShareholdersVo [stockId=" + stockId + ", deadline=" + deadline
				+ ", shareholderName=" + shareholderName + ", sharesNumber="
				+ sharesNumber + ", sharesRatio=" + sharesRatio
				+ ", shareType=" + shareType + "]";
	}
	
	
}
