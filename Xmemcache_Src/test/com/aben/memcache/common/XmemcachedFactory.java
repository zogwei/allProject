/* 
 * Copyrights @ 2011，MS Co., Ltd.
 * Project：           SA
 * Version:     V1.x
 * Author:      jzhong
 * Create Time：Oct 31, 2011 2:13:03 PM
 * File Name：      net.sf.ehcache.factory.XmemcachedFactory.java
 */
package com.aben.memcache.common;

import com.morningstar.srt.common.util.ConfigUtil;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * Description:		N/A.
 * Author：			jzhong
 * Version:			V1.x
 * Create Time:	 	Oct 31, 2011 2:13:03 PM
 */

public class XmemcachedFactory {

//	private static MemcachedClientBuilder  builder = null;
//	public static long OP_TIME_OUT = Long.valueOf(ConfigUtil.getString("memcached.operation.timeout"));
//	public static int STORE_LIVE_TIME_OUT = Integer.valueOf(ConfigUtil.getString("memcached.defaut.store.second"));
//	public static String servers = ConfigUtil.getString("memcached.servers");
//	public static String poolSize = ConfigUtil.getString("memcached.pool.size");
	
	private static MemcachedClientBuilder  builder = null;
	public static long OP_TIME_OUT = 8000;
	public static int STORE_LIVE_TIME_OUT = 20;
	public static String servers = "localhost:11211";
	public static String poolSize = "2";
	
	static{
		
	}
	
	public static MemcachedClient getMemcachedClient() {
		builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));  
//        builder.setCommandFactory(new BinaryCommandFactory());//use binary protocol
        if(poolSize!=null&&!"".equals(poolSize))
        {
        	builder.setConnectionPoolSize(Integer.valueOf(poolSize));
        }
        
		MemcachedClient cacheClient = null;
		try{
			cacheClient = builder.build();
			cacheClient.setMergeFactor(50); 
			cacheClient.setOpTimeout(OP_TIME_OUT);//set operation time out
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return cacheClient;
	}
}

