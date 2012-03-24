package com.jw.ess.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IOrderItemDao;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.service.IOrderItemService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Service("orderItemService")
public class OrderItemService implements IOrderItemService 
{
	private static final Log logger = LogFactory.getLog(CustomerService.class);
	
	@Resource(name="orderItemDao")
	private IOrderItemDao orderItemDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addOrderItem(OrderItem item) throws EssException 
	{
		try
		{
			orderItemDao.insertOrderItem(item);
		}catch (EssException e) {
			logger.error("fail to insert orderItem", e);
			throw new EssException(e,MessageCode.ORDER_ITEM_ADD_ERROR);
		}
		
	}

}
