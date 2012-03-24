package com.jw.ess.dao.impl;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import com.jw.ess.dao.IDailySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class DailySalesStatsDaoTest {
	
	private static final Log logger = LogFactory.getLog(DailySalesStatsDaoTest.class);
	private IDailySalesStatsDao dailySalesStatsDao;

	@Before
	public void setUp() throws Exception
	{
		dailySalesStatsDao = (IDailySalesStatsDao) SpringAssisant.getBean("dailySalesStatsDao");
	}


	@Test
	public void testSetSqlSessionTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindStatssby() {
		try {
			Map<String, Object> map=new HashMap();
			map.put(ParameterMapKeys.EMPLOYEE_ID,6);
			List list=dailySalesStatsDao.findStatssby(map);
			System.out.println(list.size());
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindStatssby");
		}
	}

	@Test
	public void testInsertStats() {
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(11111);
		salesStats.setSalesDate(DateUtil.currentTimeSecs());
		salesStats.setTenantId(1);
		try {
			dailySalesStatsDao.insertStats(salesStats);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertStatssby");
		}
	}

	@Test
	public void testUpdateStatsBy() {
		
		SalesStats salesStats=new SalesStats();
		salesStats.setEmployeeId(2);
		salesStats.setSalesAmount(1802544);
		salesStats.setSalesDate(1324310400);
		salesStats.setTenantId(1);
		try {
			dailySalesStatsDao.updateStatsBy(salesStats);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testUpdateStatssby");
		}
	}

	@Test
	public void testFindSalesby() {	
		try {
			System.out.println(dailySalesStatsDao.findSalesby(1, 1325238631));
			
			//System.out.println(list.size());
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindStatssby");
		}	
		
	}

}
