package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IStorageDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;


public class StorageDaoTest {
	
    private static final Log logger = LogFactory.getLog(SupplierDaoTest.class);
	
	private IStorageDao storageDao;
	
	@Before
	public void setUp() throws Exception
	{
		storageDao = SpringAssisant.getBean(IStorageDao.class);
	}
	
	@Test
	public void testInsertStorage(){
		try 
		{
			Storage storage=new Storage();
			Floor floor = new Floor();
			floor.setId(1);
			storage.setFloor(floor);
			storage.setArea(1);
			storage.setCount(1);
			storage.setTenantId(1);
			storageDao.insertStorage(storage);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testInsertStorage");
		}
	}
	
	
	@Test
	public void testFindStorage(){
		try 
		{
			Storage storage1=storageDao.findStorage(1,1);
			System.out.println(storage1.getId()+","+storage1.getFloor().getId()
					+","+storage1.getArea()
					+","+storage1.getCount()+","+storage1.getTenantId());
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindStorage");
		}
	}
	
	@Test
	public void testUpdateStorage(){
		try 
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.TENANT_ID, 1);
			param.put(ParameterMapKeys.FLOOR_ID, 1);
			param.put(ParameterMapKeys.QUANTITY, 1);
			param.put(ParameterMapKeys.AREA, 1);
			param.put(ParameterMapKeys.COUNT, 1);
			storageDao.updateStorage(param);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testUpdateStorage");
		}
	}
	
	
	
	@Test
	public void testFindCountOfStorage()
	{
	    try 
        {
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put(ParameterMapKeys.TENANT_ID, 1);
	    	param.put(ParameterMapKeys.SPEC_ID, 1);
			param.put(ParameterMapKeys.VEIN_ID, 1);
			param.put(ParameterMapKeys.CATEGORY_ID, 1);
			param.put(ParameterMapKeys.COLOR_CODE_ID, 1);
            int total = storageDao.findCountOfStorage(param);
            System.out.println(total);
        } 
        catch (EssException e) 
        {
            logger.error(e);
            fail("failed to testFindCountOfStorage");
        }
	}
	
	@Test
	public void testFindStorages() {
		try 
		{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tenantId", 1);
			map.put("specId", 1);
			map.put("veinId", 1);
			map.put("categoryId", 1);
			map.put("colorCodeId", 1);
			map.put("pageSize", 3);
			map.put("beginIndex", 0);
			map.put("orderId", 1);
			List<StorageInfo> list=storageDao.findStorages(map);
			for(StorageInfo storageInfo:list){
				System.out.println(storageInfo.getStorage().getArea());
			}
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindStorages");
		}
	}
}
