package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Supplier;
import com.jw.ess.util.ex.EssException;

public interface ISupplierService {
	
	/**
	 * 创建供应商信息
	 * @param 
	 * @return
	 */
	void addSupplier(Supplier supplier) throws EssException;
	
	/**
	 * 查询供应商(按名称查询)
	 * @param 租户标识
	 * @return 查询到的结果列出
	 */
	List<Supplier> getAllSupplierLike(Supplier supplier) throws EssException;
	
	/**
	 * 修改供应商信息
	 * @param supplier Supplier实例,根据id修改
	 * @return
	 */
	void modifySupplier(Supplier supplier) throws EssException;
	
	/**
	 * 查看供应商信息
	 * @param id唯一标识
	 * @return
	 */
	Supplier getSupplier(int id) throws EssException;
	
	/**
	 * 查看供应商信息
	 * @param 传入tenantId(租户id)和name(供应商名字)
	 * @return
	 */
	Supplier getSupplier(Supplier supplier) throws EssException;
	
}
