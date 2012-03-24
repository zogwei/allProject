package com.tydic.sso.service.base.impl;

import com.tydic.sso.service.base.Privilege;

/**
 * 权限实体对像
 * @author houdc
 *
 */
public class PrivilegeImpl implements Privilege {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -134956831531108153L;

	private long parentPrivilegeCode;
	
	private int postion;
	
	private long privilegeCode;
	
	private String privilegeDesc;
	
	private long privilegeId;
	
	private String privilegeName;
	
	private String privilegeType;
	
	private String state;
	
	private String url;
	
	private int layer;
	
	private String aliasName;
	
	private String extPropertis;
	
	public String getExtPropertis() {
		return extPropertis;
	}

	public void setExtPropertis(String extPropertis) {
		this.extPropertis = extPropertis;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public void setParentPrivilegeCode(long parentPrivilegeCode) {
		this.parentPrivilegeCode = parentPrivilegeCode;
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	public void setPrivilegeCode(long privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public void setPrivilegeDesc(String privilegeDesc) {
		this.privilegeDesc = privilegeDesc;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public long getParentPrivilegeCode() {
		return this.parentPrivilegeCode;
	}

	public int getPostion() {
		return this.postion;
	}

	public long getPrivilegeCode() {
		return this.privilegeCode;
	}

	public String getPrivilegeDesc() {
		return this.privilegeDesc;
	}

	public long getPrivilegeId() {
		return this.privilegeId;
	}

	public String getPrivilegeName() {
		return this.privilegeName;
	}

	public String getPrivilegeType() {
		return this.privilegeType;
	}

	public String getState() {
		return this.state;
	}
	public String getUrl() {
		return this.url;
	}
	
}
