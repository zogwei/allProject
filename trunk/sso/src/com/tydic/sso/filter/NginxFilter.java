package com.tydic.sso.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NginxFilter implements Filter {

	public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //将当前server的信息记录到cookies，从而让前端能够准确的定位的会话
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        Cookie tmp = null;
        if(cookies!=null){
            for(Cookie cookie:cookies){
            	if("Managed_Server_Name".equals(cookie.getName())){
            		tmp = cookie;
            	}else{
            		continue;
            	}
            } 	
        }
        String remoteIp = request.getRemoteHost();
        threadLocal.set(remoteIp);
        
        if(tmp==null){
            String localIp = request.getLocalAddr();
            String localPort = request.getLocalPort()+"";
            Cookie managedServerName = new Cookie("Managed_Server_Name",localIp+":"+localPort);
            response.addCookie(managedServerName);        	
        }
        //server信息记录结束
        chain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
