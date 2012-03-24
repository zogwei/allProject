package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.ITenantDao;
import com.jw.ess.entity.Tenant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class TenantDaoTest {
	private static final Log logger = LogFactory.getLog(TenantDaoTest.class);

	private ITenantDao tenantDao;

	@Before
	public void setUp() throws Exception {
		tenantDao = (ITenantDao) SpringAssisant.getBean("tenantDao");
	}

	@Test
	public void testFindAllTenants() {
		try {
			List<Tenant> list = tenantDao.findAllTenants();
			logger.debug(list);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testDeleteTenant");
		}
	}
	
	@Test
	public void testUpdateTenant() {
		try {
			Tenant tenant=new Tenant();
			tenant.setName("test");
			tenant.setCreatedDate(DateUtil.currentTimeSecs());
			tenant.setIsDefault(1);
			tenant.setIsValid(1);
			tenant.setDesc("i am a test tenant");
			tenant.setId(2);
			tenantDao.updateTenant(tenant);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testUpdateTenant");
		}
	}


	@Test
	public void testInsertTenant() {
		Tenant tenant = new Tenant();
		tenant.setName("system");
		tenant.setCreatedDate(DateUtil.currentTimeSecs());
		tenant.setIsValid(1);
		tenant.setIsDefault(1);
		try {
			tenantDao.insertTenant(tenant);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertTenant");
		}
	}

	@Test
	public void testFindTenantName() {
		Tenant tenant = new Tenant();
		String tenantName = "system";
		tenant.setName(tenantName);
		try {
			String result = tenantDao.findTenantName(tenant);
			logger.debug(result);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindTenantName");
		}
	}

}
