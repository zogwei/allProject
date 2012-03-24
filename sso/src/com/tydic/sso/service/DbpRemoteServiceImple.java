package com.tydic.sso.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.caucho.hessian.server.HessianServlet;
import com.tydic.sso.dao.DbpRemoteDao;
import com.tydic.sso.dao.DbpRemoteDaoException;
import com.tydic.sso.service.base.DbpSysLog;
import com.tydic.sso.service.base.InterOrg;
import com.tydic.sso.service.base.Privilege;
import com.tydic.sso.service.base.StaffInfo;
import com.tydic.sso.service.base.impl.RemoteLogImpl;

/**
 * 远程接口实现类
 * 
 * @author houdc
 * 
 */
public class DbpRemoteServiceImple extends HessianServlet implements
		DbpRemoteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8512130557420253862L;

	public void setRemoteDao(DbpRemoteDao remoteDao) {
		this.remoteDao = remoteDao;
	}

	private DbpRemoteDao remoteDao;

	private DbpSysLogThread logThread;
	
	private DbpRemoteLogThread remoteLogThread;
	
	public void setRemoteLogThread(DbpRemoteLogThread _remoteLogThread) {
		this.remoteLogThread = _remoteLogThread;
	}

	public void setLogThread(DbpSysLogThread _logThread){
		logThread = _logThread;
	}
	
	private RemoteLogImpl getLogBean(String funName,String appId,Object key,String objectType){
		RemoteLogImpl rLog = new RemoteLogImpl();
		rLog.setAppId(appId);
		rLog.setObjectKey(key+"");
		rLog.setObjectType(objectType);
		rLog.setOperator(funName);
		HttpServletRequest request = RemoteClientInfo.getRequest();
		rLog.setIp(request.getRemoteAddr());
		return rLog;
	}
	
	public List<Privilege> getButtonPrivilege(String acct, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getButtonPrivilege",appId,acct,"Privilege");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(StringUtils.isEmpty(acct)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00012");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00012");
		}
		allowedApp(appId,"getButtonPrivilege");
		
		List<Privilege> list = null;
		try {
			list = remoteDao.findButtonPrivilege(acct, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00003");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00003");// 查询按钮权限失败
		}finally{
			//写日志到队列
			this.remoteLogThread.getQueue().add(rLog);
		}
		return list;
	}

	public InterOrg getInterOrgByCode(String code, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getButtonPrivilege",appId,code,"InterOrg");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(StringUtils.isEmpty(code)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00013");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00013");
		}		
		allowedApp(appId,"getInterOrgByCode");
		
		InterOrg org = null;
		try {
			org = remoteDao.findInterOrg(code);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00004");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00004");// 按组织机构编码但询组织机构失败
		}finally{
			//写日志到队列
			this.remoteLogThread.getQueue().add(rLog);
		}

		return org;
	}

	public InterOrg getInterOrgById(long orgId, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getInterOrgById",appId,orgId,"InterOrg");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(new Long(orgId) == null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00014");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00014");
		}				
		allowedApp(appId,"getInterOrgById");
		InterOrg org = null;
		try {
			org = remoteDao.findInterOrg(orgId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00005");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00005");// 按组织机构标识但询组织机构失败
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}
		return org;
	}

	public StaffInfo getStaffByAcct(String acct, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getStaffByAcct",appId,acct,"StaffInfo");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(StringUtils.isEmpty(acct)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00015");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00015");
		}			
		allowedApp(appId,"getStaffByAcct");
		StaffInfo staff = null;
		try {
			staff = remoteDao.findStaff(acct, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00006");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00006");// 按员工账户查询员工相关信息失败
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}

		return staff;
	}

	public StaffInfo getStaffById(long staffId, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getStaffById",appId,staffId,"StaffInfo");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(new Long(staffId)==null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00016");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00016");
		}			
		allowedApp(appId,"getStaffById");
		StaffInfo staff = null;
		try {
			staff = remoteDao.findStaff(staffId, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00007");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00007");// 按员工标识查询员工相关信息失败
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}

		return staff;
	}

	public List<Privilege> getStaffPrivilegeByAcct(String acct, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getStaffById",appId,acct,"StaffInfo");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(StringUtils.isEmpty(acct)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00017");	
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00017");
		}			
		allowedApp(appId,"getStaffPrivilegeByAcct");
		List<Privilege> list = null;

		try {
			list = remoteDao.findStaffPrivilege(acct, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00008");	
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00008");
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}

		return list;
	}

	public List<Privilege> getStaffPrivilegeById(long staffId, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getStaffPrivilegeById",appId,staffId,"List<Privilege>");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(new Long(staffId)==null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00018");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00018");
		}			
		allowedApp(appId,"getStaffPrivilegeById");
		List<Privilege> list = null;

		try {
			list = remoteDao.findStaffPrivilege(staffId, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00009");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00009");
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}

		return list;
	}

	public List<InterOrg> getDataPrivilegeByStaffAcct(String acct, String appId)
			throws DbpRemoteException {
		RemoteLogImpl rLog = getLogBean("getDataPrivilegeByStaffAcct",appId,acct,"List<InterOrg>");
		if(StringUtils.isEmpty(appId)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00011");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00011");
		}
		if(StringUtils.isEmpty(acct)){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00019");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00019");
		}					
		allowedApp(appId,"getDataPrivilegeByStaffAcct");
		List<InterOrg> list = null;
		
		try {
			list = remoteDao.findStaffDataPrivilege(acct, appId);
			rLog.setOpResult("1");
		} catch (DbpRemoteDaoException e) {
			e.printStackTrace();
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00010");
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00010");
		}finally{
			this.remoteLogThread.getQueue().add(rLog);
		}
		
		return list;
	}

	/**
	 * 验证对应的APPID是否存在
	 * 
	 * @param appId
	 * @throws DbpRemoteException
	 */
	private void allowedApp(String appId,String fucn) throws DbpRemoteException {
		
		boolean flag = false;
		try {
			flag = remoteDao.checkApp(appId);
		} catch (DbpRemoteDaoException e) {
			RemoteLogImpl rLog = getLogBean("getDataPrivilegeByStaffAcct",appId,null,"List<InterOrg>");
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00001");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00001");// 接口异常，请联系管理员
		}

		if (!flag) {
			RemoteLogImpl rLog = getLogBean("getDataPrivilegeByStaffAcct",appId,null,"List<InterOrg>");
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00002");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00002"); // 对应的应用不存在
		}

	}

	public int log(DbpSysLog log,String appId) throws DbpRemoteException{
		RemoteLogImpl rLog = getLogBean("log",appId,null,"List<InterOrg>");
		if(log==null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00020");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00020");
		}
		if(log.getStaffId()==null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00021");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00021");
		}
		if(log.getOprObjId()==null){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00022");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00022");
		}
		if(StringUtils.isEmpty(log.getSysLatnId())){
			rLog.setOpResult("0");
			rLog.setException("TYDIC-DBP-REMOTE-00023");
			this.remoteLogThread.getQueue().add(rLog);
			throw new DbpRemoteException("TYDIC-DBP-REMOTE-00023");
		}
		allowedApp(appId,"log");
		
		//日志消息压入队列
		this.logThread.getQueue().add(log);
		//日志消息异步处理，返回值未知，目前统一返回0 
		rLog.setOpResult("1");
		this.remoteLogThread.getQueue().add(rLog);
		return 0;
	}

}
