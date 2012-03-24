package com.jw.ess.service;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public interface IStorageService {
	
    /**
     * 增加一条库存信息
     * @param storage
     * @throws EssException
     */
	void addStorage(Storage storage) throws EssException;
	
	/**
	 * 添加订单的时候，相应更改库存信息
	 * @param order
	 * @throws EssException
	 */
	void modifyStorageByOrderAdd(Order order) throws EssException;
	
	/**
	 * 退货的时候，相应更改库存信息
	 * @param order
	 * @throws EssException
	 */
	void modifyStorageByOrderCancel(Order order) throws EssException;
	
	/**
	 * 当入库的时候更改库存信息
	 * @param inStorage
	 * @throws EssException
	 */
	void modifyStorageByInStorageAdd(InStorage inStorage) throws EssException;
	
    /**
     * 根据租户id和分页信息查找库存中地板的基本信息
     * @param tenantId
     * @param page
     * @return PageSupport中保存有List<Storage>的结果集，
     *         该结果集中只初始化的Storage类中的Floor的对象
     *         (用于返回列表显示只显示地板编号，地板名称，规格型号)
     * @throws EssException
     */
    PageSupport<StorageInfo> getStorages(Floor floor, Page page) throws EssException;
    
    /**
     * 根据租户id和要查找库存中地板的id查找该库存的完整信息
     * @param tenantId
     * @param page
     * @return 返回一个对象其中包含库存表的详细信息以及相对应地板的详细信息
     *         初始化Storage的所有对象，包括里面的Floor对象
     * @throws EssException
     */
    StorageInfo getStorageDetail(int tenantId,int floorId) throws EssException;
    
    Storage getStorage(int tenantId,int floorId) throws EssException;
}
