package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.ex.EssException;
public interface IMonthlySalesStatsService {
	/**
	 * 查询时间段内月销售统计列表
	 * */
	List<SalesStats> MonthlyStats(int employeeId,int beginDate,int endDate) throws EssException;
	
	/**
	 * 添加某月销售统计
	 * */
	void addMonthlyStats(SalesStats salesStats) throws EssException;
	
	/**
	 * 退货,减少金额
	 * */
	void subMonthlySalesAmount(int employeeId,int salesDate,double refund) throws EssException;
}
