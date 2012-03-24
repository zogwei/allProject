package com.jw.ess.entity;

import com.jw.ess.util.DateUtil;

/**
 * 租户实体类
 * 
 * @author j&w
 * 
 */
public class Tenant {
	private int id;

	private String name;// 租户名称

	private int createdDate;// 创建时间

	private int isValid;// 是否有效，1-有效，2-无效

	private int isDefault;// 是否为系统租户，1-是，2-否

	private String desc;// 备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public String toString() {
		return "[id=" + id + ", name=" + name + ", createdDate=" + createdDate + ", isValid=" + isValid
				+ ", isDefault=" + isDefault + ", desc=" + desc + "]";
	}
	
	public String getStrCreatedDate() {
		
		return DateUtil.transformString(createdDate, DateUtil.INPUT_DATE_FORMAT);
	}
}
