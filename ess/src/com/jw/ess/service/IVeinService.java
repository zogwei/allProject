package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Vein;
import com.jw.ess.util.ex.EssException;

/**
 * 纹理表服务操作接口
 * 
 * @author j&w
 * 
 */
public interface IVeinService
{

	/**
	 * 插入新的纹理
	 * 
	 * @param vein
	 * @throws EssException
	 */
	void addVein(Vein vein) throws EssException;

	/**
	 * 查询所有纹理
	 * 
	 * @param tenantId
	 *            租户id
	 * @return 所有纹理
	 * @throws EssException
	 */
	List<Vein> getAllVeins(int tenantId) throws EssException;

}
