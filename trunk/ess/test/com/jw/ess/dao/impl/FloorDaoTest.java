package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IFloorDao;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class FloorDaoTest {
	
	private static final Log logger = LogFactory.getLog(FloorDaoTest.class);

	private IFloorDao floorDao;

	@Before
	public void setUp() throws Exception {
		floorDao = SpringAssisant.getBean(FloorDao.class);
	}

	@Test
	public void testFloorsByTenantId() {
		try {
			List<Floor> floors = floorDao.findFloorsByTenantId(1);
			for (Floor f : floors) {
				System.out.println("地板id:"+f.getId()+" "+"图片名:"+f.getPicPath());
			}
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFloorsByTenantId");
		}

	}

	@Test
	public void testFindById() {
		try {
			Floor floor = floorDao.findById(1);
			System.out.println("序号:"+floor.getId()+" "+"地板名："+floor.getName()+" "+"供应商名："+floor.getSupplier().getName()
					+" "+"类别名："+floor.getCategory().getName()+" "+"规格："+floor.getSpec().getName()
					+" "+"色号名："+floor.getColorCode().getName()+" "+"纹理名："+floor.getVein().getName()
					+" "+"单价："+floor.getSellPrice()+"备注："+floor.getDesc());
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindById");
		}
	}

	@Test

	public void testInsertFloor() {
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
		floor.setIsValid(1);

		floor.setName("地板5");
		floor.setNumber("5555");
		floor.setSellPrice(20);
		floor.setSpec(spec);
		floor.setSupplier(supplier);
		floor.setTenant(tenant);
		floor.setVein(vein);
		try {
			floorDao.insertFloor(floor);
			logger.debug(floor.getId());
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testInsertFloor");
		}

		
	}

	@Test
	public void testUpdateFloor() {

		try {
			Floor floor = floorDao.findById(1);
			floor.setName("地板更新1");
			floorDao.updateFloor(floor);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testUpdateFloor");
		}
	}

	@Test
	public void testFindFloorName() {
		int tenantId =2;
		String floorName ="floor name20001";
		try {
			String matchedName = floorDao.findFloorByName(tenantId,floorName).getName();
			
			logger.debug("matched name : " + matchedName);
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindFloorName");
		}
	}


	@Test
	public void testFindFloors(){
		int tenantId = 1 ;
		int specId = 1 ;
		int veinId =1 ;
		int categoryId =1 ;
		int colorCodeId = 1 ;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.SPEC_ID, specId);
		param.put(ParameterMapKeys.VEIN_ID, veinId);
		param.put(ParameterMapKeys.CATEGORY_ID, categoryId);
		param.put(ParameterMapKeys.COLOR_CODE_ID, colorCodeId);
		param.put(ParameterMapKeys.BEGIN_INDEX, 0);
		param.put(ParameterMapKeys.PAGE_SIZE, 3);

		try
		{
			List<Floor> floors = floorDao.findFloorsBy(param);
			for (Floor floor : floors) {
				System.out.println("序号:"+floor.getId()+" "+"地板名："+floor.getName()	
						+" "+"单价："+floor.getSellPrice());
			}
			logger.debug(floors);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindFloors");
		}
	}
	
	@Test
	public void testFindCountOfFloor()
	{

		int tenantId = 1 ;
		int specId = 1 ;
		int veinId =1 ;
		int categoryId =1 ;
		int colorCodeId = 1 ;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.SPEC_ID, specId);
		param.put(ParameterMapKeys.VEIN_ID, veinId);
		param.put(ParameterMapKeys.CATEGORY_ID, categoryId);
		param.put(ParameterMapKeys.COLOR_CODE_ID, colorCodeId);
		param.put(ParameterMapKeys.BEGIN_INDEX, 5);
		param.put(ParameterMapKeys.PAGE_SIZE, 10);
		try {
			System.out.println(floorDao.findCountOfFloor(param));
		} catch (EssException e) {
			logger.error(e);
			fail("failed to testFindCountOfFloor");
		}

	}
	
	@Test
	public void testFindFloorExcludeSelf()
	{
		int tenantId = 2;
		int floorId = 3;
		String floorName = "floor name20002";

		try
		{
			String matchedName = floorDao.findFloorName(tenantId, floorId, floorName).getName();
			logger.debug("matched name : " + matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindFloorExcludeSelf");
		}
	}
}
