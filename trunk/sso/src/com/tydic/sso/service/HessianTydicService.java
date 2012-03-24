package com.tydic.sso.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

public class HessianTydicService extends HessianServiceExporter {
	
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!"POST".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request
					.getMethod(), new String[] { "POST" },
					"HessianServiceExporter only supports POST requests");
		}
		RemoteClientInfo.setReqeust(request);
		try {
			invoke(request.getInputStream(), response.getOutputStream());
		} catch (Throwable ex) {
			throw new NestedServletException(
					"Hessian skeleton invocation failed", ex);
		}
	}

}
