package com.jw.ess.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IStorageInfoDao;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.service.IStorageInfoService;
import com.jw.ess.util.ex.EssException;

@Service("storageInfoService")
public class StorageInfoService implements IStorageInfoService {

	@Resource(name = "storageInfoDao")
	private IStorageInfoDao storageInfoDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addStorageInfo(int floorId, int countInStorage, int countOrder,
			int countOrderCancel) throws EssException {
		storageInfoDao.insertStorageInfo(floorId, countInStorage, countOrder,
				countOrderCancel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public StorageInfo getStorageInfoBy(int floorId) throws EssException {
		return storageInfoDao.findStorageInfoBy(floorId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyCountInStorage(int floorId, int countInStorage)
			throws EssException {
		storageInfoDao.updateCountInStorage(floorId, countInStorage);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyCountOrder(int floorId, int countOrder)
			throws EssException {
		storageInfoDao.updateCountOrder(floorId, countOrder);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyCountOrderCancel(int floorId, int countOrderCancel)
			throws EssException {
		storageInfoDao.updateCountOrderCancel(floorId, countOrderCancel);
	}

}
