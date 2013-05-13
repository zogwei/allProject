package com.ifinanceweb.web.test.soa.remote;

import java.util.Map;

import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;

@RemotingDestination(channels = {"dn-amf"})
public interface IRemoteTestRO {
	
	@RemotingInclude
	public String queryAllRuleConfig();
	
}
