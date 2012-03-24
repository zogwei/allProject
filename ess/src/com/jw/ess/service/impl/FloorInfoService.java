package com.jw.ess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.service.IFloorInfoService;
import com.jw.ess.service.IFloorService;
import com.jw.ess.service.ISpecService;
import com.jw.ess.service.ISupplierService;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.info.FloorInfo;

@SuppressWarnings("unchecked")
@Service("floorInfoService")
public class FloorInfoService implements IFloorInfoService {
	
	@Resource(name="floorService")
	private IFloorService floorService;
	@Resource(name="floorCategoryService")
	private IFloorCategoryService floorCategoryService;
	@Resource(name="colorCodeService")
	private IColorCodeService colorCodeService;
	@Resource(name="specService")
	private ISpecService specService;
	@Resource(name="veinService")
	private IVeinService veinService;
	@Resource(name="supplierService")
	private ISupplierService supplierService;
	
	private Map<String, Integer> floorCategoryIds;
	private Map<String, Integer> colorCodeIds;
	private Map<String, Integer> specIds;
	private Map<String, Integer> veinIds;
	private Map<String, Integer> supplierIds;
	

	@Override
	public void init(int tenantId) throws EssException {
		if(FloorInfo.info == null) {
			FloorInfo.info = new HashMap<Integer, Map<String, List>>();
			List<FloorCategory> floorCategorys = floorCategoryService.getAllFloorCategorysByTenantId(tenantId);
			List<ColorCode> colorCodes = colorCodeService.getAllColorCodes(tenantId);
			List<Spec> specs = specService.getfindAllSpecs(tenantId);
			List<Vein> veins = veinService.getAllVeins(tenantId);
			Supplier supplier = new Supplier();
			supplier.setTenantId(tenantId);
			List<Supplier> suppliers = supplierService.getAllSupplierLike(supplier);
			
			Map<String, List> subMap = new HashMap<String, List>();
			subMap.put(ParameterMapKeys.FLOOR_CATEGORY, floorCategorys);
			subMap.put(ParameterMapKeys.COLORCODE, colorCodes);
			subMap.put(ParameterMapKeys.SPEC, specs);
			subMap.put(ParameterMapKeys.VEIN, veins);
			subMap.put(ParameterMapKeys.SUPPLIER, suppliers);
			
			FloorInfo.info.put(tenantId, subMap);
			
			System.out.println("=======================================");
			System.out.println(floorCategorys);
			System.out.println(colorCodes);
			System.out.println(specs);
			System.out.println(veins);
			System.out.println(supplier);
			System.out.println("=======================================");
		}
	}
	

	@Override
	public List getListFromInfo(int tenantId, String name) throws EssException {
		if(tenantId < 0)
			return null;
		if(FloorInfo.info == null)
			init(tenantId);
		return (List) FloorInfo.info.get(tenantId).get(name);
	}
	
	@Override
	public void destory(int tenantId) throws EssException {
		if(tenantId < 0)
			return;
		if(FloorInfo.info.containsKey(tenantId))
			FloorInfo.info = null;
	}

	@Override
	public Map<String, Integer> getColorCodeIdsMap(List<ColorCode> list) throws EssException {
		if(list == null)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(ColorCode temp : list) {
			map.put(temp.getName(), temp.getId());
		}
		return map;
	}

	@Override
	public Map<String, Integer> getFloorCategoryIdsMap(List<FloorCategory> list) throws EssException {
		if(list == null)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(FloorCategory temp : list) {
			map.put(temp.getName(), temp.getId());
		}
		return map;
	}

	@Override
	public Map<String, Integer> getSpecIdsMap(List<Spec> list) throws EssException {
		if(list == null)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(Spec temp : list) {
			map.put(temp.getName(), temp.getId());
		}
		return map;
	}

	@Override
	public Map<String, Integer> getSupplierIdsMap(List<Supplier> list) throws EssException {
		if(list == null)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(Supplier temp : list) {
			map.put(temp.getName(), temp.getId());
		}
		return map;
	}

	@Override
	public Map<String, Integer> getVeinIdsMap(List<Vein> list) throws EssException {
		if(list == null)
			return null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(Vein temp : list) {
			map.put(temp.getName(), temp.getId());
		}
		return map;
	}

