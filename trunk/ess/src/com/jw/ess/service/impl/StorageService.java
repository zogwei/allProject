package com.jw.ess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IStorageDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.service.IStorageInfoService;
import com.jw.ess.service.IStorageService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

@Service("storageService")
public class StorageService implements IStorageService{
	
	private static final Log logger = LogFactory.getLog(StorageService.class);
	
	@Resource(name="storageDao")
	private IStorageDao storageDao;
	
	@Resource(name="storageInfoService")
	private IStorageInfoService storageInfoService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addStorage(Storage storage) throws EssException {
		if((storageDao.findStorage(storage.getTenantId(),storage.getFloor().getId()))!=null){
			logger.error("storage floorId is already exists");
			throw new EssException("storage floorId is already exists", 
					MessageCode.STORAGE_FLOORID_ALREADY_EXISTS);
		}
		storageDao.insertStorage(storage);
	}

    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
    public PageSupport<StorageInfo> getStorages(Floor floor, Page page)
            throws EssException
    {
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put(ParameterMapKeys.TENANT_ID, floor.getTenant().getId());
    	param.put(ParameterMapKeys.SPEC_ID, getSpecId(floor));
		param.put(ParameterMapKeys.VEIN_ID, getVeinId(floor));
		param.put(ParameterMapKeys.CATEGORY_ID, getCategoryId(floor));
		param.put(ParameterMapKeys.COLOR_CODE_ID, getColorCodeId(floor));
		
		PageSupport<StorageInfo> ps = new PageSupport<StorageInfo>();
		ps.setCurrentPage(page.getCurrentPage());
		ps.setPageSize(page.getPageSize());
		//get floor count
		int count = storageDao.findCountOfStorage(param);
		ps.setCount(count);
		//check count equals 0 or not
		if(count !=0){
			param.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			param.put(ParameterMapKeys.PAGE_SIZE, ps.pageSize);
			List<StorageInfo> storagesInfo = storageDao.findStorages(param);
			ps.setResult(storagesInfo);
		}
        return ps;
    }

    @Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
    public StorageInfo getStorageDetail(int tenantId,int floorId)
            throws EssException
    {
    	StorageInfo storageInfo = storageDao.findStorageInfo(tenantId, floorId);
        return storageInfo;
    }

	
	//判断spec对象是否为空
	public int getSpecId(Floor floor){
		if(floor.getSpec()==null){
			return 0;
		}
		else
		{
			return floor.getSpec().getId();
		}
	}
	
	//判断vein对象是否为空
	public int getVeinId(Floor floor){
		if(floor.getVein()==null){
			return 0;
		}
		else
		{
			return floor.getVein().getId();
		}
	}
	
	//判断category对象是否为空
	public int getCategoryId(Floor floor){
		if(floor.getCategory()==null){
			return 0;
		}
		else
		{
			return floor.getCategory().getId();
		}
	}
	
	//判断colorCode对象是否为空
	public int getColorCodeId(Floor floor){
		if(floor.getColorCode()==null){
			return 0;
		}
		else
		{
			return floor.getColorCode().getId();
		}
	}

	//下订单更新库存数据
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyStorageByOrderAdd(Order order)
			throws EssException {
		
		
		List<OrderItem> orderItems = order.getItems();
		
		for(OrderItem orderItem:orderItems){
			//更新时必填
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, orderItem.getFloor().getId());
			param.put(ParameterMapKeys.AREA, -(orderItem.getArea()));
			param.put(ParameterMapKeys.COUNT, -(orderItem.getAmount()));
			storageDao.updateStorage(param);
			storageInfoService.modifyCountOrder(orderItem.getFloor().getId(), 1);
			
		}
	}
	
	//入库更新库存数据
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyStorageByInStorageAdd(InStorage inStorage) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		
		//更新时必填
		param.put(ParameterMapKeys.FLOOR_ID, inStorage.getFloor().getId());
		param.put(ParameterMapKeys.QUANTITY, inStorage.getQuantity());
		param.put(ParameterMapKeys.AREA, inStorage.getArea());
		param.put(ParameterMapKeys.COUNT, inStorage.getCount());
		storageDao.updateStorage(param);
	}

	//退货时更新库存数据
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyStorageByOrderCancel(Order order) throws EssException {
		
		List<OrderItem> orderItems = order.getItems();
		System.out.println(orderItems);
		for(OrderItem orderItem:orderItems){
			//更新时必填
			Map<String, Object> param = new HashMap<String, Object>();
			param.put(ParameterMapKeys.FLOOR_ID, orderItem.getFloor().getId());
			param.put(ParameterMapKeys.AREA, orderItem.getArea());
			param.put(ParameterMapKeys.COUNT, orderItem.getAmount());
			storageDao.updateStorage(param);
			storageInfoService.modifyCountOrderCancel(orderItem.getFloor().getId(), 1);
		}
	}

	@Override
	public Storage getStorage(int tenantId, int floorId) throws EssException {
		Storage storage = storageDao.findStorage(tenantId, floorId);
		return storage;
	}
	
}
