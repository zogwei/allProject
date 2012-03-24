package com.tydic.sso.service.base;

/**
 * 权限
 * @author houdc
 *
 */
public interface Privilege extends java.io.Serializable{
	
	/**
	 * 取权限标识
	 * @return
	 */
	public long getPrivilegeId();
	
	/**
	 * 取权限编码
	 * @return
	 */
	public long getPrivilegeCode();
	
	/**
	 * 取菜单名称
	 * @return
	 */
	public String getPrivilegeName();
	
	/**
	 * 取菜单别名
	 * @return
	 */
	public String getAliasName();
	
	/**
	 * 取菜单描述
	 * @return
	 */
	public String getPrivilegeDesc();
	
	/**
	 * 取父菜单编码
	 * @return
	 */
	public long getParentPrivilegeCode();
	
	/**
	 * 取权限类型
	 * @return
	 */
	public String getPrivilegeType();
	
	/**
	 * 取权限地址
	 * @return
	 */
	public String getUrl();
	
	/**
	 * 取权限状态
	 * @return
	 */
	public String getState();
	/**
	 * 取菜单位置
	 * @return
	 */
	public int getPostion();
	
	/**
	 * 取层级
	 * @return
	 */
	public int getLayer();
	/**
	 * 取扩展属性
	 * @return
	 */
	public String getExtPropertis();
}
