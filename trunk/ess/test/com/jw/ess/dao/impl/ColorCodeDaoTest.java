package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IColorCodeDao;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class ColorCodeDaoTest
{
	private static final Log logger = LogFactory.getLog(ColorCodeDaoTest.class);

	private IColorCodeDao colorCodeDao;

	@Before
	public void setUp() throws Exception
	{
		colorCodeDao = SpringAssisant.getBean(ColorCodeDao.class);
	}

	@Test
	public void testInsertColorCode()
	{
		ColorCode colorCode = new ColorCode();
		colorCode.setName("colorCode");
		colorCode.setTenantId(1);
		colorCode.setIsValid(1);

		try
		{
			colorCodeDao.insertColorCode(colorCode);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertColorCode");
		}
	}

	@Test
	public void testFindAllColorCodes()
	{
		int tenantId = 1;
		try
		{
			List<ColorCode> colorCodes = colorCodeDao
					.findAllColorCodes(tenantId);
			logger.debug(colorCodes);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindAllColorCodes");
		}
	}

	@Test
	public void testFindColorCodeName()
	{
		int tenantId = 1;
		String colorCodeName = "colorCode1";
		try
		{
			String matchedName = colorCodeDao.findColorCodeName(tenantId,
					colorCodeName);
			logger.debug(matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindColorCodeName");
		}
	}

}
