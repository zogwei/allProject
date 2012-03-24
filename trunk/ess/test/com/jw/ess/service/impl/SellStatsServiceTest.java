package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.service.ISellStatsService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class SellStatsServiceTest 
{
	private static final Log logger = LogFactory
			.getLog(SellStatsServiceTest.class);

	private ISellStatsService sellStatsService;

	@Before
	public void setUp() throws Exception {
		sellStatsService = (ISellStatsService) SpringAssisant
				.getBean("sellStatsService");
	}
	
	@Test
	public void testGetPerformanceByDate()
	{
		try 
		{
			double cost = sellStatsService.getPerformanceByDate(1, "2011-12-01", "2011-12-12");
			System.out.println(cost);
		} catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetPerformanceByDate");
		}
	}

}
