package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.impl.DailySalesStatsDaoTest;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.service.IDailySalesStatsService;
import com.jw.ess.service.IMonthlySalesStatsService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class MonthlySalesStatsServiceTest {
private static final Log logger = LogFactory.getLog(MonthlySalesStatsServiceTest.class);
	
	private IMonthlySalesStatsService monthlySalesStatsService;

	@Before
	public void setUp() throws Exception
	{
		monthlySalesStatsService = (IMonthlySalesStatsService) SpringAssisant.getBean("monthlySalesStatsService");
	}
	@Test
	public void testAddMonthlyStats() {
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.currentTimeSecs())))));
		salesStats.setTenantId(1);
		try {
			monthlySalesStatsService.addMonthlyStats(salesStats);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertStatssby");
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
			System.out.println(monthlySalesStatsService.MonthlyStats(2,0,DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.nextMonthTimeSecs(DateUtil.currentTimeSecs()))))));
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
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.currentTimeSecs());
		salesStats.setTenantId(1);
		try {
			monthlySalesStatsService.subMonthlySalesAmount(2, DateUtil.currentTimeSecs(), 200);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testSubSalesAmount");
		}
	}
}
