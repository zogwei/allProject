package com.cup.sample.log;

import com.cup.log.logging.Log;
import com.cup.log.logging.LogFactory;

public class LogTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  Log log = LogFactory.getLog(LogTest.class);
		  log.error("-----log test !-----");
	}

}
