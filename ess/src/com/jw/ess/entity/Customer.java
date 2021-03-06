package com.jw.ess.entity;

/**
 * 客户实体类
 * 
 * @author j&w
 */
public class Customer
{
	private int id;

	private String name;// 客户名称

	private String linkman;// 联系人

	private String phone;// 电话
	
		private String telNum;//客户电话

	private String address;// 地址

	private int createdDate;// 创建日期

	private int isValid;// 是否有效，1-有效，2-无效

	private String desc;// 备注

	private int tenantId;// 租户id

	private int employeeId;// 员工id

	public int getId()
	{
		return id;
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

	public String getLinkman()
	{
		return linkman;
	}

	public void setLinkman(String linkman)
	{
		this.linkman = linkman;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
		public String getTelNum() {
		return telNum;
	}

	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
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

	public int getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(int tenantId)
	{
		this.tenantId = tenantId;
	}

	public int getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(int employeeId)
	{
		this.employeeId = employeeId;
	}

	@Override
	public String toString()
	{
		return "[address=" + address + ", createdDate=" + createdDate
				+ ", desc=" + desc + ", id=" + id + ", isValid=" + isValid
				+ ", linkman=" + linkman + ", name=" + name + ", phone="
				+ phone + ", tenantId=" + tenantId + ",employeeId="
				+ employeeId + "]";
	}

}
