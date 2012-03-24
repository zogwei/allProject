package com.tydic.sso.authenticator;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

public class NoPassWordAuthenticationHandler extends
		AbstractUsernamePasswordAuthenticationHandler {

	@Override
	protected boolean authenticateUsernamePasswordInternal(
			UsernamePasswordCredentials credentials) throws AuthenticationException {
		// TODO Auto-generated method stub
		return false;
	}

}
