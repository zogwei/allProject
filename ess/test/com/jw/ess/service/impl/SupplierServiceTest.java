package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Supplier;
import com.jw.ess.service.ISupplierService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class SupplierServiceTest {
	private static final Log logger = LogFactory
			.getLog(SupplierServiceTest.class);

	private ISupplierService supplierService;

	@Before
	public void setUp() throws Exception {
		supplierService = (ISupplierService) SpringAssisant.getBean("supplierService");
	}

	@Test
	public void testAddSupplier() {

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
			supplier.setTenantId(1);
			supplierService.addSupplier(supplier);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testAddSupplier");
		}
	}

	@Test
	public void testGetSupplier() {

		try 
		{
			Supplier supplier = supplierService.getSupplier(1);
			System.out.println(supplier.getId() + "," + supplier.getName()
					+ "," + supplier.getLinkman() + "," + supplier.getPhone()
					+ "," + supplier.getAddress() + "," + supplier.getIsValid()
					+ "," + supplier.getCreatedDate() + ","
					+ supplier.getDesc() + "," + supplier.getTenantId());
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetAllSupplier");
		}
	}

	@Test
	public void testModifySupplier() {

		try 
		{
			Supplier supplier = new Supplier();
			supplier.setId(1);
			supplier.setName("1");
			supplier.setLinkman("1");
			supplier.setPhone("136561");
			supplier.setAddress("深圳");
			supplier.setIsValid(1);
			supplier.setCreatedDate(DateUtil.currentTimeSecs());
			supplier.setDesc("1");
			supplier.setTenantId(2);
			supplierService.modifySupplier(supplier);
		}
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testModifySupplier");
		}
	}
	
	@Test
	public void testGetAllSupplierLike() {
		try 
		{
			Supplier supplier = new Supplier();
			//supplier.setName("明");
			supplier.setTenantId(1);
			List<Supplier> list=supplierService.getAllSupplierLike(supplier);
			for(Supplier s:list){
				System.out.println(s.getName());
			}
			
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetAllSupplierLike");
		}
	}
	
}
