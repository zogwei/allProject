package com.jw.ess.service;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.PageSupport;

/**
 * 订单业务接口
 * 
 * @author j&w
 * 
 */
public interface IOrderService {
	/**
	 * 订单预订
	 * 
	 * @param order
	 *            订单对象
	 * @throws EssException
	 */
	int addOrder(Order order) throws EssException;
	
	/**
	 * 新增修改订单
	 * 
	 * @param order
	 *            订单对象
	 * @throws EssException
	 */
	void addUpdateOrder(Order order,Map param) throws EssException;
	
	/**
	 * 修改订单
	 * 
	 * @param order
	 *            订单对象
	 * @throws EssException
	 */
	void updateOrderAuth(Map param,boolean result) throws EssException;

	/**
	 * 根据订单id查询订单
	 * 
	 * @param orderId
	 *            订单id
	 * @return
	 * @throws EssException
	 */
	Order getOrderById(int orderId) throws EssException;

	/**
	 * 根据订单状态，交易金额，时间段查询订单
	 * 
	 * @param Map
	 *            <String,Object>.
	 *            key["stateId"-订单状态id，"operatorId"-操作者id，"minAmount"
	 *            -最小金额，"maxAmount"
	 *            -最大金额，"operateDateBegin"-操作开始时间，"operateDateEnd"
	 *            -操作结束时间，"currentPage"-请求页数，"pageSize"-每页显示记录数]                                                                    
	 * @return 订单列表
	 * */
	PageSupport<Order> getOrdersBy(Map<String, Object> map) throws EssException;

	/**
	 * 订单确认
	 * @param order
	 * 
	 * @return void
	 * **/
	
	void orderConfirm (int orderId , double received) throws EssException;
	/***
	 * 订单取消（退货）
	 * @param order 
	 * @return void 
	 * */
	
	public void cancelOrder(Order order,List<OrderItem> items) throws EssException;
	/***
	 * 订单取消（退货）
	 * @param order 
	 * @param operatorId
	 * @return void 
	 * */

	PageSupport<Order> getOrdersByForExport(Map<String, Object> map)
			throws EssException;
	
	public Map selectOrderUpdate(Map parameter) throws EssException;
}

