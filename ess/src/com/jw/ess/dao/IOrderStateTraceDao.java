package com.jw.ess.dao;

import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.util.ex.EssException;

/**
 * 订单状态跟踪的数据库操作接口
 * 
 * @author yxliuh
 * @package com.jw.ess.dao
 * @project ess
 */
public interface IOrderStateTraceDao
{
	/**
	 * 添加订单状态追踪
	 * 
	 * @param orderStateTrace
	 * @throws EssException
	 */
	void insertOrderStateTrace(OrderStateTrace orderStateTrace)
			throws EssException;

	/***
	 * 根据订单Id查找订单确认时间
	 * @param int orderId
	 * */
	int findConfirmDate(int id) throws EssException;

}
