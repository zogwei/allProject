package com.jw.ess.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IFloorDao;
import com.jw.ess.dao.IPriceDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Price;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.TypeUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 
 * 实现Floor类的增改查
 * 
 * */
@Repository("floorDao")
public class FloorDao implements IFloorDao {

	private static final Log logger = LogFactory.getLog(FloorDao.class);

	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "priceDao")
	private IPriceDao priceDao;

	private static final String INSERT_FLOOR = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".insertFloor";

	private static final String UPDATE_FLOOR = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".updateFloor";

	private static final String FIND_FLOOR = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findById";

	private static final String FIND_FLOOR_NAME = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findFloorName";
	
	private static final String FIND_FLOOR_NAME_EXCLUDE_SELF = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findFloorNameExcludeSelf";

	private static final String FIND_FLOORS_BY_TENANTID = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findFloorsByTenantId";

	private static final String FIND_FLOORS_COUNT = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findCountOfFloor";

	private static final String FIND_FLOORS = MapperConstant.MAPPER_NAMESPACE_FLOOR
			+ ".findFloors";

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public Floor findById(int id,int tenantId) throws EssException {
		try {
			Floor item =  (Floor) sqlSessionTemplate.selectOne(FIND_FLOOR, id);
			return addTenentPrice(tenantId,item);
		} catch (PersistenceException e) {
			logger.error("failed to findFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

	}
	
	@Override
	public Floor findById(int id) throws EssException {
		try {
			return (Floor) sqlSessionTemplate.selectOne(FIND_FLOOR, id);
		} catch (PersistenceException e) {
			logger.error("failed to findFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

	}

	@Override
	public int insertFloor(Floor floor) throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_FLOOR, floor);
			return floor.getId();
		} catch (PersistenceException e) {
			logger.error("failed to insertFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

	}

	@Override
	public void updateFloor(Floor floor) throws EssException {
		try {
			sqlSessionTemplate.update(UPDATE_FLOOR, floor);
		} catch (PersistenceException e) {
			logger.error("failed to updateFLoor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Floor> findFloorsByTenantId(int tenantId) throws EssException {
		List<Floor> returnItems = new ArrayList();
		try {
			
			List<Floor> items =  (List<Floor>) sqlSessionTemplate.selectList(
					FIND_FLOORS_BY_TENANTID, tenantId);
			
			for(Floor item : items)
			{
				returnItems.add(addTenentPrice(tenantId,item));
			}
			
		} catch (PersistenceException e) {
			logger.error("failed to findFloorsByTenantId", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
		return returnItems;
	}
	
	@Override
	public int findCountOfFloor(Map<String, Object> param) throws EssException {
		try {
			return TypeUtil.toInt(sqlSessionTemplate.selectOne(
					FIND_FLOORS_COUNT, param));
		} catch (PersistenceException e) {
			logger.error("failed to findCountOfFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Floor> findFloorsBy(Map<String, Object> param,int tenantId)
			throws EssException {
		List<Floor> returnItems = new ArrayList();
		try {
			List<Floor> items = (List<Floor>) sqlSessionTemplate.selectList(FIND_FLOORS,
					param);
			for(Floor item : items)
			{
				returnItems.add(addTenentPrice(tenantId,item));
			}
			
		} catch (PersistenceException e) {
			logger.error("failed to findAllEmployees", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
		
		return returnItems;
	}

	
	@Override
	public Floor findFloorByName(int tenantId, String floorName)
			throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.FLOOR_NAME, floorName);
		try {
			 Floor item = (Floor) sqlSessionTemplate
					.selectOne(FIND_FLOOR_NAME, param);

			 return addTenentPrice(tenantId,item);
		} catch (PersistenceException e) {
			logger.error("failed to insertFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	
	@Override
	public Floor findFloorName(int tenantId, int floorId, String floorName)
			throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.FLOOR_ID, floorId);
		param.put(ParameterMapKeys.FLOOR_NAME, floorName);
		try {
			return (Floor) sqlSessionTemplate
					.selectOne(FIND_FLOOR_NAME_EXCLUDE_SELF, param);

		} catch (PersistenceException e) {
			logger.error("failed to insertFloor", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	private Floor addTenentPrice(int tenantId,Floor floor){
		Floor returnFloor = floor;
		if(floor==null||floor.getId()==0)
		{
			return floor;
		}
		
		int floorId = floor.getId();
		
		try{
			Price price = new Price();
			List<Price>  returnPrices = null;
			Price itemPrice = null;
			price.setTenantId(tenantId);
			price.setFloorId(floorId);
			returnPrices = priceDao.findPrice(price);
			if(returnPrices.size()>0)
			{
				itemPrice = returnPrices.get(0);
				floor.setAmountPrice(itemPrice.getAmountPrice());
				floor.setSellPrice(itemPrice.getSellPrice());
				floor.setDetailPrice(itemPrice.getDetailPrice());
				returnFloor = floor;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return returnFloor;
	}

}
