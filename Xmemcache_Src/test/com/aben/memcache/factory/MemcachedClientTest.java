/* 
 * Copyrights @ 2011，MS Co., Ltd.
 * Project：           SA
 * Version:     V1.x
 * Author:      jzhong
 * Create Time：Feb 13, 2012 2:44:20 PM
 * File Name：      com.aben.memcache.factory.MemcachedClientTest.java
 */
package com.aben.memcache.factory;


import net.rubyeye.xmemcached.MemcachedClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.aben.memcache.common.XmemcachedFactory;

/**
 * Description:		N/A.
 * Author：			jzhong
 * Version:			V1.x
 * Create Time:	 	Feb 13, 2012 2:44:20 PM
 */

public class MemcachedClientTest {
	
	MemcachedClient cacheClient = null;

	@Before
	public void setUp() throws Exception {
		
		
	}

	@After
	public void tearDown() throws Exception {
//		cacheClient.t
	}
	
	@Test
	public void setTest() {
		cacheClient = XmemcachedFactory.getMemcachedClient();
		try{
			cacheClient.set("testkey", 20, (Object)"testValue");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
//	@Test
//	public void getTest() {
//		try{
//			cacheClient.set("testkey", 20, (Object)"testValue");
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//	}

}