	@Override
	public void setColorCodesInfo(int tenantId, List<ColorCode> list) throws EssException {
		if(tenantId < 0 || list == null) {
			return;
		}
		if(FloorInfo.info == null) {
			init(tenantId);
		}
		if(FloorInfo.info.get(tenantId).get(ParameterMapKeys.COLORCODE) == null) {
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.COLORCODE, list);
		} else {
			FloorInfo.info.get(tenantId).get(ParameterMapKeys.COLORCODE).clear();
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.COLORCODE, list);
		}
	}

	@Override
	public void setFloorCategorysInfo(int tenantId, List<FloorCategory> list) throws EssException {
		if(tenantId < 0 || list == null) {
			return;
		}
		if(FloorInfo.info == null) {
			init(tenantId);
		}
		if(FloorInfo.info.get(tenantId).get(ParameterMapKeys.FLOOR_CATEGORY) == null) {
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.FLOOR_CATEGORY, list);
		} else {
			FloorInfo.info.get(tenantId).get(ParameterMapKeys.FLOOR_CATEGORY).clear();
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.FLOOR_CATEGORY, list);
		}
	}

	@Override
	public void setSpecsInfo(int tenantId, List<Spec> list) throws EssException {
		if(tenantId < 0 || list == null) {
			return;
		}
		if(FloorInfo.info == null) {
			init(tenantId);
		}
		if(FloorInfo.info.get(tenantId).get(ParameterMapKeys.SPEC) == null) {
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.SPEC, list);
		} else {
			FloorInfo.info.get(tenantId).get(ParameterMapKeys.SPEC).clear();
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.SPEC, list);
		}
	}

	@Override
	public void setSuppliersInfo(int tenantId, List<Supplier> list) throws EssException {
		if(tenantId < 0 || list == null) {
			return;
		}
		if(FloorInfo.info == null) {
			init(tenantId);
		}
		if(FloorInfo.info.get(tenantId).get(ParameterMapKeys.SUPPLIER) == null) {
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.SUPPLIER, list);
		} else {
			FloorInfo.info.get(tenantId).get(ParameterMapKeys.SUPPLIER).clear();
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.SUPPLIER, list);
		}
	}

	@Override
	public void setVeinsInfo(int tenantId, List<Vein> list) throws EssException {
		if(tenantId < 0 || list == null) {
			return;
		}
		if(FloorInfo.info == null) {
			init(tenantId);
		}
		if(FloorInfo.info.get(tenantId).get(ParameterMapKeys.VEIN) == null) {
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.VEIN, list);
		} else {
			FloorInfo.info.get(tenantId).get(ParameterMapKeys.VEIN).clear();
			FloorInfo.info.get(tenantId).put(ParameterMapKeys.VEIN, list);
		}
	}


	/**
	 * 批量插入地板信息处理
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addFloorsInfo(Tenant tenant, List<Floor> floors) throws EssException {
		int tenantId = tenant.getId();
		floorCategoryIds =  getFloorCategoryIdsMap(getListFromInfo(tenantId, ParameterMapKeys.FLOOR_CATEGORY));
		colorCodeIds = getColorCodeIdsMap(getListFromInfo(tenantId, ParameterMapKeys.COLORCODE));
		specIds = getSpecIdsMap(getListFromInfo(tenantId, ParameterMapKeys.SPEC));
		veinIds = getVeinIdsMap(getListFromInfo(tenantId, ParameterMapKeys.VEIN));
		supplierIds = getSupplierIdsMap(getListFromInfo(tenantId, ParameterMapKeys.SUPPLIER));
		
		for(Floor floor : floors) {
			floor.setTenant(tenant);
			
			if(floorCategoryIds.containsKey(floor.getCategory().getName())) 
			{
				floor.getCategory().setId(floorCategoryIds.get(floor.getCategory().getName()));
			} 
			else if(StringUtils.isNotBlank(floor.getCategory().getName())) 
			{
				addFloorCategoryInfo(tenantId, floor.getCategory().getName());
				floor.getCategory().setId(floorCategoryIds.get(floor.getCategory().getName()));
			}
			if(colorCodeIds.containsKey(floor.getColorCode().getName())) 
			{
				floor.getColorCode().setId(colorCodeIds.get(floor.getColorCode().getName()));
			} 
			else if(StringUtils.isNotBlank(floor.getColorCode().getName())) 
			{
				addColorCodeInfo(tenantId, floor.getColorCode().getName());
				floor.getColorCode().setId(colorCodeIds.get(floor.getColorCode().getName()));
			}
			if(specIds.containsKey(floor.getSpec().getName())) 
			{
				floor.getSpec().setId(specIds.get(floor.getSpec().getName()));
			} 
			else if(StringUtils.isNotBlank(floor.getSpec().getName())) 
			{
				addSpecInfo(tenantId, floor.getSpec().getName());
				floor.getSpec().setId(specIds.get(floor.getSpec().getName()));
			}
			if(veinIds.containsKey(floor.getVein().getName())) 
			{
				floor.getVein().setId(veinIds.get(floor.getVein().getName()));
			} 
			else if(StringUtils.isNotBlank(floor.getVein().getName())) 
			{
				addVeinInfo(tenantId, floor.getVein().getName());
				floor.getVein().setId(veinIds.get(floor.getVein().getName()));
			}
			if(supplierIds.containsKey(floor.getSupplier().getName())) 
			{
				floor.getSupplier().setId(supplierIds.get(floor.getSupplier().getName()));
			} 
			else if(StringUtils.isNotBlank(floor.getSupplier().getName())) 
			{
				addSupplierInfo(tenantId, floor.getSupplier().getName());
				floor.getSupplier().setId(supplierIds.get(floor.getSupplier().getName()));
			}
			
			floorService.addFloor(floor, null);
			
		}
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addColorCodeInfo(int tenantId, String colorCodeName)
			throws EssException {
		ColorCode colorCode = new ColorCode();
		colorCode.setTenantId(tenantId);
		colorCode.setName(colorCodeName);
		colorCodeService.addColorCode(colorCode);
		setColorCodesInfo(tenantId, colorCodeService.getAllColorCodes(tenantId));
		if(colorCodeIds != null)
			colorCodeIds = getColorCodeIdsMap(getListFromInfo(tenantId, ParameterMapKeys.COLORCODE));
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addFloorCategoryInfo(int tenantId, String floorCategoryName)
			throws EssException {
		FloorCategory floorCategory = new FloorCategory();
		floorCategory.setTenantId(tenantId);
		floorCategory.setName(floorCategoryName);
		floorCategoryService.addFloorCategory(floorCategory);
		setFloorCategorysInfo(tenantId, floorCategoryService.getAllFloorCategorysByTenantId(tenantId));
		if(floorCategoryIds != null)
			floorCategoryIds = getFloorCategoryIdsMap(getListFromInfo(tenantId, ParameterMapKeys.FLOOR_CATEGORY));
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addSpecInfo(int tenantId, String specName) throws EssException {
		Spec spec = new Spec();
		spec.setTenantId(tenantId);
		spec.setName(specName);
		specService.addSpec(spec);
		setSpecsInfo(tenantId, specService.getfindAllSpecs(tenantId));
		if(specIds != null)
			specIds = getSpecIdsMap(getListFromInfo(tenantId, ParameterMapKeys.SPEC));
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addSupplierInfo(int tenantId, String supplierName)
			throws EssException {
		Supplier supplier = new Supplier();
		supplier.setTenantId(tenantId);
		supplier.setName(supplierName);
		supplierService.addSupplier(supplier);
		
		supplier = new Supplier();
		supplier.setTenantId(tenantId);
		setSuppliersInfo(tenantId, supplierService.getAllSupplierLike(supplier));
		if(supplierIds != null)
			supplierIds = getSupplierIdsMap(getListFromInfo(tenantId, ParameterMapKeys.SUPPLIER));
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addVeinInfo(int tenantId, String veinName) throws EssException {
		Vein vein = new Vein();
		vein.setTenantId(tenantId);
		vein.setName(veinName);
		veinService.addVein(vein);
		setVeinsInfo(tenantId, veinService.getAllVeins(tenantId));
		if(veinIds != null)
			veinIds = getVeinIdsMap(getListFromInfo(tenantId, ParameterMapKeys.VEIN));
		
	}
	

}
