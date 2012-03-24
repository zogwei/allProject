package com.jw.ess.service;

import com.jw.ess.util.ex.EssException;

/**
 * 个人销售业绩统计操作接口
 * @author yxliuh
 *
 */
public interface ISellStatsService 
{
	/**
	 * 查询每个时间段内某个销售人员的业绩
	 * @param employeeId 销售人员Id
	 * @param beginDate 开始日期
	 * @param endDate  结束日期
	 * @return  业绩总额
	 * @throws EssException
	 */
	Double getPerformanceByDate(int employeeId,String beginDate,String endDate) throws EssException;

}
