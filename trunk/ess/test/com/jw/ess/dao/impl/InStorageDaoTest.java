package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IInStorageDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class InStorageDaoTest {

	private static final Log logger = LogFactory.getLog(InStorageDaoTest.class);

	private IInStorageDao inStorageDao;

	@Before
	public void setUp() throws Exception {
		inStorageDao = SpringAssisant.getBean(IInStorageDao.class);
	}

	@Test
	public void testInsertInStorage() {
		try {
			InStorage inStorage = new InStorage();
			Floor floor = new Floor();
			floor.setId(1);
			inStorage.setFloor(floor);
			inStorage.setLength(2);
			inStorage.setWidth(56);
			inStorage.setQuantity(1);
			inStorage.setArea(1);
			inStorage.setCount(1);
			inStorage.setOperator("324234");
			inStorage.setIsValid(CommonConstant.STATE_INVALID);
			inStorage.setCreatedDate(DateUtil.currentTimeSecs());
			inStorage.setDesc("1");
			inStorage.setTenantId(1);
			inStorageDao.insertInStorage(inStorage);
		} 
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertInStorage");
		}
	}

	@Test
	public void testFindInStorageBy() {
		try 
		{
			InStorage inStorage = inStorageDao.findInStorageBy(1);
			System.out.println(inStorage.getId()
					+ "," + inStorage.getFloor().getId() + ","
					+ inStorage.getLength() + "," + inStorage.getWidth() + ","
					+ inStorage.getQuantity() + "," + inStorage.getArea() + ","
					+ inStorage.getPrice() + "," + inStorage.getCount() + ","
					+ inStorage.getOperator() + "," + inStorage.getIsValid()
					+ ',' + inStorage.getCreatedDate() + ","
					+ inStorage.getDesc() + "," + inStorage.getTenantId()
					+ ',' + inStorage.getFloor().getName());
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindInStorageBy");
		}
	}

	@Test
	public void testUpdateInStorage() {
		try {
			InStorage inStorage = new InStorage();
			inStorage.setId(1);
			inStorage.setLength(2);
			inStorage.setWidth(568);
			inStorage.setQuantity(10);
			inStorage.setArea(10);
			inStorage.setCount(10);
			inStorage.setOperator("是");
			inStorage.setDesc("");
			inStorageDao.updateInStorage(inStorage);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testUpdateInStorage");
		}
	}
	
	@Test
	public void testFindCountOfInStorage() {
		try 
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tenantId", 1);
//			map.put("firstDate", 1);
//			map.put("lastDate", 1999999999);
//			map.put("supplierId", 1);
//			map.put("specId", 1);
//			map.put("veinId", 1);
//			map.put("categoryId", 1);
//			map.put("colorCodeId", 1);
			int i = inStorageDao.findCountOfInStorage(map);
			System.out.println(i);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindCountOfInStorage");
		}
	}
	
	@Test
	public void testFindInStoragesBy() {
		try 
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tenantId", 1);
			map.put("firstDate", 1);
			map.put("lastDate", 1999999999);
			map.put("supplierId", 1);
			map.put("specId", 1);
			map.put("veinId", 1);
			map.put("categoryId", 1);
			map.put("colorCodeId", 1);
			map.put("beginIndex", 0);
			map.put("pageSize", 3);
			List<InStorage> list=inStorageDao.findInStoragesBy(map);
			for(InStorage inStorage:list){
				System.out.println(inStorage.getFloor().getId());
			}
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindInStoragesBy");
		}
	}
	
}
