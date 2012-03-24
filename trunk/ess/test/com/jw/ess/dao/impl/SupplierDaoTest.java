package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.ISupplierDao;
import com.jw.ess.entity.Supplier;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;



public class SupplierDaoTest {
	private static final Log logger = LogFactory.getLog(SupplierDaoTest.class);
	
	private ISupplierDao supplierDao;
	
	@Before
	public void setUp() throws Exception
	{
		supplierDao = SpringAssisant.getBean(SupplierDao.class);
	}
	
	@Test
	public void testInsertSupplier(){
		try 
		{
			Supplier supplier=new Supplier();
			supplier.setName("黎明");
			supplier.setLinkman("中华");
			supplier.setPhone("1365687");
			supplier.setAddress("深圳");
			supplier.setIsValid(1);
			supplier.setCreatedDate(DateUtil.currentTimeSecs());
			supplier.setDesc("1");
			supplier.setTenantId(2);
			supplierDao.insertSupplier(supplier);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testInsertSupplier");
		}
	}
	
	@Test
	public void testFindSupplierBy(){
		try 
		{
			Supplier supplier=supplierDao.findSupplierBy(1);
			System.out.println(supplier.getId()+","+supplier.getName()
					+","+supplier.getAddress()+","+supplier.getDesc());
		} 
		catch (EssException e) {
			logger.error(e);
			fail("failed to testfindSupplier");
		}
	}
	
	
	@Test
	public void testUpdateSupplier(){
		try 
		{
			Supplier supplier=new Supplier();
			supplier.setId(1);
			supplier.setName("小明");
			supplier.setLinkman("黄晓东");
			supplier.setPhone("1235458");
			supplier.setAddress("广东");
			supplier.setIsValid(2);
			supplier.setCreatedDate(DateUtil.currentTimeSecs());
			supplier.setDesc("1");
			supplier.setTenantId(2);
			supplierDao.updateSupplier(supplier);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testUpdateSupplier");
		}
	}
	
	@Test
	public void testFindLikeSupplier(){
		try 
		{
			Supplier supplier=new Supplier();
			supplier.setName("明");
			supplier.setTenantId(2);
			List<Supplier> list = supplierDao.findSupplierLikeBy(supplier);
			for(Supplier s:list){
				System.out.println(s.getName());
			}
			
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testFindLikeSupplier");
		}
	}
	
	@Test
	public void testFindSupplier(){
		try 
		{
			Supplier supplier=new Supplier();
//			supplier.setName("小明");
			supplier.setTenantId(2);
//			if(supplierDao.findSupplier(supplier) !=null){
				Supplier SupplierTest = supplierDao.findSupplier(supplier);
			    System.out.println(SupplierTest.getName()+"/"+SupplierTest.getDesc());
//			}
		} 
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindSupplier");
		}
	}
	
}
