package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IOrderItemDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class OrderItemDaoTest {
	private static final Log logger = LogFactory.getLog(OrderItemDaoTest.class);

	private IOrderItemDao orderItemDao;

	@Before
	public void setUp() throws Exception {
		orderItemDao = (IOrderItemDao) SpringAssisant.getBean("orderItemDao");
	}

	@Test
	public void testInsertOrderItem() {
		OrderItem item = new OrderItem();
		item.setArea(5);
		item.setOrderId(1);
		item.setSellPrice(86);
		Floor floor = new Floor();
		floor.setId(1);
		item.setFloor(floor);
		item.setAmount(430);

		try {
			orderItemDao.insertOrderItem(item);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertOrderItem");
		}
	}
	@Test
	public void testFindItemsByOrderId()
	{
		try {
			List<OrderItem> items = orderItemDao.findItemsByOrderId(1);
			for(OrderItem o : items){
				System.out.println(o);
			}
		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
