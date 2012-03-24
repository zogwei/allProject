package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Vein;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class VeinServiceTest
{
	private static final Log logger = LogFactory.getLog(VeinServiceTest.class);

	private IVeinService veinService;

	@Before
	public void setUp() throws Exception
	{
		veinService = (IVeinService) SpringAssisant.getBean("veinService");
	}

	@Test
	public void testAddVein()
	{
		Vein vein = new Vein();
		vein.setName("打飞机");
		vein.setTenantId(1);
		vein.setIsValid(1);

		try
		{
			veinService.addVein(vein);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddVein");
		}
	}

}
