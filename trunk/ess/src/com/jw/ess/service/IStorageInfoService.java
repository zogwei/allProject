package com.jw.ess.service;

import com.jw.ess.entity.StorageInfo;
import com.jw.ess.util.ex.EssException;

public interface IStorageInfoService {
	
	void addStorageInfo(int floorId,int countInStorage,
			int countOrder,int countOrderCancel) throws EssException;
	
	void modifyCountInStorage(int floorId,int countInStorage) throws EssException;
	
	void modifyCountOrder(int floorId,int countOrder) throws EssException;
	
	void modifyCountOrderCancel(int floorId,int countOrderCancel) throws EssException;
	
	StorageInfo getStorageInfoBy(int floorId) throws EssException;
}
