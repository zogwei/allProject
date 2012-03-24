package com.tydic.sso.service.base.impl;

import com.tydic.sso.service.base.StaffRole;

public class StaffRoleImpl implements StaffRole {
	
	private String latnId;
	
	private long roleId;
	
	private String roleName;
	
	public void setLatnId(String latnId) {
		this.latnId = latnId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLatnId() {
		return this.latnId;
	}

	public long getRoleId() {
		return this.roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

}
