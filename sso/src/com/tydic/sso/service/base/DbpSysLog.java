package com.tydic.sso.service.base;

/**
 * 
 * @author houdc
 *
 */
public interface DbpSysLog extends java.io.Serializable{

	/**
	 * 员工编号
	 * @return
	 */
	 Long getStaffId();
	 
	 /**
	  * 取员工姓名
	  * @return
	  */
	 String getStaffName();
	 /**
	  * 操作状态，1：成功   0：失败
	  * @return
	  */
	 Integer getState();
	 /**
	  * 日志信息
	  * @return
	  */
	 String getLogInfo();
	 
	 /**
	  * 操作人IP地址
	  * @return
	  */
	 String getIp();
	 
	 /**
	  * 操作人本地网
	  * @return
	  */
	 String getLatnId();
	 /**
	  * 操作类型 //	1:查看 2：新增  3：删除 4：登录 5：注销   6：修改  7:用户分配角色  8：密码初始化  9：禁用用户账号 10 激活用户账号
	  * @return
	  */
	 Integer getOprType();
	 /**
	  * 系统ID
	  * @return
	  */
	 String getAppId();
	 
	 /**
	  * 系统所属本地网
	  * @return
	  */
	 String getSysLatnId();
	 
	 /**
	  * 操作对像ID
	  * @return
	  */
	 Long getOprObjId();

}
