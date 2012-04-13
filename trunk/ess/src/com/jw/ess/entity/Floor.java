package com.jw.ess.entity;

import java.util.List;

/**
 * 地板实体类
 * 
 * @author j&w
 * 
 */
public class Floor
{
	

	private int id;

	private String name;// 地板名称

	private String number;// 编号

	private Supplier supplier;// 供应商

	private FloorCategory category;// 类别

	private Spec spec;// 规格

	private ColorCode colorCode;// 色号

	private Vein vein;// 纹理

	private float bookPrice;// 进货价
	
	private float amountPrice;// 批发价
	
	private float detailPrice;// 零售价
	
	private float sellPrice;// 建议销售价

	private List<PicPath> picPath;// 图片地址

	private int createdDate;// 创建日期

	private int isValid;// 是否有效，1-有效，2-无效

	private String desc;// 备注
	
	private Tenant tenant;
	
	
	
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

	public int getId()
	{
		return id;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Supplier getSupplier()
	{
		return supplier;
	}

	public void setSupplier(Supplier supplier)
	{
		this.supplier = supplier;
	}

	public FloorCategory getCategory()
	{
		return category;
	}

	public void setCategory(FloorCategory category)
	{
		this.category = category;
	}

	public Spec getSpec()
	{
		return spec;
	}

	public void setSpec(Spec spec)
	{
		this.spec = spec;
	}

	public ColorCode getColorCode()
	{
		return colorCode;
	}

	public void setColorCode(ColorCode colorCode)
	{
		this.colorCode = colorCode;
	}

	public Vein getVein()
	{
		return vein;
	}

	public void setVein(Vein vein)
	{
		this.vein = vein;
	}

	public float getSellPrice()
	{
		return sellPrice;
	}

	public void setSellPrice(float sellPrice)
	{
		this.sellPrice = sellPrice;
	}



	public int getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(int createdDate)
	{
		this.createdDate = createdDate;
	}

	public int getIsValid()
	{
		return isValid;
	}

	public void setIsValid(int isValid)
	{
		this.isValid = isValid;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public List<PicPath> getPicPath() {
		return picPath;
	}

	public void setPicPath(List<PicPath> picPath) {
		this.picPath = picPath;
	}

	@Override
	public String toString() {
		return "Floor [category=" + category + ", colorCode=" + colorCode
				+ ", createdDate=" + createdDate + ", desc=" + desc + ", id="
				+ id + ", isValid=" + isValid + ", name=" + name + ", number="
				+ number + ", picPath=" + picPath + ", sellPrice=" + sellPrice
				+ ", spec=" + spec + ", supplier=" + supplier + ", tenant="
				+ tenant + ", vein=" + vein + "]";
	}

	


}
