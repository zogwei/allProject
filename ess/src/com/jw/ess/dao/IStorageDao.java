package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.ex.EssException;

public interface IStorageDao {
	
    /**
     * 插入一条库存信息
     * @param storage
     * @throws EssException
     */
    void insertStorage(Storage storage) throws EssException;
	
	/**
	 * 根据租户id以及地板id查询库存信息 (利用租户id以及地板id确定改库存信息的唯一性)
	 * 方法用于：返回要查询的地板数量和返回库存信息
	 * @param storage 保存了租户id和地板id
	 * @return 
	 * @throws EssException
	 */
	Storage findStorage(int tenantId, int floorId) throws EssException;
	
	/**
	 * 更新一条库存信息
	 * @param storage
	 * @throws EssException
	 */
	void updateStorage(Map<String, Object> map) throws EssException;
	
	/**
	 * 根据租户id统计库存总记录数
	 * @param tenantId
	 * @return
	 * @throws EssException
	 */
	int findCountOfStorage(Map<String, Object> map) throws EssException;

	/**
	 * 根据租户id以及分页显示查询库存信息
	 * @param Map(int tenantId,int employeeId,int beginIndex,int pageNum)
	 * @return
	 * @throws EssException
	 */
	List<StorageInfo> findStorages(Map<String, Object> map) throws EssException;
	
	/**
	 * 库存详细信息
	 * @param floorId, orderId
	 * @return storage
	 * @throws EssException
	 */
	StorageInfo findStorageInfo(int tenantId,int floorId ) throws EssException;
}
