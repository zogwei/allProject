package com.jw.ess.dao.impl;


import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IMonthlySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class MonthlySalesStatsDaoTest {
	private static final Log logger = LogFactory.getLog(MonthlySalesStatsDaoTest.class);

	private IMonthlySalesStatsDao monthlysalesStatsDao;

	@Before
	public void setUp() throws Exception
	{
		monthlysalesStatsDao = SpringAssisant.getBean(MouthlySalesStatsDao.class);
	}
	
	@Test
	public void testinsertStats()
	{
		SalesStats monthlySalesStats =new SalesStats() ;
		monthlySalesStats.setTenantId(1);
		monthlySalesStats.setEmployeeId(2);
		monthlySalesStats.setSalesAmount(2000);
		monthlySalesStats.setSalesDate(201105);
		
		try {
			monthlysalesStatsDao.insertStats(monthlySalesStats);
		} catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertCustomer");
		}
	}
	
	@Test
	public void testupdateStats()
	{
		SalesStats monthlySalesStats =new SalesStats() ;
		monthlySalesStats.setTenantId(1);
		monthlySalesStats.setEmployeeId(2);
		monthlySalesStats.setSalesAmount(3000);
		monthlySalesStats.setSalesDate(1322668800);
		
		try {
			monthlysalesStatsDao.updateStatsBy(monthlySalesStats);
		} catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertCustomer");
		}
	}
	
	@Test
	public void testfindMonthlyStatsBy()
	{
		int employeeId = 2;
		int startTime = DateUtil.transformTimeSecs("2011-01-01",DateUtil.DATE_FORMAT);
		int endTime = DateUtil.transformTimeSecs("2011-12-02",DateUtil.DATE_FORMAT);;
		System.out.println(startTime);
		System.out.println(endTime);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		param.put(ParameterMapKeys.BEGIN_DATE, startTime);
		param.put(ParameterMapKeys.END_DATE, endTime);
	
	try
	{
		List<SalesStats> salesStats =  monthlysalesStatsDao.findMonthlyStatsBy(param);
		 if(salesStats==null)
		 {
			System.out.println("没有找到相应的数据"); 
		 }
		  System.out.println("size:"+salesStats.size()+","+salesStats);
        
		logger.debug(salesStats);
		
	}
	catch (EssException e)
	{
		logger.error(e);
		fail("failed to testFindSalesStats");
	}
}
	@Test
	public void testFindSalesby() {	
		try {
			System.out.println(monthlysalesStatsDao.findmonthlySalesby(2, 1322668800));
			
			//System.out.println(list.size());
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindStatssby");
		}	
		
	}
}
