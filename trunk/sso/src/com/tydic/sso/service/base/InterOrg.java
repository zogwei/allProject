package com.tydic.sso.service.base;

/**
 * 组织机构信息接口
 * @author houdc
 *
 */
public interface InterOrg extends java.io.Serializable{

	/**
	 * 取节点标识
	 * @return
	 */
	public long getOrgId();
	/**
	 * 取组织名称
	 * @return
	 */
	public String getOrgName();
	/**
	 * 取组织编码
	 */
	public String getOrgCode();
	/**
	 * 取组织结点描述
	 * @return
	 */
	public String getOrgDesc();
	/**
	 * 取有效状态
	 * @return
	 */
	public String getOrgState();
	/**
	 * 取本地网标识
	 * @return
	 */
	public String getOrgLatnId();
	/**
	 * 取机构类型
	 * @return
	 */
	public int getOrgType();
	/**
	 * 取组织机构简称
	 * @return
	 */
	public String getOrgShortName();
	
	/**
	 * 取排序
	 * @return
	 */
	public int getOrgOrderNumb();
	/**
	 * 取结点层级
	 * @return
	 */
	public String getOrgStrutId();
	/**
	 * 取父结点标识
	 * @return
	 */
	public long getOrgParentId();
	/**
	 * 取结点索引
	 * @return
	 */
	public String getOrgIndex();

    /**
     * 取是否包含下属节点
     * @return
     */
    public int getOrgContainChilds();
}
