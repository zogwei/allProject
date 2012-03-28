package com.jw.ess.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.jw.ess.entity.OrderUpdate;
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
	public PageSupport<Order> getOrdersByForExport(Map<String, Object> map)
			throws EssException {
		PageSupport<Order> ps = new PageSupport<Order>();

		ps.setCurrentPage(Integer.parseInt(String.valueOf(map
				.get("exportPage"))));
		ps.setPageSize(Integer.parseInt((String.valueOf(map
				.get("exportSize")))));
		map.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
		map.put("pageSize", Integer.parseInt((String.valueOf(map
				.get("exportSize")))));
		List<Order> orders = orderDao.findOrdersBy(map);
		ps.setResult(orders);
		
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
	
	/**
	 * 新增修改订单
	 * 
	 * @param order
	 *            订单对象
	 * @throws EssException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addUpdateOrder(Order order,Map param) throws EssException{
		//新增订单
		order.setCurrentState(CommonConstant.ORDER_STATE_BOOK);
		order.setOperateDate(DateUtil.currentTimeSecs());
		order.setOrderNo(DefaultNumberGenerator.OrderNumberGenerate());
		order.setIsValid(2);//新订单为无效
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
		
		//修改原订单状态
		Order orderUpdate = new Order();
		orderUpdate.setId(Integer.valueOf((String)param.get("oldOrderId")).intValue());
		orderUpdate.setCurrentState(CommonConstant.ORDER_STATE_UPDATE);
		orderDao.updateOrder(orderUpdate);
		
		//插入新旧订单关系表
		OrderUpdate orderUpdateASS = new OrderUpdate();
		orderUpdateASS.setNewOrderId(orderId);
		orderUpdateASS.setOldOrderId(Integer.valueOf((String)param.get("oldOrderId")).intValue());
		orderUpdateASS.setOperatorId(order.getOperator().getId());
		orderUpdateASS.setOperateDate(order.getOperateDate());
		orderUpdateASS.setStatus("1");
		orderDao.insertOrderUpdateDao(orderUpdateASS);
	}
	
	/**
	 * 修改订单
	 * 
	 * @param order
	 *            订单对象
	 * @throws EssException
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void updateOrderAuth(Map param,boolean result) throws EssException{
		
		
		int oldOrderId = Integer.valueOf((String)param.get("oldOrderId")).intValue();
		int newOrderId = Integer.valueOf((String)param.get("newOrderId")).intValue();
		if(!result)
		{
			//如果不确认，修改状态（新的无效，）
			//修改原订单状态，回复确认状态
			Order orderUpdate = new Order();
			orderUpdate.setId(oldOrderId);
			orderUpdate.setCurrentState(CommonConstant.ORDER_STATE_BOOK);
			orderDao.updateOrder(orderUpdate);
		}
		else
		{
			//如果确认
			
			//修改新订单设置有效，旧的设置为无效
			Order oldorderUpdate = new Order();
			oldorderUpdate.setId(oldOrderId);
			oldorderUpdate.setIsValid(2);
			orderDao.updateOrder(oldorderUpdate);
			
			Order neworderUpdate = new Order();
			neworderUpdate.setId(newOrderId);
			neworderUpdate.setIsValid(1);
			orderDao.updateOrder(neworderUpdate);
			
			//修改销售表
			//减去以前的销售，
			Order paramOrder = orderDao.findOrderById(oldOrderId);
			int confirmDate = orderStateTraceDao.findConfirmDate(oldOrderId);
			dailySalesStatsService.subSalesAmount(paramOrder.getOperator().getId(),
					confirmDate, paramOrder.getRefund());
			monthlySalesStatsService.subMonthlySalesAmount(paramOrder.getOperator().getId(),
					confirmDate, paramOrder.getRefund());	
			//增加新的销售
			Order newparamOrder = orderDao.findOrderById(newOrderId);
			SalesStats salesState = new SalesStats();
			salesState.setEmployeeId(newparamOrder.getOperator().getId());
			salesState.setTenantId(newparamOrder.getTenantId());
			salesState.setSalesAmount(newparamOrder.getAmount());
			salesState.setSalesDate(newparamOrder.getOperateDate());
			dailySalesStatsService.addDailyStats(salesState);
			monthlySalesStatsService.addMonthlyStats(salesState);
			
			
			//修改订单跟踪表,
			
			
			//修改订单更新表状态
			Map statusMap = new HashMap();
			statusMap.put("oldOrderId", oldOrderId+"");
			orderDao.updateOrderUpdateStatus(statusMap);
		}
	}
	
	public Map selectOrderUpdate(Map parameter) throws EssException
	{
		return orderDao.selectOrderUpdate(parameter);
	}
}
