package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderUpdate;
import com.jw.ess.util.ex.EssException;

/**
 * 订单的数据库操作接口
 * 
 * @author yxliuh
 * @package com.jw.ess.dao
 * @project ess
 */
public interface IOrderDao {
	/**
	 * 创建订单
	 * 
	 * @param order
	 *            创建的订单
	 * @throws EssException
	 */
	int insertOrder(Order order) throws EssException;
	
	/***
	 * 订单签收
	 * @param order
	 * 		订单签收，修改 received currentState operateDate
	 * @throws EssException
	 * */

	void updateOrderReceived(Order order) throws EssException;

	/**
	 * 根据订单id查询订单
	 * 
	 * @param orderId
	 *            订单id
	 * @return 订单对象
	 * @throws EssException
	 */
	Order findOrderById(int orderId) throws EssException;

	/**
	 * 根据订单状态，交易金额，时间段查询订单
	 * 
	 * @param Map
	 *            <String,Object> <"tenantId",租户Id> <"stateId",stateId> 订单状态
	 *            <"amount",amount> 交易金额 <"beginIndex",beginIndex> 页面开始数据
	 *            <"operateDateBegin",operateDateBegin> 时间段查询起始时间
	 *            <"operateDateEnd",operateDateEnd> 时间段查询截止时间
	 *            <"minAmount",minAmount>最小金额 <"maxAmount",maxAmount>最小金额
	 * @return 订单列表
	 * */
	List<Order> findOrdersBy(Map<String, Object> map) throws EssException;

	/**
	 * 根据订单状态，交易金额，时间段查询订单记录总数
	 * 
	 * @param Map
	 *            <String,Object>.
	 *            key["stateId"-订单状态id，"operatorId"-操作者id，"minAmount"
	 *            -最小金额，"maxAmount"
	 *            -最大金额，"operateDateBegin"-操作开始时间，"operateDateEnd"
	 *            -操作结束时间]                                                                    
	 * @return 记录总数
	 * */

	int findCountOfOrder(Map<String, Object> map) throws EssException;
	
	/***
	 * 订单退货
	 * @param order
	 * 			修改字段 refund currentState operatoDate 
	 * @return void
	 * 
	 * */
	
	void cancelOrder(Order order) throws EssException;
	
	void updateOrder(Order order) throws EssException;
	
	int insertOrderUpdateDao(OrderUpdate orderUpdate) throws EssException ;
	
}
