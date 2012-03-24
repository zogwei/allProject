package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.ITenantDao;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Service("tenantService")
public class TenantService implements ITenantService {
	private static final Log logger = LogFactory.getLog(TenantService.class);
	@Resource(name = "tenantDao")
	private ITenantDao tenantDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addTenant(Tenant tenant) throws EssException {
		//check tenant name is exists or not
		checkTenantName(tenant);
		tenantDao.insertTenant(tenant);
	}

	private void checkTenantName(Tenant tenant) throws EssException {
		if (tenantDao.findTenantName(tenant) != null) {
			logger.info("tenant name" + tenant.getName() + " already exists");
			throw new EssException("tenant name " + tenant.getName() + " already exists", MessageCode.TENANT_NAME_ALERADY_EXISTS);
		}
	}
	
	@Override
	public List<Tenant> getAllTenants() throws EssException {
		return tenantDao.findAllTenants();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=EssException.class)
	public void modifyTenant(Tenant tenant) throws EssException {
		//check tenant name is exists or not
		checkTenantName(tenant);
		tenantDao.updateTenant(tenant);
	}

	@Override
	public List<Tenant> getTenantsByName(String tenantName) throws EssException {
		return tenantDao.findTenantsByName(tenantName);
	}

	@Override
	public Tenant getTenantsById(int id) throws EssException {
		return tenantDao.findTenantsById(id);
	}

	@Override
	public String getTenantName(Tenant tenant) throws EssException {
		String tenantName = tenantDao.findTenantName(tenant);
		return tenantName;
	}

	@Override
	public boolean checkTenant(Tenant tenant) throws EssException {
		if (tenantDao.findTenantName(tenant) == null 
				&& tenant.getName()!= tenantDao.findTenantName(tenant)) {
			return true;
		}
		return false;
	}

}
