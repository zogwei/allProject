/**
 * 
 */
package com.jw.ess.dao.impl;


import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IPicPathDao;
import com.jw.ess.entity.PicPath;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class PicPathDaoTest {
	
	private IPicPathDao picPathDao;
	
	private static final Log logger = LogFactory.getLog(PicPathDaoTest.class);

	@Before
	public void setUp() throws Exception {
		picPathDao = SpringAssisant.getBean(PicPathDao.class);
	}
	
	@Test
	public void testInsertPicPath()
	{
		PicPath picPath = new PicPath();
		picPath.setPicPath("2.jpg");
		picPath.setFloorId(2);
		
		try
		{
			picPathDao.insertPicPath(picPath);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertPicPath");
		}
	}
	
	@Test
	public void testFindPicPathNameBy()
	{
		PicPath picPath = new PicPath();
		picPath.setFloorId(1);
		picPath.setPicPath("3.jpg");
		try
		{
			System.out.println(picPathDao.findPicPathNameBy(picPath));
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindPicPathNameBy");
		}
	}
	
	@Test
	public void testFindPicPathsByFloorId(){
		try{
			List<PicPath> picPaths = picPathDao.findPicPathsBy(1);
			for (PicPath picPath : picPaths) {
				System.out.println(picPath.getPicPath());
			}
		}catch(EssException e)
		{
			logger.error(e);
			fail("failed to testFindPicPathsByFloorId");
		}
	}

}
