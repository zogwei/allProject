package com.ifinance.company.parse.vo;

public class CompanyInfoVO {

	//stock id
	private String stockId;
	
	//公司全称
	private String companyChinaName;
	
	//英文名称
	private String companyEnglishName;
	
	//注册地址
	private String registereAddress;
	
	//公司简称
	private String companyShotName;
	
	//法定代表人 
	private String legalRepresentative;
	
	//公司董秘 	
	private String secretaryBoard;
	
	//注册资本(万元)
	private String registeredCapital;
	
	//行业种类
	private String industryTypes;
	
	//邮政编码
	private String postCode;
	
	//公司电话
	private String companyTelephone;
	
	//公司传真
	private String fax;
	
	//公司网址
	private String webSite;
	
	//上市时间
	private String timeToMarket;
	
	//招股时间
	private String prospectusTime;
	
	//发行数量（万股）
	private String issuedNumber;
	
	//发行价格（元）
	private String price;
	
	//发行市盈率（倍） 
	private String earnings;
	
	//发行方式
	private String distributionMethods;
	
	//主承销商
	private String leadUnderwriter;
	
	//上市推荐人
	private String recommender;
	
	//保荐机构
	private String sponsorAgencies;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getCompanyChinaName() {
		return companyChinaName;
	}

	public void setCompanyChinaName(String companyChinaName) {
		this.companyChinaName = companyChinaName;
	}

	public String getCompanyEnglishName() {
		return companyEnglishName;
	}

	public void setCompanyEnglishName(String companyEnglishName) {
		this.companyEnglishName = companyEnglishName;
	}

	public String getRegistereAddress() {
		return registereAddress;
	}

	public void setRegistereAddress(String registereAddress) {
		this.registereAddress = registereAddress;
	}

	public String getCompanyShotName() {
		return companyShotName;
	}

	public void setCompanyShotName(String companyShotName) {
		this.companyShotName = companyShotName;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getSecretaryBoard() {
		return secretaryBoard;
	}

	public void setSecretaryBoard(String secretaryBoard) {
		this.secretaryBoard = secretaryBoard;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getIndustryTypes() {
		return industryTypes;
	}

	public void setIndustryTypes(String industryTypes) {
		this.industryTypes = industryTypes;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCompanyTelephone() {
		return companyTelephone;
	}

	public void setCompanyTelephone(String companyTelephone) {
		this.companyTelephone = companyTelephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getTimeToMarket() {
		return timeToMarket;
	}

	public void setTimeToMarket(String timeToMarket) {
		this.timeToMarket = timeToMarket;
	}

	public String getProspectusTime() {
		return prospectusTime;
	}

	public void setProspectusTime(String prospectusTime) {
		this.prospectusTime = prospectusTime;
	}

	public String getIssuedNumber() {
		return issuedNumber;
	}

	public void setIssuedNumber(String issuedNumber) {
		this.issuedNumber = issuedNumber;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}

	public String getDistributionMethods() {
		return distributionMethods;
	}

	public void setDistributionMethods(String distributionMethods) {
		this.distributionMethods = distributionMethods;
	}

	public String getLeadUnderwriter() {
		return leadUnderwriter;
	}

	public void setLeadUnderwriter(String leadUnderwriter) {
		this.leadUnderwriter = leadUnderwriter;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}

	public String getSponsorAgencies() {
		return sponsorAgencies;
	}

	public void setSponsorAgencies(String sponsorAgencies) {
		this.sponsorAgencies = sponsorAgencies;
	}

	@Override
	public String toString() {
		return "CompanyInfoVO [stockId=" + stockId + ", companyChinaName="
				+ companyChinaName + ", companyEnglishName="
				+ companyEnglishName + ", registereAddress=" + registereAddress
				+ ", companyShotName=" + companyShotName
				+ ", legalRepresentative=" + legalRepresentative
				+ ", secretaryBoard=" + secretaryBoard + ", registeredCapital="
				+ registeredCapital + ", industryTypes=" + industryTypes
				+ ", postCode=" + postCode + ", companyTelephone="
				+ companyTelephone + ", fax=" + fax + ", webSite=" + webSite
				+ ", timeToMarket=" + timeToMarket + ", prospectusTime="
				+ prospectusTime + ", issuedNumber=" + issuedNumber
				+ ", price=" + price + ", earnings=" + earnings
				+ ", distributionMethods=" + distributionMethods
				+ ", leadUnderwriter=" + leadUnderwriter + ", recommender="
				+ recommender + ", sponsorAgencies=" + sponsorAgencies + "]";
	}
	
	
}
