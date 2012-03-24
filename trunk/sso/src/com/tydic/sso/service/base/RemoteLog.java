package com.tydic.sso.service.base;

import java.util.Date;

/**
 * 远程访问日志
 * @author houdc
 *
 */
public interface RemoteLog extends java.io.Serializable{
	/**
	 * 请求应用编号
	 * @return
	 */
	public String getAppId();
	/**
	 * 取操作类型 ,请求接口方法
	 * @return
	 */
	public String getOperator();
	/**
	 * 取访问时间
	 * @return
	 */
	public Date getAccessDate();
	/**
	 * 请求对像类型
	 * @return
	 */
	public String getObjectType();
	/**
	 * 对像KEY
	 * @return
	 */
	public String getObjectKey();
	
	/**
	 * 请求结果
	 * @return
	 */
	public String getOpResult();
	 /**
	  * 异常编码
	  * @return
	  */
	public String getException();
	/**
	 * 请求系统的IP地址
	 * @return
	 */
	public String getIp();
}
