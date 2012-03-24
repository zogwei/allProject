package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IVeinDao;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class VeinDaoTest
{
	private static final Log logger = LogFactory.getLog(VeinDaoTest.class);

	private IVeinDao veinDao;

	@Before
	public void setUp() throws Exception
	{
		veinDao = SpringAssisant.getBean(VeinDao.class);
	}

	@Test
	public void testFindAllVeins()
	{
		int tenantId = 1;
		try
		{
			List<Vein> veins = veinDao.findAllVeins(tenantId);
			logger.debug(veins);
		}
		catch (EssException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testFindVeinName()
	{
		int tenantId = 1;
		String veinName = "直纹";
		try
		{
			String matchedName = veinDao.findVeinName(tenantId, veinName);
			logger.debug(matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindVeinName");
		}
	}

	@Test
	public void testInsertVein()
	{
		Vein vein = new Vein();
		vein.setName("豹纹");
		vein.setTenantId(1);
		vein.setIsValid(1);

		try
		{
			veinDao.insertVein(vein);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertVein");
		}
	}

}
