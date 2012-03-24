package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IFloorService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public class FloorServiceTest {

	private static final Log logger = LogFactory.getLog(FloorServiceTest.class);

	private IFloorService floorService;

	@Before
	public void setUp() throws Exception {
		floorService = (IFloorService) SpringAssisant.getBean("floorService");
	}

	@Test
	public void testAddFloor() {
		Floor floor = new Floor();
		FloorCategory category = new FloorCategory();
		category.setId(1);
		ColorCode colorCode = new ColorCode();
		colorCode.setId(1);
		Spec spec = new Spec();
		spec.setId(1);
		Supplier supplier = new Supplier();
		supplier.setId(1);
		Vein vein = new Vein();
		vein.setId(1);
		Tenant tenant = new Tenant();
		tenant.setId(1);
		floor.setCategory(category);
		floor.setColorCode(colorCode);
		floor.setCreatedDate(DateUtil.currentTimeSecs());
		floor.setDesc("");
		floor.setIsValid(CommonConstant.STATE_VALID);
		floor.setName("地板");
		floor.setNumber("11");
		floor.setSellPrice(20);
		floor.setSpec(spec);
		floor.setSupplier(supplier);
		floor.setTenant(tenant);
		floor.setVein(vein);

		try {
			floorService.addFloor(floor, null);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testAddFloor");
		}

	}

	@Test
	public void testGetFloorById() {
		Floor floor = new Floor();
		try {
			floor = floorService.getFloorById(2);
			System.out.println("序号:"+floor.getId()+" "+"编号"+floor.getNumber()+" "+"地板名："+floor.getName()+" "+"供应商名："+floor.getSupplier().getName()
					+" "+"类别名："+floor.getCategory().getName()+" "+"规格："+floor.getSpec().getName()
					+" "+"色号名："+floor.getColorCode().getName()+" "+"纹理名："+floor.getVein().getName()
					+" "+"单价："+floor.getSellPrice()+"备注："+floor.getDesc());
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testGetFloorById");
		}
	}

	@Test
	public void testModifyFloor() {
		try {
			Floor floor = new Floor();
			Tenant tenant = new Tenant();
			Spec spec = new Spec();
			Supplier supplier = new Supplier();
			FloorCategory category = new FloorCategory();
			ColorCode colorCode = new ColorCode();
			Vein vein = new Vein();
			vein.setId(1);
			tenant.setId(1);
			spec.setId(1);
			supplier.setId(1);
			category.setId(1);
			colorCode.setId(1);
			floor.setId(1);
			floor.setVein(vein);
			floor.setTenant(tenant);
			floor.setSpec(spec);
			floor.setSupplier(supplier);
			floor.setCategory(category);
			floor.setColorCode(colorCode);
			floor.setCreatedDate(DateUtil.currentTimeSecs());
			floor.setIsValid(1);
			floor.setNumber("kkkk");
			floor.setName("地板更新2");
			
			floorService.modifyFloor(floor, null);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testModifyFloor");
		}

	}

	@Test
	public void testFindFloorsByTenantId() {
		try {
			List<Floor> floors = floorService.getFloorsByTenantId(1);
			for (Floor f : floors) {
				System.out.println(f.getId());
				System.out.println(f.getName());
				System.out.println(f.getPicPath());
				System.out.println(f.getTenant().getName());
				
			}
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindAllFloors");
		}
	}

	
	@Test
	public void testGetFloors() {
		
		try {
			Floor floor = new Floor();
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(5);
			FloorCategory category = new FloorCategory();
//			category.setId(1);
			ColorCode colorCode = new ColorCode();
//			colorCode.setId(1);
			Vein vein = new Vein();
//			vein.setId(1);
			Spec spec = new Spec();
//			spec.setId(1);
			Tenant tenant = new Tenant();
			tenant.setId(1);
			
			floor.setTenant(tenant);
			floor.setCategory(category);
			floor.setColorCode(colorCode);
			floor.setVein(vein);
			floor.setSpec(spec);
			PageSupport<Floor> list = floorService.getFloorsBy(floor, page);
			System.out.println(list);
			for (Floor f : list.getResult()) {
				System.out.println(f.getSpec().getName()+" "+f.getId() + " "+ f.getName() + " "	+ f.getSellPrice()
						);
			}

		} catch (EssException e) {
			logger.error(e);
            fail("failed to testGetFloors");
		}

	}
	
	@Test
	public void testGetFloorsByName() {
		
		try {
			Floor floor = new Floor();
			floor.setName("floor name20001");
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(5);
			Tenant tenant = new Tenant();
			tenant.setId(2);
			
			floor.setTenant(tenant);
			PageSupport<Floor> list = floorService.getFloorsBy(floor, page);
			System.out.println(list);
			for (Floor f : list.getResult()) {
				System.out.println(f.getId() + " "+ f.getName() + " "	+ f.getSellPrice());
			}

		} catch (EssException e) {
			logger.error(e);
            fail("failed to testGetFloorsByName");
		}

	}

}
