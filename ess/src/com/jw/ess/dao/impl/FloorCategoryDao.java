package com.jw.ess.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IFloorCategoryDao;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 地板类型表数据库操作接口实现类
 * @author chenxiangbin
 *
 */
@Repository("floorCategoryDao")
public class FloorCategoryDao implements IFloorCategoryDao
{
    
    private static final Log logger = LogFactory.getLog(FloorCategoryDao.class);

    private SqlSessionTemplate sqlSessionTemplate;
    
    private static final String INSERT_FlOOR_CATEGORY = MapperConstant.MAPPER_NAMESPACE_FlOOR_CATEGORY
            + ".insertFloorCategory";
    
    private static final String FIND_FlOOR_CATEGORY_BY_ID = MapperConstant.MAPPER_NAMESPACE_FlOOR_CATEGORY
            + ".findFloorCategoryById";
    
    private static final String FIND_ALL_FlOOR_CATEGORY_BY_TENANT_ID = MapperConstant.MAPPER_NAMESPACE_FlOOR_CATEGORY
            + ".findAllFloorCategorysByTenantId";


	private static final String FINE_CATEGORY_NAME = MapperConstant.MAPPER_NAMESPACE_FlOOR_CATEGORY
            + ".findCategoryName";
    
    
    @Resource(name = "sqlSessionTemplate")
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
    {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public FloorCategory findFloorCategoryById(int id) throws EssException
    {
        try
        {
            return (FloorCategory) this.sqlSessionTemplate.selectOne(FIND_FlOOR_CATEGORY_BY_ID, id);
        }
        catch (PersistenceException e)
        {
            logger.error("failed to findFloorCategory", e);
            throw new EssException(e, MessageCode.DATABASE_ERROR);
        }
    }


    @Override
    public void insertFloorCategory(FloorCategory floorCategory)
            throws EssException
    {
        try
        {
            this.sqlSessionTemplate.insert(INSERT_FlOOR_CATEGORY, floorCategory);
        }
        catch (PersistenceException e)
        {
            logger.error("failed to insertFloorCategory", e);
            throw new EssException(e, MessageCode.DATABASE_ERROR);
        }
    }


    @SuppressWarnings("unchecked")
	@Override
    public List<FloorCategory> findAllFloorCategorysByTenantId(int id) throws EssException
    {
        try
        {
            return (List<FloorCategory>) this.sqlSessionTemplate.selectList(FIND_ALL_FlOOR_CATEGORY_BY_TENANT_ID, id);
        }
        catch (PersistenceException e)
        {
            logger.error("failed to findAllFloorCategorysByTenantId", e);
            throw new EssException(e, MessageCode.DATABASE_ERROR);
        }
    }

	@Override
	public String findCategoryNameBy(int tenantId, String categoryName)
			throws EssException {
			FloorCategory floorCategory=new FloorCategory();
			floorCategory.setTenantId(tenantId);
			floorCategory.setName(categoryName);
        try
        {
            return (String) this.sqlSessionTemplate.selectOne(
            		FINE_CATEGORY_NAME, floorCategory);
        }
        catch (PersistenceException e)
        {
            logger.error("failed to findCategoryName", e);
            throw new EssException(e, MessageCode.DATABASE_ERROR);
        }
	}


}
