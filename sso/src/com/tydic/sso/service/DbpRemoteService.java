package com.tydic.sso.service;

import java.util.List;

import com.tydic.sso.service.base.DbpSysLog;
import com.tydic.sso.service.base.InterOrg;
import com.tydic.sso.service.base.Privilege;
import com.tydic.sso.service.base.StaffInfo;

/**
 * 企业数据门户远程接口
 * @author houdc
 *
 */
public interface DbpRemoteService {
	
	/**
	 * 按用户ID取用户信息
	 * @param staffId
	 * @param appId
	 * @return
	 */
	public StaffInfo getStaffById(long staffId,String appId) throws DbpRemoteException;
	/**
	 * 按登陆账户取用户信息
	 * @param acct
	 * @param appId
	 * @return
	 */
	public StaffInfo getStaffByAcct(String acct,String appId) throws DbpRemoteException;
	
	/**
	 * 根据用户账户取权限信息
	 * @param acct
	 * @param appId
	 * @return
	 */
	public List<Privilege> getStaffPrivilegeByAcct(String acct,String appId) throws DbpRemoteException;
	
	/**
	 * 根据用户标识取权限信息
	 * @param staffId
	 * @param appId
	 * @return
	 */
	public List<Privilege> getStaffPrivilegeById(long staffId,String appId) throws DbpRemoteException;
	
	/**
	 * 取用按钮权限
	 * @param acct
	 * @param appId
	 * @return
	 */
	public List<Privilege> getButtonPrivilege(String acct,String appId) throws DbpRemoteException;
	
	/**
	 * 根据组织机构结点取对应组织机构的信息
	 * @param orgId
	 * @param appId
	 * @return
	 */
	public InterOrg getInterOrgById(long orgId,String appId) throws DbpRemoteException;
	/**
	 * 根据组织机构编码取组织机构信息
	 * @param code
	 * @param appId
	 * @return
	 */
	public InterOrg getInterOrgByCode(String code,String appId) throws DbpRemoteException;
	
	/**
	 * 根据员工账户对应系统的数据权限
	 * @param code
	 * @param appId
	 * @return
	 * @throws DbpRemoteException
	 */
	public List<InterOrg> getDataPrivilegeByStaffAcct(String code,String appId) throws DbpRemoteException;
	
	/**
	 * 记录系统日志  写放成功返回 大于0的数，失败返回-1
	 * @param log
	 * @return
	 */
	public int log(DbpSysLog log,String appId) throws DbpRemoteException;
	
}
