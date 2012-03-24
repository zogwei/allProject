package com.jw.ess.entity;

/**
 * 纹理实体类
 * 
 * @author j&w
 * 
 */
public class Vein
{
	private int id;

	private String name;// 纹理名称

	private int isValid;// 是否有效，1-有效，2-无效

	private String desc;// 备注

	private int tenantId;// 租户id

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

	public void setTenantId(int tenantId)
	{
		this.tenantId = tenantId;
	}

	public int getTenantId()
	{
		return tenantId;
	}

	public String toString()
	{
		return "[id=" + id + ", name=" + name + ", desc=" + desc
				+ ", tenantId=" + tenantId + ", isValid=" + isValid + "]";
	}
}
