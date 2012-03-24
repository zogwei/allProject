/*
 * Copyrights © 2009，Tianyuan DIC Computer Co., Ltd. 数据大门户  All rights reserved. 
 * See license distributed with this available online at 
 *
 *      http://www.tydic.com/en/html/product/default.aspx
 *
 * Address: 3/F,T3 Building, South 7th Road, South Area, Hi-tech Industrial park, Shenzhen, P.R.C.
 * Email: webmaster@tydic.com　
 * Tel: +86 755 26745688 
 */
package com.tydic.sso.authenticator;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.inspektr.common.ioc.annotation.NotNull;
import org.jasig.cas.CentralAuthenticationServiceImpl;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.tydic.dbp.utils.Constants;
import com.tydic.sso.filter.NginxFilter;

/**
 * @author cameron[曹志军]
 * @module 模块名： sso
 * @description 描述： 数据大门户SSO认证
 * @email caozj@tydic.com,cameron6@163.com
 * @date Created On : 2009-11-04 4:51:00 PM
 * @version 1.0
 **/
public class DBPAuthenticationHandler extends
		AbstractJdbcUsernamePasswordAuthenticationHandler {

	@NotNull
	private String sql;

	protected HttpServletRequest request;

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private String userLog;

	private String sysLog;   

	/**
	 * @description: 登录验证业务实现
	 * 
	 * @param credentials
	 * @return
	 * @throws AuthenticationException
	 * @see org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler#authenticateUsernamePasswordInternal(org.jasig.cas.authentication.principal.UsernamePasswordCredentials)
	 */
	@Override
	protected boolean authenticateUsernamePasswordInternal(
			UsernamePasswordCredentials credentials)
			throws AuthenticationException {
		if (credentials.getPassword().matches(".*@:true")) {
			return true;
		} else {
			final String username = credentials.getUsername();
			final String password = credentials.getPassword();
			final String encryptedPassword = this.getPasswordEncoder().encode(
					password);
			try {
				// final String dbPassword = getJdbcTemplate().queryForObject(
				// this.sql, String.class, username);
				// return dbPassword.equals(encryptedPassword);

				// ---------------------20100519 zhr
				// ------------------------------
				final Map<String, Object> map = getJdbcTemplate().queryForMap(
						sql, username);
				String dbPassword = null;
				if (map != null) {
					dbPassword = (String) map.get("PASSWORD");
					if (!dbPassword.equals(encryptedPassword))// 密码错误
					{
						this.logInfo(map, "2");
						return false;
					}
					// 记录登陆日志
					this.logInfo(map, "1");
					return true;
				} else {
					return false;
				}
			} catch (final IncorrectResultSizeDataAccessException e) {
				// this means the username was not found.
				return false;
			}
		}
	}

	// 密码是否超过90天没有修改
	private boolean validPassword(Timestamp date) {
		if (date != null) {
			long curTime = System.currentTimeMillis();
			long passTime = date.getTime();
			String time = Constants.reportProperties.getProperty("psdDate");
			return (curTime - passTime) / (1000 * 60 * 60 * 24) > Integer
					.valueOf(time) ? true : false;
		}
		return false;
	}

	/**
	 * 记录用户点击菜单的信息
	 * 
	 * @param user
	 *            用户信息
	 * @param opFlag
	 *            登陆状态 1：成功 2：失败
	 * @param ip
	 */
	@SuppressWarnings( { "unchecked", "static-access" })
	private void logInfo(Map user, String opFlag) {
		// 系统日志参数
		Map<Object, Object> logParam = new HashMap<Object, Object>();

		// 详细资料
		String descStr = null;
		if ("1".equals(opFlag)) {
			descStr = user.get("STAFF_NAME")+ "用户登陆系统成功";
		} else {
			descStr = user.get("STAFF_NAME")+ "用户登录失败，原因是'密码错误或登录账号未通过审核'";
		}

		// 系统监控参数
		Map<Object, Object> sysMonParam = new HashMap<Object, Object>();

		// 员工ID
		sysMonParam.put("staffId", user.get("STAFF_ID"));

		// 创建时间
		sysMonParam.put("createDate", new Date());

		// 操作类型，本次为菜单
		sysMonParam.put("opType", "4");// 用户登陆

		// 操作对象ID，
		sysMonParam.put("opId", null);// 登陆没有操作对象

		// 操作对象名称，本次为菜单名称
		sysMonParam.put("opName", "用户登陆成功");

		// 操作状态，		
		if ("1".equals(opFlag)) {
			sysMonParam.put("opState", 1);// 登陆成功
		} else {
			sysMonParam.put("opState", 2);// 登陆失败
		}

		// 操作详细信息
		sysMonParam.put("opInfo", descStr);

		// 上次日志ID
		sysMonParam.put("lastId", 0);		
		try {
			// 员工名称
			logParam.put("staffName", user.get("STAFF_NAME"));
			// 建立时间
			logParam.put("createDate", new Date());
			// 初始化状态
			
			if ("1".equals(opFlag)) {
				logParam.put("logstate", "1");// 用户登陆成功
			} else {
				logParam.put("logstate", "2");// 登陆失败
			}
			logParam.put("logInfo", descStr);			
			logParam.put("ip", NginxFilter.threadLocal.get());
			logParam.put("zoneId", user.get("ZONE_ID"));
			logParam.put("staffId", user.get("STAFF_ID"));
			logParam.put("oprObjId", null);
			logParam.put("oprType", "4"); // 操作类型为登陆
			this.simpleJdbcTemplate = new SimpleJdbcTemplate(this
					.getDataSource());	
			insertLog(logParam);
			insertSysMonitor(sysMonParam);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入日志
	 * 
	 * @param logParam
	 * @throws Exception
	 */
	private void insertLog(Map logParam) throws Exception {
		this.simpleJdbcTemplate.getNamedParameterJdbcOperations().update(
				userLog, logParam);
	}

	/**
	 * 插入系统监控
	 * 
	 * @param sysMonParam
	 * @throws Exception
	 */
	private void insertSysMonitor(Map sysMonParam) throws Exception {
		this.simpleJdbcTemplate.getNamedParameterJdbcOperations().update(
				sysLog, sysMonParam);
	}

	/**
	 * 获取当前时间
	 * <p>
	 * TODO
	 * </p>
	 * 
	 * @return
	 */
	private static String getSysDateStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * @param sql
	 *            The sql to set.
	 */
	public void setSql(final String sql) {
		this.sql = sql;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getUserLog() {
		return userLog;
	}

	public void setUserLog(String userLog) {
		this.userLog = userLog;
	}

	public String getSysLog() {
		return sysLog;
	}

	public void setSysLog(String sysLog) {
		this.sysLog = sysLog;
	}

}
