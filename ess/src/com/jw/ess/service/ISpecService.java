package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Spec;
import com.jw.ess.util.ex.EssException;

/**
 * 规格表服务操作接口
 * 
 * @author j&w
 * 
 */
public interface ISpecService
{

	/**
	 * 插入新的规格
	 * 
	 * @param spec
	 * @throws EssException
	 */
	void addSpec(Spec spec) throws EssException;

	/**
	 * 根据租户id查询该租户的所有规格信息
	 * 
	 * @param tenantId
	 *            租户id
	 * @return 该租户的所有规格信息
	 * @throws EssException
	 */
	List<Spec> getfindAllSpecs(int tenantId) throws EssException;

}
