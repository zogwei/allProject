package com.jw.ess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IInStorageDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.entity.Storage;
import com.jw.ess.service.IInStorageService;
import com.jw.ess.service.IStorageInfoService;
import com.jw.ess.service.IStorageService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

@Service("inStorageService")
public class InStorageService implements IInStorageService{
	
	@Resource(name="inStorageDao")
	private IInStorageDao inStorageDao;
	
	@Resource(name="storageService")
	private IStorageService storageService;
	
	@Resource(name="storageInfoService")
	private IStorageInfoService storageInfoService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addInStorage(InStorage inStorage) throws EssException {
		inStorageDao.insertInStorage(inStorage);
		if(storageInfoService.getStorageInfoBy(inStorage.getFloor().getId())==null){
			storageInfoService.addStorageInfo(inStorage.getFloor().getId(),1,0,0);
		}else
		{
			storageInfoService.modifyCountInStorage(inStorage.getFloor().getId(), 1);
		}
		if(storageService.getStorage(inStorage.getTenantId(), 
				inStorage.getFloor().getId())!=null){
			storageService.modifyStorageByInStorageAdd(inStorage);
		}else{
			Storage storage = new Storage();
			Floor floor = new Floor();
			floor.setId(inStorage.getFloor().getId());
			storage.setFloor(floor);
			storage.setTenantId(inStorage.getTenantId());
			storage.setArea(inStorage.getArea());
			storage.setCount(inStorage.getCount());
			storageService.addStorage(storage);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void modifyInStorage(InStorage inStorage) throws EssException {
		if(inStorage !=null){
			InStorage iS = new InStorage();
			iS = inStorageDao.findInStorageBy(inStorage.getId());
			
			float inStorageCount =  iS.getCount();
			int inStorageQuantity = iS.getQuantity();
			float inStorageArea = iS.getArea();
			
			
			float modifyInStorageCount = inStorage.getCount();
			int modifyInStorageQuantity = inStorage.getQuantity();
			float modifyInStorageArea = inStorage.getArea();
			//差值
			float countTotal = modifyInStorageCount - inStorageCount;
			int quantityTotal = modifyInStorageQuantity - inStorageQuantity;
			float areaTotal = modifyInStorageArea - inStorageArea;
			inStorageDao.updateInStorage(inStorage);
			inStorage.setCount(countTotal);
			inStorage.setQuantity(quantityTotal);
			inStorage.setArea(areaTotal);
			
			Floor floor = new Floor();
			floor.setId(iS.getFloor().getId());
			inStorage.setFloor(floor);
			storageService.modifyStorageByInStorageAdd(inStorage);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public InStorage getInStorage(int id) throws EssException {
		return inStorageDao.findInStorageBy(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public PageSupport<InStorage> getInStoragesBy(Floor floor,int firstDate,
			int lastDate,Page page) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, floor.getTenant().getId());
		param.put(ParameterMapKeys.FIRST_DATE, firstDate);
		param.put(ParameterMapKeys.LAST_DATE, lastDate);
		param.put(ParameterMapKeys.SUPPLIER_ID, (floor.getSupplier() == null) ? 0 : floor.getSupplier().getId());
	    param.put(ParameterMapKeys.SPEC_ID, (floor.getSpec() == null) ? 0 : floor.getSpec().getId());
		param.put(ParameterMapKeys.VEIN_ID, (floor.getVein() == null) ? 0 : floor.getVein().getId());
		param.put(ParameterMapKeys.CATEGORY_ID, (floor.getCategory() == null) ? 0 : floor.getCategory().getId());
		param.put(ParameterMapKeys.COLOR_CODE_ID, (floor.getColorCode() == null) ? 0 : floor.getColorCode().getId());
		
		PageSupport<InStorage> ps = new PageSupport<InStorage>();
		ps.setCurrentPage(page.getCurrentPage());
		ps.setPageSize(page.getPageSize());
		// get inStorage count
		int count = inStorageDao.findCountOfInStorage(param);
		ps.setCount(count);
		//check count equals 0 or not
		if (count !=0){
			param.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			param.put(ParameterMapKeys.PAGE_SIZE, ps.pageSize);
			List<InStorage> inStorages = inStorageDao.findInStoragesBy(param);
			ps.setResult(inStorages);
		}
		return ps;
	}

	@Override
	public PageSupport<InStorage> getInStorages(int tenantId, Page page)
			throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		PageSupport<InStorage> ps = new PageSupport<InStorage>();
		ps.setCurrentPage(page.getCurrentPage());
		ps.setPageSize(page.getPageSize());
		// get inStorage count
		int count = inStorageDao.findCountOfInStorage(param);
		ps.setCount(count);
		//check count equals 0 or not
		if (count !=0){
			param.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			param.put(ParameterMapKeys.PAGE_SIZE, ps.pageSize);
			List<InStorage> inStorages = inStorageDao.findInStoragesBy(param);
			ps.setResult(inStorages);
		}
		return ps;
	}

}
