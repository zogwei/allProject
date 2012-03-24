package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.util.ex.EssException;

/**
 * 色号表服务操作接口
 * 
 * @author j&w
 * 
 */
public interface IColorCodeService
{
	/**
	 * 插入新的色号
	 * 
	 * @param colorCode
	 * @throws EssException
	 */
	void addColorCode(ColorCode colorCode) throws EssException;

	/**
	 * 根据租户id查询该租户的所有色号信息
	 * 
	 * @param id
	 *            租户id
	 * @return
	 * @throws EssException
	 */
	List<ColorCode> getAllColorCodes(int tenantId) throws EssException;

}
