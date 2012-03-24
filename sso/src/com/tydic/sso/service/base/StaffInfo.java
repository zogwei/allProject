package com.tydic.sso.service.base;

import java.util.Date;
import java.util.List;

/**
 * 用户信息
 * @author houdc
 *
 */
public interface StaffInfo extends java.io.Serializable{

	/**
	 * 取用户标识
	 * @return
	 */
	public long getStaffId();
	/**
	 * 取用户姓名
	 * @return
	 */
	public String getStaffName();
	
	/**
	 * 取登陆账号
	 * @return
	 */
	public String getStaffAcct();
	
	/**
	 * 取用户地址
	 * @return
	 */
	public String getStaffAddr();
	/**
	 * 取用户email
	 * @return
	 */
	public String getEmail();
	/**
	 * 取移动电话
	 * @return
	 */
	public String getMobile();
	/**
	 * 取电话
	 * @return
	 */
	public String getTeleNo();
	/**
	 * 取员工类型
	 * @return
	 */
	public String getStaffType();
	/**
	 * 取身份证号
	 * @return
	 */
	public String getCerNumb();
	/**
	 * 取本地网
	 * @return
	 */
	public String getLatnId();
	/**
	 * 取员工工号
	 * @return
	 */
	public String getStaffCode();
	/**
	 * 取创建/修改日期
	 * @return
	 */
	public Date getCrtDate();
	/**
	 * 取认证IP/IP段
	 * @return
	 */
	public String getIpAddr();
	/**
	 * 取Mac地址
	 * @return
	 */
	public String getMacAddr();
	/**
	 * 取员工级别
	 * @return
	 */
	public int getStaffLevel();
	/**
	 * 取排序序号
	 * @return
	 */
	public long getOrderNumb();
	/**
	 * 取员工角色列表
	 * @return
	 */
	public List<StaffRole> getRoleList();
	/**
	 * 取组织机构
	 * @return
	 */
	public List<InterOrg> getOrgs();
	
	/**
	 * 取数据权限
	 * @return
	 */
	public List<Long> getDataPrivilege();
	
	/**
	 * 客户群:0全部 1公客部 2政企部
	 * @return
	 */
	public int getDepType();
	/**
	 * 取用户区域
	 * @return
	 */
	public String getAreaId();
	/**
	 * 取用户区域级别
	 * @return
	 */
	public String getAreaLevel();

    /**
     * CRM工号
     * @return
     */
    public String getCrmAcct();
}
