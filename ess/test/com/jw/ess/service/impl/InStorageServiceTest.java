package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.InStorage;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IInStorageService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public class InStorageServiceTest {

	private static final Log logger = LogFactory
			.getLog(InStorageServiceTest.class);

	private IInStorageService inStorageService;

	@Before
	public void setUp() throws Exception {
		inStorageService = (IInStorageService) SpringAssisant
				.getBean("inStorageService");
	}

	@Test
	public void testAddInStorage() {

		try 
		{
			InStorage inStorage = new InStorage();
			Floor floor = new Floor();
			floor.setId(3);
			inStorage.setFloor(floor);
			inStorage.setLength(2);
			inStorage.setWidth(333);
			inStorage.setQuantity(1);
			inStorage.setArea(20);
			inStorage.setCount(20);
			inStorage.setOperator("是1111");
			inStorage.setIsValid(1);
			inStorage.setCreatedDate(DateUtil.currentTimeSecs());
			inStorage.setTenantId(1);
			inStorageService.addInStorage(inStorage);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testAddInStorage");
		}
	}

	@Test
	public void testGetInStorage() {

		try 
		{
			InStorage inStorage = inStorageService.getInStorage(1);
			System.out.println(inStorage.getId() 
					+ "," + inStorage.getFloor().getId()+ ","
					+ inStorage.getLength() + "," + inStorage.getWidth() + ","
					+ inStorage.getQuantity() + "," + inStorage.getArea() + ","
					+ inStorage.getPrice() + "," + inStorage.getCount() + ","
					+ inStorage.getOperator() + "," + inStorage.getIsValid()
					+ ',' + inStorage.getCreatedDate() + ","
					+ inStorage.getDesc() + "," + inStorage.getTenantId());
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetInStorage");
		}
	}

	@Test
	public void testModifyInStorage() {

		try 
		{
			InStorage inStorage = new InStorage();
			inStorage.setId(1);
			Floor floor = new Floor();
			floor.setId(2);
			inStorage.setFloor(floor);
			inStorage.setLength(200);
			inStorage.setWidth(56);
			inStorage.setQuantity(1);
			inStorage.setArea(1);
			inStorage.setCount(1);
			inStorage.setOperator("是");
			inStorage.setIsValid(1);
			inStorage.setCreatedDate(DateUtil.currentTimeSecs());
			inStorage.setTenantId(1);
			inStorageService.modifyInStorage(inStorage);
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testUpdateInStorage");
		}
	}
	
	@Test
	public void testGetInStoragesBy() {

		try 
		{
			Floor floor = new Floor();
			Supplier supplier = new Supplier();
			supplier.setId(1);
			ColorCode colorCode = new ColorCode();
			colorCode.setId(1);
			Spec spec = new Spec();
			spec.setId(1);
			Vein vein = new Vein();
			vein.setId(1);
//			
			Tenant tenant = new Tenant();
			tenant.setId(2);
//			
//			FloorCategory category =new FloorCategory();
//			category.setId(1);
//			floor.setSupplier(supplier);
			floor.setTenant(tenant);
//			floor.setColorCode(colorCode);
//			floor.setSpec(spec);
//			floor.setVein(vein);
//			floor.setCategory(category);
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(3);
			PageSupport<InStorage> pageSupport = inStorageService.getInStoragesBy(
					floor, 315504000, 1356710400, page);
			for(InStorage inStorage:pageSupport.getResult()){
				System.out.println(inStorage.getArea());
			}
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetInStoragesBy");
		}
	}
	
	
	@Test
	public void testGetInStorages() {

		try 
		{
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(3);
			PageSupport<InStorage> pageSupport = inStorageService.getInStorages(1, page);
			for(InStorage inStorage:pageSupport.getResult()){
				System.out.println(inStorage.getArea());
			}
		} 
		catch (EssException e) 
		{
			logger.error(e);
			fail("failed to testGetInStorages");
		}
	}
}
