package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Tenant;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class TenantServiceTest {
	private static final Log logger = LogFactory.getLog(TenantServiceTest.class);

	private ITenantService tenantService;

	@Before
	public void setUp() throws Exception {
		tenantService = (ITenantService) SpringAssisant.getBean("tenantService");
	}

	@Test
	public void testFindAllTenants() {
		try {
			List<Tenant> list = tenantService.getAllTenants();
			if (list != null) {
				logger.debug(list);
			}
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testDeleteTenant");
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
			tenantService.addTenant(tenant);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertTenant");
		}
	}
	
	@Test
	public void testUpdateTenant(){
		Tenant tenant=new Tenant();
		tenant.setId(2);
		tenant.setName("tenant1");
		tenant.setCreatedDate(DateUtil.currentTimeSecs());
		tenant.setIsValid(2);
		tenant.setIsDefault(2);
		tenant.setDesc("test update tenant1");
		try {
			tenantService.modifyTenant(tenant);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testUpdateTenant");
		}
	}

}
