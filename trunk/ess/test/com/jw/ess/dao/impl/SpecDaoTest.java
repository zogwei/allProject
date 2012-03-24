package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.ISpecDao;
import com.jw.ess.entity.Spec;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class SpecDaoTest
{
	private static final Log logger = LogFactory.getLog(SpecDaoTest.class);

	private ISpecDao specDao;

	@Before
	public void setUp() throws Exception
	{
		specDao = SpringAssisant.getBean(SpecDao.class);
	}

	@Test
	public void testInsertSpec()
	{
		Spec spec = new Spec();
		spec.setName("900*50*18");
		spec.setIsValid(1);
		spec.setTenantId(1);

		try
		{
			specDao.insertSpec(spec);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertSpec");
		}
	}

	@Test
	public void testFindAllSpecs()
	{
		int tenantId = 1;
		List<Spec> specs;
		try
		{
			specs = specDao.findAllSpecs(tenantId);
			logger.debug(specs);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertSpec");
		}
	}

	@Test
	public void testFindSpecName()
	{
		int tenantId = 1;
		String specName = "900*50*18";
		try
		{
			String matchedSpecName = specDao.findSpecName(tenantId, specName);
			logger.debug(matchedSpecName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertSpec");
		}
	}

}
