package com.tydic.sso.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.sso.dao.DbpRemoteDao;
import com.tydic.sso.dao.DbpRemoteDaoException;
import com.tydic.sso.service.base.RemoteLog;

public class DbpRemoteLogThread extends Thread {
	private static final Log log = LogFactory.getLog(DbpSysLogThread.class);
	
	private Executor woorkPool;
	
	private BlockingQueue<RemoteLog> queue;
	
	private DbpRemoteDao dbpDao;
	
	public void setDbpDao(DbpRemoteDao dbpDao) {
		this.dbpDao = dbpDao;
	}

	public BlockingQueue<RemoteLog> getQueue(){
		return this.queue;
	}
	
	public DbpRemoteLogThread(int poolSize,int queueSize){
        log.debug("初始化线程池，线程池大小：" + poolSize);
        woorkPool = Executors.newFixedThreadPool(poolSize);
        log.debug("初始化队列，队列大小：" + queueSize);
        queue = new ArrayBlockingQueue<RemoteLog>(queueSize);
	}
	
	public void run(){
		RemoteLog dbplog = null;
		while (!Thread.currentThread().isInterrupted()){
			try {
				dbplog = queue.take();
				log.debug("队列里面附件数量:" + queue.size() + ":log" + log);
				SysWork work = new SysWork(dbplog);
				woorkPool.execute(work);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}
	
	private class SysWork implements Runnable{

		RemoteLog log;
		
		public SysWork(RemoteLog _log){
			log = _log;
		}
		
		public void run() {
			try {
				//日志入库
				dbpDao.logRemoteSys(log);
			} catch (DbpRemoteDaoException e) {
				DbpRemoteLogThread.log.error(e);
			}
		}
		
	}
}
