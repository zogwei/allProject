package com.jw.ess.service;

import org.springframework.stereotype.Service;

import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.util.ex.EssException;

@Service("orderStateTraceService")
public interface IOrderStateTraceService
{
	void addOrderState(OrderStateTrace state) throws EssException;
}
