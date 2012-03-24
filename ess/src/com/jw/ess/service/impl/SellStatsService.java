package com.jw.ess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IOrderDao;
import com.jw.ess.entity.Order;
import com.jw.ess.service.ISellStatsService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ex.EssException;

@Service("sellStatsService")
public class SellStatsService implements ISellStatsService 
{
	@Resource
	private IOrderDao orderDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public Double getPerformanceByDate(int employeeId, String beginDate,
			String endDate) throws EssException 
	{
		int operateDateBegin = DateUtil.transformTimeSecs(beginDate);
		int operateDateEnd = DateUtil.transformTimeSecs(endDate) + 24*60*60;
		List<Order> orders = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("stateId", 2);
		map.put("operateDateEnd", operateDateEnd);
		map.put("operateDateBegin", operateDateBegin);
		orders = orderDao.findOrdersBy(map);
		Double cost = 0.0;
		for(Order order:orders)
		{
			cost = cost+order.getAmount();
		}
		return cost;
	}

}
