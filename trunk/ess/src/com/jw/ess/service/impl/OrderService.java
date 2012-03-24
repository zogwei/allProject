package com.jw.ess.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IOrderDao;
import com.jw.ess.dao.IOrderItemDao;
import com.jw.ess.dao.IOrderStateTraceDao;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.service.IDailySalesStatsService;
import com.jw.ess.service.IMonthlySalesStatsService;
import com.jw.ess.service.IOrderService;
import com.jw.ess.service.IStorageService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.generator.DefaultNumberGenerator;
import com.jw.ess.util.page.PageSupport;

@Service("orderService")
public class OrderService implements IOrderService {

	@Resource
	private IOrderDao orderDao;

	@Resource
	private IOrderItemDao orderItemDao;

	@Resource
	private IOrderStateTraceDao orderStateTraceDao;

	@Resource
	private IStorageService storageService;

	@Resource
	private IDailySalesStatsService dailySalesStatsService;
	
	@Resource
	private IMonthlySalesStatsService monthlySalesStatsService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public int addOrder(Order order) throws EssException {

		order.setCurrentState(CommonConstant.ORDER_STATE_BOOK);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setOrderNo(DefaultNumberGenerator.OrderNumberGenerate());
		int orderId = orderDao.insertOrder(order);
		List<OrderItem> items = order.getItems();
		for (OrderItem item : items) {
			item.setOrderId(orderId);
			orderItemDao.insertOrderItem(item);
		}
		OrderStateTrace stateTrace = new OrderStateTrace();
		stateTrace.setStateId(CommonConstant.ORDER_STATE_BOOK);
		stateTrace.setOperateDate(DateUtil.currentTimeSecs());
		stateTrace.setOrderId(order.getId());
		orderStateTraceDao.insertOrderStateTrace(stateTrace);
		
		// 向销售表中添加数据
		Order paramOrder = orderDao.findOrderById(orderId);
		SalesStats salesState = new SalesStats();
		salesState.setEmployeeId(paramOrder.getOperator().getId());
		salesState.setTenantId(paramOrder.getTenantId());
		salesState.setSalesAmount(paramOrder.getAmount());
		salesState.setSalesDate(paramOrder.getOperateDate());
		dailySalesStatsService.addDailyStats(salesState);
		monthlySalesStatsService.addMonthlyStats(salesState);
		return orderId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public Order getOrderById(int orderId) throws EssException {
		return orderDao.findOrderById(orderId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public PageSupport<Order> getOrdersBy(Map<String, Object> map)
			throws EssException {
		PageSupport<Order> ps = new PageSupport<Order>();

		ps.setCurrentPage(Integer.parseInt(String.valueOf(map
				.get(ParameterMapKeys.CURRENT_PAGE))));
		ps.setPageSize(Integer.parseInt((String.valueOf(map
				.get(ParameterMapKeys.PAGE_SIZE)))));
		// get count
		int count = orderDao.findCountOfOrder(map);
		ps.setCount(count);
		// check count equals 0 or not
		if (count != 0) {
			map.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			List<Order> orders = orderDao.findOrdersBy(map);
			ps.setResult(orders);
		}else
		{
			map.put(ParameterMapKeys.BEGIN_DATE, 0);
			List<Order> orders = new ArrayList<Order>();
			ps.setResult(orders);
		}
		return ps;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void orderConfirm(int orderId, double received) throws EssException {

		Order order = new Order();
		order.setId(orderId);
		order.setCurrentState(CommonConstant.ORDER_STATE_PAY);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setReceived(received);
		orderDao.updateOrderReceived(order);

		OrderStateTrace stateTrace = new OrderStateTrace();
		stateTrace.setStateId(CommonConstant.ORDER_STATE_PAY);
		stateTrace.setOrderId(orderId);
		stateTrace.setOperateDate(DateUtil.currentTimeSecs());
		orderStateTraceDao.insertOrderStateTrace(stateTrace);

		order.setItems(orderItemDao.findItemsByOrderId(orderId));
		storageService.modifyStorageByOrderAdd(order);

		// }

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void cancelOrder(Order order) throws EssException {

		order.setCurrentState(CommonConstant.ORDER_STATE_CANCEL);
		order.setOperateDate(DateUtil.currentTimeSecs());

		double totalRefund = 0;
		for (OrderItem item : order.getItems()) {
			totalRefund = totalRefund + item.getAmount();
		}
		order.setRefund(totalRefund);	
		
		orderDao.cancelOrder(order);

		orderStateTraceDao.insertOrderStateTrace(order.getStateTraces().get(0));
		storageService.modifyStorageByOrderCancel(order);

		// 销售处理
		int confirmDate = orderStateTraceDao.findConfirmDate(order.getId());
		Order paramOrder = new Order();
		paramOrder = orderDao.findOrderById(order.getId());
		dailySalesStatsService.subSalesAmount(paramOrder.getOperator().getId(),
				confirmDate, paramOrder.getRefund());
		monthlySalesStatsService.subMonthlySalesAmount(paramOrder.getOperator().getId(),
				confirmDate, paramOrder.getRefund());	
	}
}
