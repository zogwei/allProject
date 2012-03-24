package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Customer;
import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.service.IOrderService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.PageSupport;

public class OrderServiceTest
{
	private static final Log logger = LogFactory.getLog(OrderServiceTest.class);

	private IOrderService orderService;

	@Before
	public void setUp() throws Exception
	{
		orderService = (IOrderService) SpringAssisant.getBean("orderService");
	}

	@Test
	public void testAddOrder()
	{
		Order order = new Order();
		Customer customer = new Customer();
		customer.setId(1);
		order.setCustomer(customer);
		order.setAmount(1688);
		order.setImprest(100);
		order.setIsValid(CommonConstant.STATE_VALID);
		Employee operator = new Employee();
		operator.setId(2);
		order.setOperator(operator);
		order.setCurrentState(CommonConstant.ORDER_STATE_BOOK);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setOrderNo("dd201112182319");
		order.setTenantId(2);

		Floor floor = new Floor();
		floor.setId(1);

		List<OrderItem> items = new ArrayList<OrderItem>();
		OrderItem item = new OrderItem();
		item.setArea(5);
		item.setSellPrice(108);
		item.setFloor(floor);
		item.setAmount(540);
		items.add(item);

		floor = new Floor();
		floor.setId(2);
		item = new OrderItem();
		item.setArea(8);
		item.setSellPrice(79);
		item.setFloor(floor);
		item.setAmount(632);
		items.add(item);

		floor = new Floor();
		floor.setId(3);
		item = new OrderItem();
		item.setArea(6);
		item.setSellPrice(86);
		item.setFloor(floor);
		item.setAmount(516);
		items.add(item);
		order.setItems(items);
		try
		{
			orderService.addOrder(order);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddOrder");
		}
	}

	@Test
	public void testGetOrderById()
	{
		int orderId = 3;
		try
		{
			Order order = orderService.getOrderById(orderId);
			logger.debug("matched order is " + order);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testGetOrderById");
		}
	}

	@Test
	public void testGetOrdersBy()
	{
		Map<String, Object> map = createQueryCondition();
		try
		{
			PageSupport<Order> ps = orderService.getOrdersBy(map);
			logger.debug(ps);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testGetOrderById");
		}
	}

	private Map<String, Object> createQueryCondition()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(ParameterMapKeys.OPERATOR_ID, 2);
		map.put(ParameterMapKeys.STATE_ID, CommonConstant.ORDER_STATE_BOOK);
		map.put(ParameterMapKeys.CURRENT_PAGE, 1);
		map.put(ParameterMapKeys.PAGE_SIZE, 10);
//		map.put(ParameterMapKeys.END_TIME, 1324220530);
		map.put(ParameterMapKeys.MIN_AMOUNT, 1500);
		return map;
	}
	
	@Test
	public void testOrderConfirm(){
		try {
			orderService.orderConfirm(1, 1200);
		} catch (EssException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCancelOrder(){
		OrderItem  item1 = new OrderItem();
		item1.setAmount(10);
		item1.setArea(30);
		item1.setOrderId(19);
		Floor floor = new Floor();
		floor.setId(1);
		item1.setFloor(floor);
		
		OrderItem item2 = new OrderItem();
		item2.setAmount(20);
		item2.setArea(10);
		item2.setOrderId(19);
		Floor floor2 = new Floor();
		floor2.setId(2);
		item2.setFloor(floor2);
		List<OrderStateTrace> states = new ArrayList<OrderStateTrace>();
		OrderStateTrace  state = new OrderStateTrace();
		state.setOrderId(19);
		state.setStateId(3);
		state.setOperateDate(DateUtil.currentTimeSecs());
		states.add(state);
		
		List<OrderItem> items = new ArrayList<OrderItem>();
		items.add(item1);
		items.add(item2);
		Order order = new Order();
		order.setItems(items);
		order.setId(19);
		order.setStateTraces(states);
		try {
			orderService.cancelOrder(order);
		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
