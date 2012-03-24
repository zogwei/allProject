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

import com.jw.ess.dao.IMonthlySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.service.IMonthlySalesStatsService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
@Service("monthlySalesStatsService")
public class MonthlySalesStatsService implements IMonthlySalesStatsService {
    private static final Log logger = LogFactory.getLog(MonthlySalesStatsService.class);
	
	@Resource(name = "monthlySalesStatsDao")
	private IMonthlySalesStatsDao monthlySalesStatsDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public List<SalesStats> MonthlyStats(int employeeId, int beginDate,
			int endDate) throws EssException {
		
        beginDate=DateUtil.transformMonth(beginDate);
		
		endDate=DateUtil.transformMonth(endDate);
		
		Map<String,Object> param=new HashMap<String,Object>();
		
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		
		param.put(ParameterMapKeys.BEGIN_DATE, beginDate);
		
		param.put(ParameterMapKeys.END_DATE, endDate);
		
		List<SalesStats> list=monthlySalesStatsDao.findMonthlyStatsBy(param);
		
		if(list==null||list.size()==0){
			return list;
		}
		
		Set<Integer> set=new HashSet<Integer>();
		
		for(SalesStats s:list){
			
			set.add(s.getSalesDate());
		}
		
		int minDate=beginDate;
		
		int maxDate=endDate;
		
		while((minDate=DateUtil.nextMonthTimeSecs(minDate))<maxDate){
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

	@Override
	
	public void addMonthlyStats(SalesStats salesStats) throws EssException {
        
		int salesDate=DateUtil.transformMonth(salesStats.getSalesDate());
		
		salesStats.setSalesDate(salesDate);
		
		SalesStats result=monthlySalesStatsDao.findmonthlySalesby(salesStats.getEmployeeId()
				, salesDate);
		if (result != null)
		{
           double income=result.getSalesAmount();
			
			income+=salesStats.getSalesAmount();
			
			modifySalesAmount(salesStats.getEmployeeId(),salesDate,income);
		}
		else{
			monthlySalesStatsDao.insertStats(salesStats);
		}

	}
        private void modifySalesAmount(int employeeId,int salesDate,double income) throws EssException {
		
		SalesStats stats=new SalesStats();
		
		stats.setEmployeeId(employeeId);
		
		stats.setSalesDate(salesDate);
		
		stats.setSalesAmount(income);
		System.out.println(stats);
		monthlySalesStatsDao.updateStatsBy(stats);
		
	}

		@Override
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
		public void subMonthlySalesAmount(int employeeId, int salesDate, double refund)
				throws EssException {
			salesDate=DateUtil.transformMonth(salesDate);
			
			String strDateError=DateUtil.transformString(
					salesDate,DateUtil.DATE_FORMAT);
			
			SalesStats result=monthlySalesStatsDao.findmonthlySalesby(
					employeeId,salesDate);
			
			if(result==null){
				
				logger.error("employeeId "+employeeId+" ,DailySalesStats salesStats "
						+ strDateError
						+ " is not exists");
				throw new EssException("employeeId "+employeeId
						+" ,MonthlySalesStats salesStats "
						+ strDateError
						+ " is not exists",
						MessageCode.MONTHLY_SALES_STATS_SALESSTATS_NOT_EXISTS);
			}
			
			double income=result.getSalesAmount();
			
			if(refund>income){
				
				logger.error("modifyMonthlyStats refund "+ refund
						+ " is bigger than SalesAmount"+income);
				throw new EssException("modifyMonthlyStats refund "+ refund
						+ " is bigger than SalesAmount"+income,
						MessageCode.MONTHLY_SALES_STATS_SALESSTATS_REFUND_ERROR);
				
			}
			
			income-=refund;
			
			modifySalesAmount(employeeId,salesDate,income);
			
		}

		
			
		}

	


