package com.jw.ess.service.impl;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IDailySalesStatsDao;
import com.jw.ess.dao.impl.DailySalesStatsDaoTest;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.service.IDailySalesStatsService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class DailySalesStatsServiceTest {

	private static final Log logger = LogFactory.getLog(DailySalesStatsDaoTest.class);
	
	private IDailySalesStatsService dailySalesStatsService;

	@Before
	public void setUp() throws Exception
	{
		dailySalesStatsService = (IDailySalesStatsService) SpringAssisant.getBean("dailySalesStatsService");
	}

	@Test
	public void testAddDailyStats() {
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.currentTimeSecs())))));
		salesStats.setTenantId(1);
		try {
			dailySalesStatsService.addDailyStats(salesStats);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddDailyStats");
		}
		
	}

	@Test
	public void testGetStatss() {
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.currentTimeSecs());
		salesStats.setTenantId(1);         
		try {
			System.out.println(dailySalesStatsService.getStatss(2,0,DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.nextDayTimeSecs(DateUtil.currentTimeSecs()))))));
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testGetStatss");
		}
	}

	@Test
	public void testSubSalesAmount() {
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(1);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.currentTimeSecs());
		salesStats.setTenantId(1);
		try {
			dailySalesStatsService.subSalesAmount(2, DateUtil.currentTimeSecs(), 1000);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testSubSalesAmount");
		}
	}

}
