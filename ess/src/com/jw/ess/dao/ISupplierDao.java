package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Supplier;
import com.jw.ess.util.ex.EssException;

public interface ISupplierDao {
	
	/**
	 * 添加Supplier实例
	 * @param supplier Supplier实例
	 */
	void insertSupplier(Supplier supplier) throws EssException;
	
	/**
	 * 用于添加时判断供应商是否存在
	 * @param 根据tenantId和supplierName(name)查询 ,id
	 * @return 供应商信息
	 */
	Supplier findSupplier(Supplier supplier) throws EssException;
	
	/**
	 * 查看供应商信息
	 * @param id唯一标识
	 * @return 供应商信息
	 */
	Supplier findSupplierBy(int id) throws EssException;
	
	/**
	 * 修改供应商信息
	 * @param id唯一标识别
	 */
	void updateSupplier(Supplier supplier) throws EssException;
	
	/**
	 * 列出supplier所有信息
	 * 根据tenantId查找所有租户信息，根据tenantId和 name进行模糊查询
	 * @param supplier 通过Supplier实例传入tenantId与name进行模糊查询
	 * @return 返回匹配的数据,列表输出
	 */
	List<Supplier> findSupplierLikeBy(Supplier supplier) throws EssException;
	
}
