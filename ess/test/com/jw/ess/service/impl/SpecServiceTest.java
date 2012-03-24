package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Spec;
import com.jw.ess.service.ISpecService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class SpecServiceTest
{
	private static final Log logger = LogFactory.getLog(SpecServiceTest.class);

	private ISpecService specService;

	@Before
	public void setUp() throws Exception
	{
		specService = (ISpecService)SpringAssisant.getBean("specService");
	}

	@Test
	public void testAddSpec()
	{
		Spec spec = new Spec();
		spec.setName("900*95*18");
		spec.setIsValid(1);
		spec.setTenantId(1);

		try
		{
			specService.addSpec(spec);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddSpec");
		}
	}

}
