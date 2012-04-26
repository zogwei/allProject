package com.jw.ess.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jw.ess.util.SessionManager;

public class SessionFilter implements Filter {

	private static final long serialVersionUID = 1L;
	
	private static final String[] passStringS  = {"js","css","jpg","jpeg","gif","bmp","png","zip","rar","xls","xlsx","html","jsp"};

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
		FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		String url = request.getRequestURI();
		// client or web login
		if (isClientRequestUri(url) || isWebLoginUri(url) ||isStaticRequest (request) ) {
			arg2.doFilter(arg0, arg1);
			return;
		}
		// other
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(true);
		Object user = SessionManager.getEmployeeFrom(session);

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login?urlValue=toLogin");
			return;
		}
		arg2.doFilter(arg0, arg1);
		return;
	}
	
	private boolean isStaticRequest(HttpServletRequest arg0){
		boolean returnValue = false;
		String url = arg0.getRequestURL().toString();
		String uri = arg0.getRequestURI().toString();
		
		int num = passStringS.length;
		for(int i = 0 ; i< num ;i++)
		{
			if(url.endsWith(passStringS[i]))
			{
				returnValue = true;
				break;
			}
		}
		
		return returnValue;
	}

	private boolean isClientRequestUri(String uri) {
		if (uri != null && !uri.equals("")
				&& (uri.indexOf("/c/") < 0 && uri.indexOf("/c/") < 0)) {
			return false;
		}
		return true;
	}

	private boolean isWebLoginUri(String uri) {
		if (uri != null && !uri.equals("")
				&& (uri.indexOf("Login") < 0 && uri.indexOf("login") < 0)) {
			return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}