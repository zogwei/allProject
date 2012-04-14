package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IPriceDao;
import com.jw.ess.dao.ISpecDao;
import com.jw.ess.entity.Price;
import com.jw.ess.entity.Spec;
import com.jw.ess.service.IPriceService;
import com.jw.ess.service.ISpecService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 规格表服务操作接口实现类
 * 
 * @author j&w
 * 
 */
@Service("priceService")
public class PriceService implements IPriceService
{
	private static final Log logger = LogFactory.getLog(PriceService.class);

	@Resource(name = "priceDao")
	private IPriceDao priceDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addPrice(Price price) throws EssException
	{
		priceDao.deletePrice(price);
		priceDao.insertPrice(price);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void updatePrice(Price price) throws EssException
	{
		priceDao.updatePrice(price);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public List<Price> findPrice(Price price) throws EssException
	{
		return priceDao.findPrice(price);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void deletePrice(Price price) throws EssException
	{
		 priceDao.deletePrice(price);
	}

}
