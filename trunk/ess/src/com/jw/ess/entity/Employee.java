package com.jw.ess.entity;

import com.jw.ess.util.DateUtil;

/**
 * 员工实体类
 * 
 * @author j&w
 */
public class Employee
{
	private int id;

	private String account;// 登录账号

	private String password;// 登录密码

	private String name;// 姓名

	private int sex;// 性别，1-男，2-女

	private String phone;// 电话

	private String cardNo;// 证件号码

	private String address;// 地址

	private int state;// 状态，2-离职，1-在职

	private int category;// 类别，3-销售人员，2-销售经理，1-管理员

	private int isValid;// 是否有效，1-有效，2-无效

	private int createdDate;// 创建日期

	private String desc;// 备注

	private int tenantId;// 租户ID
	

	public String getStrCreatedDate() {
		
		return DateUtil.transformString(createdDate, DateUtil.INPUT_DATE_FORMAT);
	}

	public int getTenantId()
	{
		return tenantId;
	}

	public void setTenantId(int tenantId)
	{
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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getCardNo()
	{
		return cardNo;
	}

	public void setCardNo(String cardNo)
	{
		this.cardNo = cardNo;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public int getCategory()
	{
		return category;
	}

	public void setCategory(int category)
	{
		this.category = category;
	}

	public int getIsValid()
	{
		return isValid;
	}

	public void setIsValid(int isValid)
	{
		this.isValid = isValid;
	}

	public int getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(int createdDate)
	{
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

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "[account=" + account + ", address=" + address
				+ ", cardNo=" + cardNo + ", category=" + category
				+ ", createdDate=" + createdDate + ", desc=" + desc + ", id="
				+ id + ", isValid=" + isValid + ", name=" + name
				+ ", password=" + password + ", phone=" + phone + ", sex="
				+ sex + ", state=" + state + ", tenantId=" + tenantId + "]";
	}

}
