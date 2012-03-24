package com.tydic.sso.service.base.impl;

import com.tydic.sso.service.base.DbpSysLog;

public class DbpSysLogImpl implements DbpSysLog {

	private Long staffId;
	
	private Integer state;
	
	private String logInfo;
	
	private String ip;
	
	private String latnId;
	
	private Integer oprType;
	
	private String appId;
	
	private String sysLatnId;
	
	private Long oprObjId;
	
	private String staffName;
	
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public Long getOprObjId() {
		return oprObjId;
	}

	public void setOprObjId(Long oprObjId) {
		this.oprObjId = oprObjId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setLatnId(String latnId) {
		this.latnId = latnId;
	}

	public void setOprType(Integer oprType) {
		this.oprType = oprType;
	}

	public Long getStaffId() {
		return staffId;
	}

	public Integer getState() {
		return state;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public String getIp() {
		return ip;
	}

	public String getLatnId() {
		return latnId;
	}

	public Integer getOprType() {
		return oprType;
	}

	public String getAppId() {
		return appId;
	}

	public String getSysLatnId() {
		return sysLatnId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSysLatnId(String sysLatnId) {
		this.sysLatnId = sysLatnId;
	}
	

}
