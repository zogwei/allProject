package com.jw.ess.dao;
import java.util.List;
import java.util.Map;

import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.ex.EssException;
public interface IMonthlySalesStatsDao {
	/**
	 * 查询每天的销售统计列表
	 * */
	List<SalesStats> findMonthlyStatsBy(Map<String,Object> param) throws EssException;
	
	SalesStats findmonthlySalesby(int employeeId,int salesDate) throws EssException;
	
    void insertStats(SalesStats monthlySalesStats) throws EssException;
	
	void updateStatsBy(SalesStats monthlySalesStats) throws EssException;
	
	
}
