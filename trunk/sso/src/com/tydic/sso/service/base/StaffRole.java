package com.tydic.sso.service.base;
/**
 * 角色信息接口
 * @author houdc
 *
 */
public interface StaffRole extends java.io.Serializable{

	/**
	 * 取角色ID
	 * @return
	 */
	public long getRoleId();
	/**
	 * 取角色名称
	 * @return
	 */
	public String getRoleName();
	/**
	 * 取角色本地网
	 * @return
	 */
	public String getLatnId();
	
}
