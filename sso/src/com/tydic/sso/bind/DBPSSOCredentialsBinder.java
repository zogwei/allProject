package com.tydic.sso.bind;

import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.web.bind.CredentialsBinder;

public class DBPSSOCredentialsBinder implements CredentialsBinder {

	public void bind(HttpServletRequest request, Credentials credentials) {
		UsernamePasswordCredentials upc = (UsernamePasswordCredentials) credentials;
		upc.setUsername(request.getParameter("username")==null?"":request.getParameter("username").trim());
		upc.setPassword(request.getParameter("password"));
	}

	public boolean supports(Class<?> clazz) {
		
		return true;
	}

}
