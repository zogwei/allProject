package com.jw.ess.service;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public interface IInStorageService {
	
	/**
	 * 产品入库
	 * @param inStorage
	 */
    void addInStorage(InStorage inStorage) throws EssException;
    
    /**
	 * 查看入库信息
	 * @param id主键
	 */
    InStorage getInStorage(int id) throws EssException;
    
	/**
	 * 修改入库信息
	 * @param inStorage
	 */
	void modifyInStorage(InStorage inStorage) throws EssException;
	
	/**
	 * 查询入库信息
	 * @param inStorage 因为有多种查询，会根据对象传进来的参数进行模糊查询
	 * @return 查出的信息
	 */
	PageSupport<InStorage> getInStoragesBy(Floor floor,int firstDate,int lastDate,Page page) 
	     throws EssException;
	
	/**
	 * 所有入库信息
	 * @param tenantId一个租户的所有信息
	 * @return 
	 */
	PageSupport<InStorage> getInStorages(int tenantId,Page page) throws EssException;
}
