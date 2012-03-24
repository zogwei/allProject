package com.jw.ess.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IStorageInfoDao;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("storageInfoDao")
public class StorageInfoDao implements IStorageInfoDao {
	
	private static final Log logger=LogFactory.getLog(StorageInfoDao.class);
	
	private static final String FIND_STORAGE_INFO_BY = MapperConstant.MAPPER_NAMESPACE_STORAGE_INFO
	+ ".findStorageInfoBy";
	
	private static final String INSERT_STORAGE_INFO = MapperConstant.MAPPER_NAMESPACE_STORAGE_INFO
	+ ".insertStorageInfo";
	
	private static final String UPDATE_COUNT_STORAGE = MapperConstant.MAPPER_NAMESPACE_STORAGE_INFO
	+ ".updateCountInStorage";
	
	private static final String UPDATE_COUNT_ORDER = MapperConstant.MAPPER_NAMESPACE_STORAGE_INFO
	+ ".updateCountOrder";
	
	private static final String UPDATE_COUNT_ORDER_CANCEL = MapperConstant.MAPPER_NAMESPACE_STORAGE_INFO
	+ ".updateCountOrderCancel";

	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public StorageInfo findStorageInfoBy(int floorId) throws EssException {
		try 
		{
			return (StorageInfo) sqlSessionTemplate.selectOne(FIND_STORAGE_INFO_BY, floorId);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findStorageInfoBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertStorageInfo(int floorId, int countInStorage,
			int countOrder, int countOrderCancel) throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, floorId);
	    	param.put(ParameterMapKeys.COUNT_IN_STORAGE, countInStorage);
	    	param.put(ParameterMapKeys.COUNT_ORDER, countOrder);
	    	param.put(ParameterMapKeys.COUNT_ORDER_CANCEL, countOrderCancel);
			sqlSessionTemplate.insert(INSERT_STORAGE_INFO, param);
		} 
		catch (PersistenceException e) {
			logger.error("failed to insertStorageInfo", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateCountInStorage(int floorId,int countInStorage) throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, floorId);
	    	param.put(ParameterMapKeys.COUNT_IN_STORAGE, countInStorage);
			sqlSessionTemplate.update(UPDATE_COUNT_STORAGE, param);
		} 
		catch (PersistenceException e) {
			logger.error("failed to updateCountInStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateCountOrder(int floorId,int countOrder) throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, floorId);
	    	param.put(ParameterMapKeys.COUNT_ORDER, countOrder);
			sqlSessionTemplate.update(UPDATE_COUNT_ORDER, param);
		} 
		catch (PersistenceException e) {
			logger.error("failed to updateCountOrder", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateCountOrderCancel(int floorId,int countOrderCancel)
			throws EssException {
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, floorId);
	    	param.put(ParameterMapKeys.COUNT_ORDER_CANCEL, countOrderCancel);
			sqlSessionTemplate.update(UPDATE_COUNT_ORDER_CANCEL, param);
		} 
		catch (PersistenceException e) {
			logger.error("failed to updateCountOrderCancel", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
