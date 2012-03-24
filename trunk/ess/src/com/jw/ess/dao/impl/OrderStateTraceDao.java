package com.jw.ess.dao.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IOrderStateTraceDao;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("orderStateTraceDao")
public class OrderStateTraceDao implements IOrderStateTraceDao
{
	private static final Log logger = LogFactory
			.getLog(OrderStateTraceDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	private static final String INSERT_ORDERSTATETRACE = MapperConstant.MAPPER_NAMESPACE_ORDERSTATETRACE
			+ ".insertOrderStateTrace";
	
	private static final String FIND_OPERATE_DATE = MapperConstant.MAPPER_NAMESPACE_ORDERSTATETRACE
			+".findOperateDate";

	@Override
	public void insertOrderStateTrace(OrderStateTrace orderState)
			throws EssException
	{
		try
		{
			sqlSessionTemplate.insert(INSERT_ORDERSTATETRACE, orderState);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertOrderState", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public int findConfirmDate(int id) throws EssException {
		try{
			return (Integer) sqlSessionTemplate.selectOne(FIND_OPERATE_DATE, id);
		}catch (PersistenceException e) {
			logger.error("failed to find operateDate", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
		
	}
	

}
