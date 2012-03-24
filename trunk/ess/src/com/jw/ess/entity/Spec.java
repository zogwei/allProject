package com.jw.ess.entity;

/**
 * 规格实体类
 * 
 * @author j&w
 * 
 */
public class Spec
{
	private int id;

	private String name;// 名称

	private String desc;// 备注

	private int isValid;// 是否有效，1-有效，2-无效

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

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getIsValid()
	{
		return isValid;
	}

	public void setIsValid(int isValid)
	{
		this.isValid = isValid;
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
		return "[id=" + id + ", name=" + name + ",desc=" + desc + ", tenantId="
				+ tenantId + ", isValid=" + isValid + "]";
	}
}
