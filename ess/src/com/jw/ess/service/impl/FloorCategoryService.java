package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.jw.ess.dao.IFloorCategoryDao;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 地板类型表服务操作接口实现类
 * @author chenxiangbin
 *
 */
@Service("floorCategoryService")
public class FloorCategoryService implements IFloorCategoryService
{
	private static final Log logger = LogFactory.getLog(IFloorCategoryService.class);
    @Resource
    private IFloorCategoryDao floorCategoryDao;

    @Override
    public FloorCategory getFloorCategoryById(int id) throws EssException
    {
        return floorCategoryDao.findFloorCategoryById(id);
    }

    @Override
    public void addFloorCategory(FloorCategory floorCategory)
            throws EssException
    {
    	floorCategory.setIsValid(1);
		String matchedName = floorCategoryDao.findCategoryNameBy(floorCategory.getTenantId(),
				floorCategory.getName());
				if (matchedName != null)
				{
					logger.error("floorCategory name " + floorCategory.getName()
							+ " is already exists");
					throw new EssException("floorCategory name " + floorCategory.getName()
							+ " is already exists",
							MessageCode.FLOOR_CATEGORY_NAME_ALREADY_EXISTS);
				}
         floorCategoryDao.insertFloorCategory(floorCategory);
    }
    
    @Override
    public List<FloorCategory> getAllFloorCategorysByTenantId(int id)
            throws EssException
    {
        return floorCategoryDao.findAllFloorCategorysByTenantId(id);
    }


}
