package com.jw.ess.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.ITenantDao;
import com.jw.ess.entity.Tenant;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("tenantDao")
public class TenantDao implements ITenantDao {
	private static final Log logger = LogFactory.getLog(TenantDao.class);
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_TENANT = MapperConstant.MAPPER_NAMESPACE_TENANT + ".insertTenant";

	private static final String FIND_TENANT_NAME = MapperConstant.MAPPER_NAMESPACE_TENANT + ".findTenantName";

	private static final String FIND_ALL_TENANTS = MapperConstant.MAPPER_NAMESPACE_TENANT + ".findAllTenants";
	
	private static final String UPDATE_TENANT = MapperConstant.MAPPER_NAMESPACE_TENANT + ".updateTenant";
	
	private static final String FIND_TENANTS_BY_NAME = MapperConstant.MAPPER_NAMESPACE_TENANT + ".findTenantsByName";
	
	private static final String FIND_TENANTS_BY_ID = MapperConstant.MAPPER_NAMESPACE_TENANT + ".findTenantsById";

	@SuppressWarnings("unchecked")
	@Override
	public List<Tenant> findAllTenants() throws EssException {
		try {
			return (List<Tenant>) sqlSessionTemplate.selectList(FIND_ALL_TENANTS);
		} catch (PersistenceException e) {
			logger.error("failed to findAllTenants", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertTenant(Tenant tenant) throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_TENANT, tenant);
		} catch (PersistenceException e) {
			logger.error("failed to insertTenant", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findTenantName(Tenant tenant) throws EssException {
		try {
			return (String) sqlSessionTemplate.selectOne(FIND_TENANT_NAME, tenant);
		} catch (PersistenceException e) {
			logger.error("failed to findTenantNameBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateTenant(Tenant tenant) throws EssException {
		try {
			sqlSessionTemplate.update(UPDATE_TENANT, tenant);
		} catch (PersistenceException e) {
			logger.error("failed to updateTenant", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tenant> findTenantsByName(String tenantName) throws EssException {
		try {
			return (List<Tenant>)sqlSessionTemplate.selectList(FIND_TENANTS_BY_NAME,tenantName);
		} catch (PersistenceException e) {
			logger.error("failed to findTenantsByName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Tenant findTenantsById(int id) throws EssException {
		try {
			return (Tenant) sqlSessionTemplate.selectOne(FIND_TENANTS_BY_ID, id);
		} catch (PersistenceException e) {
			logger.error("failed to findTenantById", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	

}
