package com.tydic.sso.dao;

import java.util.List;

import com.tydic.sso.service.base.DbpSysLog;
import com.tydic.sso.service.base.InterOrg;
import com.tydic.sso.service.base.Privilege;
import com.tydic.sso.service.base.RemoteLog;
import com.tydic.sso.service.base.StaffInfo;

public interface DbpRemoteDao {
	/**
	 * 判断请求的应用是否为接入系统
	 * @param appId
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public boolean checkApp(String appId) throws DbpRemoteDaoException;
	/**
	 * 按员工标识查找员工
	 * @param staffId
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public StaffInfo findStaff(long staffId,String appId) throws DbpRemoteDaoException;
	/**
	 * 按员工账户查找员工
	 * @param staffAcct
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public StaffInfo findStaff(String staffAcct,String appId) throws DbpRemoteDaoException;
	/**
	 * 按组织机构标识查找组织机构
	 * @param orgId
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public InterOrg findInterOrg(long orgId) throws DbpRemoteDaoException;
	/**
	 * 按组织机构编码查找
	 * @param orgCode
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public InterOrg findInterOrg(String orgCode) throws DbpRemoteDaoException;
	/**
	 * 按员工标识找到员工菜单
	 * @param staffId
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public List<Privilege> findStaffPrivilege(long staffId,String appId) throws DbpRemoteDaoException;
	
	/**
	 * 按员工账户查到员工菜单
	 * @param staffAcct
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public List<Privilege> findStaffPrivilege(String staffAcct,String appId) throws DbpRemoteDaoException;
	
	/**
	 * 按员工账户查找按钮权限
	 * @param staffAcct
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public List<Privilege> findButtonPrivilege(String staffAcct,String appId) throws DbpRemoteDaoException;
	
	/**
	 * 查找员工数据权限
	 * @param staffAcct
	 * @param appId
	 * @return
	 * @throws DbpRemoteDaoException
	 */
	public List<InterOrg> findStaffDataPrivilege(String staffAcct,String appId) throws DbpRemoteDaoException;
	
	/**
	 * 记录操作日志
	 * @param log
	 * @throws DbpRemoteDaoException
	 */
	public void logDbpSys(DbpSysLog log) throws DbpRemoteDaoException;
	
	/**
	 * 记录远程调用日志
	 * @param log
	 * @throws DbpRemoteDaoException
	 */
	public void logRemoteSys(RemoteLog log) throws DbpRemoteDaoException;
}
