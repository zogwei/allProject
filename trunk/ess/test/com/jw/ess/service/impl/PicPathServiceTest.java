package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.PicPath;
import com.jw.ess.service.IPicPathService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class PicPathServiceTest {

	private static final Log logger = LogFactory
		.getLog(PicPathServiceTest.class);

	private IPicPathService picPathService;

	@Before
	public void setUp() throws Exception {
		picPathService = (IPicPathService) SpringAssisant
				.getBean("picPathService");
	}
	
	@Test
	public void testGetPicPathsBy() {
		try
		{
			List<PicPath> list = picPathService.getPicPathsBy(1);
			if(list != null)
				for(PicPath p : list)
					System.out.println(p.getPicPath());
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testGetPicPathsBy");
		}
	}

	@Test
	public void testAddPicPath() {
		try
		{
			PicPath picPath = new PicPath();
			picPath.setPicPath("picPathName1.jpg");
			picPath.setFloorId(1);
			picPathService.addPicPath(picPath);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddPicPath");
		}
	}

	@Test
	public void testGetPicPathNameBy() {
		try
		{
			PicPath picPath = new PicPath();
			picPath.setPicPath("picPathName1.jpg");
			picPath.setFloorId(1);
			System.out.println(picPathService.getPicPathNameBy(picPath));
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testGetPicPathNameBy");
		}
	}

	@Test
	public void testUploadFloorImages() {
		fail("Not yet implemented");
	}

}
