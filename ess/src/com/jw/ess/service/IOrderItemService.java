package com.jw.ess.service;

import com.jw.ess.entity.OrderItem;
import com.jw.ess.util.ex.EssException;

public interface IOrderItemService 
{
	void addOrderItem(OrderItem item) throws EssException;
	
//	List<OrderItem> findItemByOrderNo(String orderNo) throws EssException;
	
	
}
