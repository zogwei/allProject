package com.jw.ess.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IDailySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.service.IDailySalesStatsService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Service("dailySalesStatsService")
public class DailySalesStatsService implements IDailySalesStatsService{
	
	private static final Log logger = LogFactory.getLog(DailySalesStatsService.class);
	
	@Resource(name = "dailySalesStatsDao")
	private IDailySalesStatsDao dailySalesStatsDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addDailyStats(SalesStats salesStats) throws EssException {
		
		int salesDate=DateUtil.transformDay(salesStats.getSalesDate());
		
		salesStats.setSalesDate(salesDate);
		
		SalesStats result=dailySalesStatsDao.findSalesby(salesStats.getEmployeeId()
				, salesStats.getSalesDate());
		
		if (result != null)
		{
			double income=result.getSalesAmount();
			
			income+=salesStats.getSalesAmount();
			
			modifySalesAmount(salesStats.getEmployeeId(),salesDate,income);
		}
		else{
		dailySalesStatsDao.insertStats(salesStats);
		}
	}

	@Override
	public List<SalesStats> getStatss(int employeeId, int beginDate, int endDate)
			throws EssException {
		
		beginDate=DateUtil.transformDay(beginDate);
		
		endDate=DateUtil.transformDay(endDate);
		
		Map<String,Object> param=new HashMap<String,Object>();
		
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		
		param.put(ParameterMapKeys.BEGIN_DATE, beginDate);
		
		param.put(ParameterMapKeys.END_DATE, endDate);
		
		List<SalesStats> list=dailySalesStatsDao.findStatssby(param);
		
		if(list==null||list.size()==0){
			return list;
		}
		
		Set<Integer> set=new HashSet<Integer>();
		
		for(SalesStats s:list){
			
			set.add(s.getSalesDate());
		}
		
		int minDate=beginDate;
		
		int maxDate=endDate;
		
		while((minDate=DateUtil.nextDayTimeSecs(minDate))<maxDate){
			if(!set.contains(minDate)){
				
				SalesStats stats=new SalesStats();
				
				stats.setEmployeeId(employeeId);
				
				stats.setSalesDate(minDate);
				
				list.add(stats);
			}
		}
		
		Collections.sort(list, new Comparator<SalesStats>(){
			@Override
			public int compare(SalesStats s1, SalesStats s2) {
				
				return s1.getSalesDate()-s2.getSalesDate();
			}
		});
		
		return list;
	}

	private void modifySalesAmount(int employeeId,int salesDate,double income) throws EssException {
		
		SalesStats stats=new SalesStats();
		
		stats.setEmployeeId(employeeId);
		
		stats.setSalesDate(salesDate);
		
		stats.setSalesAmount(income);
		
		dailySalesStatsDao.updateStatsBy(stats);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void subSalesAmount(int employeeId, int salesDate, double refund)
			throws EssException {
		
		salesDate=DateUtil.transformDay(salesDate);
		
		String strDateError=DateUtil.transformString(
				salesDate,DateUtil.INPUT_DATE_FORMAT);
		
		SalesStats result=dailySalesStatsDao.findSalesby(
				employeeId,salesDate);
		if(result==null){
			
			logger.error("employeeId "+employeeId+" ,DailySalesStats salesStats "
					+ strDateError
					+ " is not exists");
			throw new EssException("employeeId "+employeeId
					+" ,DailySalesStats salesStats "
					+ strDateError
					+ " is not exists",
					MessageCode.DAILY_SALES_STATS_SALESSTATS_NOT_EXISTS);
		}
		
		double income=result.getSalesAmount();
		
		if(refund>income){
			
			logger.error("modifyDailyStats refund "+ refund
					+ " is bigger than SalesAmount"+income);
			throw new EssException("modifyDailyStats refund "+ refund
					+ " is bigger than SalesAmount"+income,
					MessageCode.DAILY_SALES_STATS_SALESSTATS_REFUND_ERROR);
			
		}
		
		income-=refund;
		
		modifySalesAmount(employeeId,salesDate,income);
	}



}
