package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Spec;
import com.jw.ess.util.ex.EssException;

/**
 * 规格表数据库操作接口
 * 
 * @author chenxiangbin
 * 
 */
public interface ISpecDao
{

	/**
	 * 插入新的规格
	 * 
	 * @param spec
	 * @throws EssException
	 */
	void insertSpec(Spec spec) throws EssException;

	/**
	 * 根据租户id查询该租户的所有规格信息
	 * 
	 * @param tenantId
	 *            租户id
	 * @return 该租户的所有规格信息
	 * @throws EssException
	 */
	List<Spec> findAllSpecs(int tenantId) throws EssException;

	/**
	 * 查询规格名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param specName
	 *            规格名称
	 * @return 规格名称
	 * @throws EssException
	 */
	String findSpecName(int tenantId, String specName) throws EssException;

}
