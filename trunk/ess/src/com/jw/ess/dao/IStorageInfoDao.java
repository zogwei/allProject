package com.jw.ess.dao;

import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.ex.EssException;

public interface IStorageInfoDao {
	
	/**
	 * 第一次入库，累计入库次数
	 * @param  floorId(地板id) countInStorage(累计入库)
			 countOrder(累计订单) countOrderCancel(累计取消订单)
	 */
	void insertStorageInfo(int floorId,int countInStorage,
			int countOrder,int countOrderCancel) throws EssException;
	
	/**
	 * 累计入库次数
	 * @param  floorId(地板id) countInStorage(累计入库+1)
	 */
	void updateCountInStorage(int floorId,int countInStorage) throws EssException;
	
	/**
	 * 累计订单次数
	 * @param  floorId(地板id)  countOrder(累计订单+1)
	 */
	void updateCountOrder(int floorId,int countOrder) throws EssException;
	
	/**
	 * 累计取消订单次数
	 * @param  floorId(地板id) countOrderCancel(累计取消订单+1)
	 */
	void updateCountOrderCancel(int floorId,int countOrderCancel) throws EssException;
	
	/**
	 * 用于判断地板id是否已经存在
	 * @param  floorId(地板id) 
	 */
	StorageInfo findStorageInfoBy(int floorId) throws EssException;
}
