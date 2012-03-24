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

import com.jw.ess.dao.ISpecDao;
import com.jw.ess.entity.Spec;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 规格表数据库操作接口实现类
 * 
 * @author chenxiangbin
 * 
 */
@Repository("specDao")
public class SpecDao implements ISpecDao
{
	private static final Log logger = LogFactory.getLog(SpecDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_SPEC = MapperConstant.MAPPER_NAMESPACE_SPEC
			+ ".insertSpec";

	private static final String FIND_ALL_SPECS = MapperConstant.MAPPER_NAMESPACE_SPEC
			+ ".findAllSpecs";

	private static final String FIND_SPEC_NAME = MapperConstant.MAPPER_NAMESPACE_SPEC
			+ ".findSpecName";

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void insertSpec(Spec spec) throws EssException
	{
		try
		{
			this.sqlSessionTemplate.insert(INSERT_SPEC, spec);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertSpec", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Spec> findAllSpecs(int tenantId) throws EssException
	{
		try
		{
			return (List<Spec>) this.sqlSessionTemplate.selectList(
					FIND_ALL_SPECS, tenantId);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findAllSpecs", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findSpecName(int tenantId, String specName)
			throws EssException
	{
		try
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.TENANT_ID, tenantId);
			param.put(ParameterMapKeys.SPEC_NAME, specName);
			return (String) sqlSessionTemplate.selectOne(FIND_SPEC_NAME, param);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findSpecName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
