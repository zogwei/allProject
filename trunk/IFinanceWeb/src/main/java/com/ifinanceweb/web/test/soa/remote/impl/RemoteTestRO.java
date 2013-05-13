package com.ifinanceweb.web.test.soa.remote.impl;

import org.springframework.stereotype.Service;

import com.ifinanceweb.web.test.soa.remote.IRemoteTestRO;

@Service("remoteTestRO")
public class RemoteTestRO implements IRemoteTestRO {

	@Override
	public String queryAllRuleConfig() {
		return "ooooo";
	}

}
