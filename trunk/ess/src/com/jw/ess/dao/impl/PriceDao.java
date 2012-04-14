package com.jw.ess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IPriceDao;
import com.jw.ess.dao.IVeinDao;
import com.jw.ess.entity.Price;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("priceDao")
public class PriceDao implements IPriceDao {
	private static final Log logger = LogFactory.getLog(PriceDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERTPRICE = MapperConstant.MAPPER_NAMESPACE_PRICE + ".insertPrice";

	private static final String FINDPRICE = MapperConstant.MAPPER_NAMESPACE_PRICE + ".findPrice";

	private static final String UPDATEPRICE = MapperConstant.MAPPER_NAMESPACE_PRICE + ".updatePrice";
	
	private static final String DELETEPRICE = MapperConstant.MAPPER_NAMESPACE_PRICE + ".deletePrice";
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	public void insertPrice(Price price) throws EssException{
		try {
			this.sqlSessionTemplate.insert(INSERTPRICE, price);
		} catch (PersistenceException e) {
			logger.error("failed to insertVein", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	public List<Price> findPrice(Price price) throws EssException{
		try {
			return (List<Price>) sqlSessionTemplate.selectList(FINDPRICE, price);
		} catch (PersistenceException e) {
			logger.error("failed to insertVein", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	public void deletePrice(Price price) throws EssException{
		try
		{
			sqlSessionTemplate.update(DELETEPRICE, price);
		}catch (PersistenceException e) {
			logger.error("failed to findItemsByOrderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	public void updatePrice(Price price) throws EssException{
		try
		{
			sqlSessionTemplate.update(UPDATEPRICE, price);
		}catch (PersistenceException e) {
			logger.error("failed to findItemsByOrderId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
}
