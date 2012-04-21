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
import com.jw.ess.dao.IStorageDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Price;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("storageDao")
public class StorageDao implements IStorageDao {
	
	private static final Log logger=LogFactory.getLog(StorageDao.class);
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "priceDao")
	private IPriceDao priceDao;
	
	private static final String INSERT_STORAGE = MapperConstant.MAPPER_NAMESPACE_STORAGE
	+ ".insertStorage";
	
	private static final String FIND_STORAGE = MapperConstant.MAPPER_NAMESPACE_STORAGE
	+ ".findStorage";
	
	private static final String UPDATE_STORAGE = MapperConstant.MAPPER_NAMESPACE_STORAGE
	+ ".updateStorage";
	
	private static final String FIND_STORAGES = MapperConstant.MAPPER_NAMESPACE_STORAGE
	+ ".findStorages";
	
	private static final String FIND_COUNT_OF_STORAGE = MapperConstant.MAPPER_NAMESPACE_STORAGE
    + ".findCountOfStorage";
	
	private static final String FIND_STORAGE_INFO = MapperConstant.MAPPER_NAMESPACE_STORAGE
    + ".findStorageInfo";
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public Storage findStorage(int tenantId, int floorId) throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.TENANT_ID, tenantId);
	    	param.put(ParameterMapKeys.FLOOR_ID, floorId);
			return (Storage) sqlSessionTemplate.selectOne(FIND_STORAGE, param);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertStorage(Storage storage) throws EssException {
		try 
		{
			sqlSessionTemplate.insert(INSERT_STORAGE, storage);
		} 
		catch (PersistenceException e) 
		{
			logger.error("failed to insertStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateStorage(Map<String, Object> map) throws EssException {
		try 
		{
			sqlSessionTemplate.update(UPDATE_STORAGE, map);
		} 
		catch (PersistenceException e) {
			logger.error("failed to updateStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public int findCountOfStorage(Map<String, Object> map) throws EssException {
		try 
		{
			return (Integer)sqlSessionTemplate.selectOne(FIND_COUNT_OF_STORAGE, map);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findCountStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StorageInfo> findStorages(Map<String, Object> map) throws EssException {
		try 
		{
			return (List<StorageInfo>) sqlSessionTemplate.selectList(FIND_STORAGES, map);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findStorages", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public StorageInfo findStorageInfo(int tenantId,int floorId)
			throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.TENANT_ID, tenantId);
	    	param.put(ParameterMapKeys.FLOOR_ID, floorId);
	    	StorageInfo storageInfo = (StorageInfo) sqlSessionTemplate.selectOne(FIND_STORAGE_INFO, param);
	    	Floor floor = storageInfo.getStorage().getFloor();
	    	storageInfo.getStorage().setFloor(addTenentPrice(tenantId,floor));
	    	
			return storageInfo;
		} 
		catch (PersistenceException e) {
			logger.error("failed to findStorageInfo", e);
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
