package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IOrderDao;
import com.jw.ess.entity.Customer;
import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Order;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.generator.DefaultNumberGenerator;

public class OrderDaoTest {
	private static final Log logger = LogFactory.getLog(OrderDaoTest.class);

	private IOrderDao orderDao;

	@Before
	public void setUp() throws Exception {
		orderDao = (IOrderDao) SpringAssisant.getBean("orderDao");
	}

	@Test
	public void testInsertOrder() {
		Order order = new Order();
		Customer customer = new Customer();
		customer.setId(1);
		order.setCustomer(customer);
		order.setOrderNo(DefaultNumberGenerator.OrderNumberGenerate());
		Employee operator = new Employee();
		operator.setId(2);
		order.setOperator(operator);
		order.setAmount(1330);
		order.setImprest(150);
		order.setCurrentState(CommonConstant.ORDER_STATE_BOOK);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setIsValid(CommonConstant.STATE_VALID);
		order.setTenantId(2);

		try {
			orderDao.insertOrder(order);
			logger.debug("order id is " + order.getId());
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertOrder");
		}
	}

	@Test
	public void testFindOrderById() {
		int orderId = 8;
		try {
			Order order = orderDao.findOrderById(orderId);
			System.out.println(order.getOperator().getId());
			logger.debug(order);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindOrderById");
		}
	}

	@Test
	public void testFindOrdersBy() {
		Map<String, Object> map = createQueryCondition();
		map.put(ParameterMapKeys.BEGIN_INDEX, 0);
		map.put(ParameterMapKeys.PAGE_SIZE, 10);
		try {
			List<Order> orders = orderDao.findOrdersBy(map);
			logger.debug("matched orders is " + orders);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindCountOfOrder");
		}
	}

	@Test
	public void testFindCountOfOrder() {
		Map<String, Object> map = createQueryCondition();
		try {
			int count = orderDao.findCountOfOrder(map);
			logger.debug("count of order is " + count);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindCountOfOrder");
		}
	}

	private Map<String, Object> createQueryCondition() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ParameterMapKeys.OPERATOR_ID, 2);
		//		map.put(ParameterMapKeys.STATE_ID, CommonConstant.ORDER_STATE_BOOK);
		map.put(ParameterMapKeys.CURRENT_STATE, CommonConstant.ORDER_STATE_BOOK);
		//		map.put(ParameterMapKeys.MIN_AMOUNT, 750);
		//		map.put(ParameterMapKeys.MIN_AMOUNT, 1800);
		return map;
	}
	
	@Test
	public void testUpdateOrderReceived ()
	{
		Order order = new Order();
		order.setId(1);
		order.setReceived(1000);
		order.setCurrentState(2);
		order.setOperateDate(DateUtil.currentTimeSecs());
		try {
			orderDao.updateOrderReceived(order);
		} catch (EssException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCancelOrder(){
		Order order = new Order();
		order.setId(1);
		order.setCurrentState(3);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setRefund(140);
		try {
			orderDao.cancelOrder(order);
		} catch (EssException e) {
			e.printStackTrace();
		}
		
	}
}
