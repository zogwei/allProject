package com.jw.ess.entity;

/**
 * 入库实体类
 * 
 * @author j&w
 * 
 */
public class InStorage
{
	private int id;

	private Floor floor;// 地板编号

	private float length;// 长度

	private float width;// 宽度

	private float area;// 面积

	private int quantity;// 片数

	private float price;// 进价

	private float count;// 总价

	private String operator;// 经办人

	private int isValid;// 是否有效，1-有效，2-无效

	private int createdDate;// 创建日期

	private String desc;// 备注
	
	private int tenantId;//租户id

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}

	public float getLength()
	{
		return length;
	}

	public void setLength(float length)
	{
		this.length = length;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getArea()
	{
		return area;
	}

	public void setArea(float area)
	{
		this.area = area;
	}

	public float getPrice()
	{
		return price;
	}

	public void setPrice(float price)
	{
		this.price = price;
	}

	public float getCount()
	{
		return count;
	}

	public void setCount(float count)
	{
		this.count = count;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public int getIsValid()
	{
		return isValid;
	}

	public void setIsValid(int isValid)
	{
		this.isValid = isValid;
	}

	public int getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(int createdDate) {
		this.createdDate = createdDate;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}

}
