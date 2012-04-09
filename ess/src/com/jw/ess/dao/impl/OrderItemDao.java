package com.jw.ess.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IOrderItemDao;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("orderItemDao")
public class OrderItemDao implements IOrderItemDao 
{
	private static final Log logger=LogFactory.getLog(OrderItemDao.class);
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	private static final String INSERT_ORDERITEM = MapperConstant.MAPPER_NAMESPACE_ORDERITEM
	+ ".insertOrderItem";
	
	private static final String FIND_ITEMS_BY_ORDERID = MapperConstant.MAPPER_NAMESPACE_ORDERITEM
    + ".findItemsByOrderId";
	
	private static final String UPDATEITEMS = MapperConstant.MAPPER_NAMESPACE_ORDERITEM
		    + ".updateItems";
	
	private static final String DELETEITEMS = MapperConstant.MAPPER_NAMESPACE_ORDERITEM
		    + ".deleteItems";
	
	@Override
	public void insertOrderItem(OrderItem orderItem) throws EssException 
	{
		try
		{
			sqlSessionTemplate.insert(INSERT_ORDERITEM, orderItem);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertOrderItem", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> findItemsByOrderId(int orderId) throws EssException {
		try
		{
			return(List<OrderItem>) sqlSessionTemplate.selectList(FIND_ITEMS_BY_ORDERID, orderId);
		}catch (PersistenceException e) {
			logger.error("failed to findItemsByOrderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateItems(Map param) throws EssException {
		try
		{
			sqlSessionTemplate.update(UPDATEITEMS, param);
		}catch (PersistenceException e) {
			logger.error("failed to findItemsByOrderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteItems(Map param) throws EssException {
		try
		{
			sqlSessionTemplate.update(DELETEITEMS, param);
		}catch (PersistenceException e) {
			logger.error("failed to findItemsByOrderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
}
