package com.jw.ess.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IOrderDao;
import com.jw.ess.entity.Order;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.TypeUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("orderDao")
public class OrderDao implements IOrderDao {
	private static final Log logger = LogFactory.getLog(OrderDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	private static final String INSERT_ORDER = MapperConstant.MAPPER_NAMESPACE_ORDER + ".insertOrder";
	
	private static final String FIND_ORDER_BY_ID = MapperConstant.MAPPER_NAMESPACE_ORDER + ".findOrderById";

	private static final String FIND_ORDERS_BY_MAP = MapperConstant.MAPPER_NAMESPACE_ORDER + ".findOrdersBy";

	private static final String COUNT_BY_MAP = MapperConstant.MAPPER_NAMESPACE_ORDER + ".findCountOfOrder";

	private static final String CANCEL_ORDER = MapperConstant.MAPPER_NAMESPACE_ORDER+".cancelOrder";
	
	private static final String UPDATE_ORDER_RECEIVED = MapperConstant.MAPPER_NAMESPACE_ORDER+".updateOrderReceivced";
	@Override
	public int insertOrder(Order order) throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_ORDER, order);
			return order.getId();
		} catch (PersistenceException e) {
			logger.error("failed to insertOrder", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Order findOrderById(int orderId) throws EssException {
		Order order = new Order();
		try {
			order = (Order) sqlSessionTemplate.selectOne(FIND_ORDER_BY_ID, orderId);
		} catch (PersistenceException e) {
			logger.error("fail to find order by orderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

		return order;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> findOrdersBy(Map<String, Object> map) throws EssException {
		List<Order> orders = new ArrayList<Order>();
		try {
			orders = (List<Order>) sqlSessionTemplate.selectList(FIND_ORDERS_BY_MAP, map);
		} catch (PersistenceException e) {
			logger.error("fail to find orders by map", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
		return orders;
	}

	@Override
	public int findCountOfOrder(Map<String, Object> map) throws EssException {
		try {
			return TypeUtil.toInt(sqlSessionTemplate.selectOne(COUNT_BY_MAP, map));
		} catch (PersistenceException e) {
			logger.error("failed to findCountOfOrder", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateOrderReceived(Order order) throws EssException {
		try{
			sqlSessionTemplate.update(UPDATE_ORDER_RECEIVED, order);
		}catch (PersistenceException e) {
			logger.error("failed to updateOrderReceived", e);
			throw new EssException(e,MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void cancelOrder(Order order) throws EssException {
		try{
			sqlSessionTemplate.update(CANCEL_ORDER, order);
		}catch (PersistenceException e) {
			logger.error("failed to cancelorder", e);
		}
	}
	
}
