package com.tydic.sso.service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.sso.dao.DbpRemoteDao;
import com.tydic.sso.dao.DbpRemoteDaoException;
import com.tydic.sso.service.base.DbpSysLog;

/**
 * 日志后台监控线程
 * @author houdc
 *
 */
public class DbpSysLogThread extends Thread {

	private static final Log log = LogFactory.getLog(DbpSysLogThread.class);
	
	private Executor woorkPool;
	
	private BlockingQueue<DbpSysLog> queue;
	
	private DbpRemoteDao dbpDao;
	
	public void setDbpDao(DbpRemoteDao dbpDao) {
		this.dbpDao = dbpDao;
	}

	public BlockingQueue<DbpSysLog> getQueue(){
		return this.queue;
	}
	
	public DbpSysLogThread(int poolSize,int queueSize){
        log.debug("初始化线程池，线程池大小：" + poolSize);
        woorkPool = Executors.newFixedThreadPool(poolSize);
        log.debug("初始化队列，队列大小：" + queueSize);
        queue = new ArrayBlockingQueue<DbpSysLog>(queueSize);
	}
	
	public void run(){
		DbpSysLog dbplog = null;
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

		DbpSysLog log;
		
		public SysWork(DbpSysLog _log){
			log = _log;
		}
		
		public void run() {
			try {
				//日志入库
				dbpDao.logDbpSys(log);
			} catch (DbpRemoteDaoException e) {
				DbpSysLogThread.log.error(e);
			}
		}
		
	}
	
}
