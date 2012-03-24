package com.jw.ess.service.impl;

import static org.junit.Assert.fail;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class FloorCategorySerivceTest
{
    private static final Log logger = LogFactory.getLog(FloorCategorySerivceTest.class);

    private IFloorCategoryService floorCategoryService;

    @Before
    public void setUp() throws Exception
    {
        floorCategoryService = SpringAssisant.getBean(FloorCategoryService.class);
    }

    @Test
    public void testInsertFloorCategory()
    {
        FloorCategory floorCategory = new FloorCategory();
        floorCategory.setName("FloorCategory1");
        floorCategory.setDesc("");
        floorCategory.setIsValid(1);
        floorCategory.setTenantId(1);
        
        FloorCategory floorCategory2 = new FloorCategory();
        floorCategory2.setName("FloorCategory2");
        floorCategory2.setDesc("");
        floorCategory2.setIsValid(1);
        floorCategory2.setTenantId(1);
        
        try
        {
            floorCategoryService.addFloorCategory(floorCategory);
            floorCategoryService.addFloorCategory(floorCategory2);
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testInsertFloorCategory");
        }
    }
    
    @Test
    public void testFindFloorCategoryById()
    {
        int id = 1;
        try
        {
            FloorCategory c = floorCategoryService.getFloorCategoryById(id);
            if(c != null)
                System.out.println(c.getId() + "/" + c.getName() + "/" + c.getDesc() + "/" + c.getIsValid() + "/" + c.getTenantId());
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testFindFloorCategoryById");
        }
    }
    
    @Test
    public void testFindAllFloorCategorysByTenantId()
    {
        int tenantId = 1;
        try
        {
            List<FloorCategory> list = floorCategoryService.getAllFloorCategorysByTenantId(tenantId);
            if(list != null)
                for(FloorCategory c : list)
                    System.out.println(c.getId() + "/" + c.getName() + "/" + c.getDesc() + "/" + c.getIsValid() + "/" + c.getTenantId());
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testFindAllFloorCategorysByTenantId");
        }
    }
    
}
