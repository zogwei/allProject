package com.jw.ess.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IOrderStateTraceDao;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.service.IOrderStateTraceService;
import com.jw.ess.util.ex.EssException;

@Service("orderStateTraceService")
public class OrderStateTraceService implements IOrderStateTraceService 
{
	@Resource(name="orderStateTraceDao")
	private IOrderStateTraceDao orderStateTrace;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addOrderState(OrderStateTrace state) throws EssException
	{
		orderStateTrace.insertOrderStateTrace(state);
		
	}

}
