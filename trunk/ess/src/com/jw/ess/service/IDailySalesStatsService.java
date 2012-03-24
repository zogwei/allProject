package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.ex.EssException;
public interface IDailySalesStatsService {
	
	/**
	 * 查询时间段内天销售统计列表
	 * */
	List<SalesStats> getStatss(int employeeId,int beginDate,int endDate) throws EssException;
	
	/**
	 * 添加某天销售统计
	 * */
	void addDailyStats(SalesStats salesStats) throws EssException;
	
	/**
	 * 退货,减少金额
	 * */
	void subSalesAmount(int employeeId,int salesDate,double refund) throws EssException;
	
}
