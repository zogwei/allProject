package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.FloorCategory;
import com.jw.ess.util.ex.EssException;

/**
 * 地板类型表服务操作接口
 * @author chenxiangbin
 *
 */
public interface IFloorCategoryService
{
    
    /**
     * 插入新的地板类型
     * @param floorCategory
     * @throws EssException
     */
    void addFloorCategory(FloorCategory floorCategory) throws EssException;
    
    /**
     * 根据地板类型的id查询地板类型详细信息
     * @param id
     * @return
     * @throws EssException
     */
    FloorCategory getFloorCategoryById(int id) throws EssException;
    
    /**
     * 根据租户id查询该租户的所有地板类型信息
     * @param id 租户id
     * @return
     * @throws EssException
     */
    List<FloorCategory> getAllFloorCategorysByTenantId(int id) throws EssException;
    
}
