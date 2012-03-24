package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class ColorCodeServiceTest
{
	private static final Log logger = LogFactory
			.getLog(ColorCodeServiceTest.class);

	private IColorCodeService colorCodeService;

	@Before
	public void setUp() throws Exception
	{
		colorCodeService = (IColorCodeService) SpringAssisant
				.getBean("colorCodeService");
	}

	@Test
	public void testAddColorCode()
	{
		ColorCode colorCode = new ColorCode();
		colorCode.setName("colorCode2");
		colorCode.setTenantId(1);
		colorCode.setIsValid(1);

		try
		{
			colorCodeService.addColorCode(colorCode);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddColorCode");
		}
	}

}
