package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.ex.EssException;
public interface IDailySalesStatsDao {
	
	/**
	 * 查询每天的销售统计列表
	 * */
	List<SalesStats> findStatssby(Map<String,Object> param) throws EssException;
	
	/**
	 * 查询某员工某天的记录
	 * */
	SalesStats findSalesby(int employeeId,int salesDate) throws EssException;
	
	void insertStats(SalesStats salesStats) throws EssException;
	
	void updateStatsBy(SalesStats salesStats) throws EssException;
	
}
