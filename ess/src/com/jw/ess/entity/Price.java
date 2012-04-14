package com.jw.ess.entity;

public class Price {

	private int id;
	private int tenantId;
	private int floorId;
	
	private String tenantName;
	
	private String floorName;
	
	private float bookPrice;// 进货价
	
	private float amountPrice;// 批发价
	
	private float detailPrice;// 零售价
	
	private float sellPrice;// 建议销售价
	
	private String description;
	
	private int createdate;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	private int createdDate;// 创建日期

	private int isValid;// 是否有效，1-有效，2-无效


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getFloorId() {
		return floorId;
	}

	public void setFloorId(int floorId) {
		this.floorId = floorId;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public float getAmountPrice() {
		return amountPrice;
	}

	public void setAmountPrice(float amountPrice) {
		this.amountPrice = amountPrice;
	}

	public float getDetailPrice() {
		return detailPrice;
	}

	public void setDetailPrice(float detailPrice) {
		this.detailPrice = detailPrice;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(float sellPrice) {
		this.sellPrice = sellPrice;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

}
