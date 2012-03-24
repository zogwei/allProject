package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Vein;
import com.jw.ess.util.ex.EssException;

/**
 * 纹理表数据库操作接口
 * 
 * @author chenxiangbin
 * 
 */
public interface IVeinDao
{

	/**
	 * 插入新的纹理
	 * 
	 * @param vein
	 * @throws EssException
	 */
	void insertVein(Vein vein) throws EssException;

	/**
	 * 查询所有纹理
	 * 
	 * @param tenantId
	 *            租户id
	 * @return 所有纹理
	 * @throws EssException
	 */
	List<Vein> findAllVeins(int tenantId) throws EssException;

	/**
	 * 查询纹理名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param veinName
	 *            纹理名称
	 * @return 纹理名称
	 * @throws EssException
	 */
	String findVeinName(int tenantId, String veinName) throws EssException;
}
