package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.OrderItem;
import com.jw.ess.util.ex.EssException;

/**
 * 订单项的数据库操作接口
 * @author yxliuh
 * @package com.jw.ess.dao
 * @project ess
 */
public interface IOrderItemDao {
	/**
	 * 添加订单项
	 * @param orderItem
	 * @throws EssException
	 */
	void insertOrderItem(OrderItem orderItem) throws EssException;
	
	/**
	 * 查看订单明细
	 * @param number 订单编号
	 * @return 订单明细
	 * @throws EssException
	 */
	List<OrderItem> findItemsByOrderId(int orderId)throws EssException;
	
	public void updateItems(Map param) throws EssException;
	
	public void deleteItems(Map param) throws EssException;
}
