package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.util.ex.EssException;

/**
 * 色号表数据库操作接口
 * 
 * @author j&w
 * 
 */
public interface IColorCodeDao
{
	/**
	 * 插入新的色号
	 * 
	 * @param colorCode
	 * @throws EssException
	 */
	void insertColorCode(ColorCode colorCode) throws EssException;

	/**
	 * 根据租户id查询该租户的所有色号信息
	 * 
	 * @param id
	 *            租户id
	 * @return
	 * @throws EssException
	 */
	List<ColorCode> findAllColorCodes(int tenantId) throws EssException;

	/**
	 * 查询色号名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param colorCodeName
	 *            色号名称
	 * @return 色号名称
	 * @throws EssException
	 */
	String findColorCodeName(int tenantId, String colorCodeName)
			throws EssException;

}
