package com.jw.ess.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IInStorageDao;
import com.jw.ess.entity.InStorage;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("inStorageDao")
public class InStorageDao implements IInStorageDao {
	
    private static final Log logger=LogFactory.getLog(InStorageDao.class);
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String INSERT_INSTORAGE = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".insertInStorage";
	
	private static final String UPDATE_INSTORAGE = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".updateInStorage";
	
	private static final String FIND_COUNT_OF_INSTORAGE = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".findCountOfInStorage";
	
	private static final String FIND_INSTORAGES_BY = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".findInStoragesBy";
	
	private static final String FIND_INSTORAGE_BY = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".findInStorageBy";
	
	private static final String FIND_INSTORAGES = MapperConstant.MAPPER_NAMESPACE_INSTORAGE
	+ ".findInStorage";
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public InStorage findInStorageBy(int id) throws EssException {
		try
		{
			return (InStorage) sqlSessionTemplate.selectOne(FIND_INSTORAGE_BY, id);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findInStorageBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertInStorage(InStorage inStorage) throws EssException {
		try
		{
			sqlSessionTemplate.insert(INSERT_INSTORAGE, inStorage);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertInStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateInStorage(InStorage inStorage) throws EssException {
		try
		{
			sqlSessionTemplate.update(UPDATE_INSTORAGE, inStorage);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to updateInStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<InStorage> findInStoragesBy(Map<String, Object> map) throws EssException {
		try
		{
			return (List<InStorage>) sqlSessionTemplate.selectList(FIND_INSTORAGES_BY, map);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findPageSizeInStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	public int findCountOfInStorage(Map<String, Object> param)
			throws EssException {
		try
		{
			return (Integer)sqlSessionTemplate.selectOne(FIND_COUNT_OF_INSTORAGE,param);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findInStorageTotal", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InStorage> findInStorages(Map<String, Object> map) throws EssException {
		try
		{
			return (List<InStorage>) sqlSessionTemplate.selectList(FIND_INSTORAGES, map);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findPageSizeInStorage", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
}
