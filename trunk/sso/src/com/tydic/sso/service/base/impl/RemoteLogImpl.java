package com.tydic.sso.service.base.impl;

import java.util.Date;

import com.tydic.sso.service.base.RemoteLog;


public class RemoteLogImpl implements RemoteLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9210292550845094289L;

	private String appId;
	
	private String operator;
	
	private Date accessDate;
	
	private String objectType;
	
	private String objectKey;
	
	private String opResult;
	
	private String exception;
	
	private String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}

	public String getOpResult() {
		return opResult;
	}

	public void setOpResult(String opResult) {
		this.opResult = opResult;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

}
