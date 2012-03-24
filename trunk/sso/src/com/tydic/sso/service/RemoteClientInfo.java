package com.tydic.sso.service;

import javax.servlet.http.HttpServletRequest;

public class RemoteClientInfo {

	private static ThreadLocal<HttpServletRequest> remoteInfo = new ThreadLocal<HttpServletRequest>();
	
	public static HttpServletRequest getRequest(){
		return remoteInfo.get();
	}
	
	public static void setReqeust(HttpServletRequest request){
		remoteInfo.set(request);
	}
}
