package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.FloorCategory;
import com.jw.ess.util.ex.EssException;

/**
 * 地板类型表数据库操作接口
 * @author chenxiangbin
 *
 */
public interface IFloorCategoryDao
{
    
    /**
     * 插入新的地板类型
     * @param floorCategory
     * @throws EssException
     */
    void insertFloorCategory(FloorCategory floorCategory) throws EssException;
    
    /**
     * 根据地板类型的id查询地板类型详细信息
     * @param id
     * @return
     * @throws EssException
     */
    FloorCategory findFloorCategoryById(int id) throws EssException;
    
    /**
     * 根据租户id查询该租户的所有地板类型信息
     * @param id 租户id
     * @return
     * @throws EssException
     */
    List<FloorCategory> findAllFloorCategorysByTenantId(int id) throws EssException;
    
    
	/**
	 *根据租户id查询地板类型名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param categoryName
	 *            类型名称
	 * @return 地板类型名称名称
	 * @throws EssException
	 */
	String findCategoryNameBy(int tenantId, String categoryName)
			throws EssException;
    
}
