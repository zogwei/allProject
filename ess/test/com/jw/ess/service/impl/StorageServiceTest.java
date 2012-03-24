package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IOrderItemDao;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.InStorage;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IOrderService;
import com.jw.ess.service.IStorageService;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;


public class StorageServiceTest {
	
	private static final Log logger = LogFactory.getLog(StorageServiceTest.class);

    private IStorageService storageService;
    private IOrderService orderService;

    private IOrderItemDao orderItemDao; 
    @Before
    public void setUp() throws Exception{
    	storageService =  (IStorageService) SpringAssisant.getBean("storageService");
    	orderService=(IOrderService) SpringAssisant.getBean("orderService");
    	orderItemDao = (IOrderItemDao)SpringAssisant.getBean("orderItemDao");
    }
    
    @Test
    public void testAddStorage(){
    	
        try
        {
        	Storage storage=new Storage();
        	Floor floor = new Floor();
			floor.setId(1);
			storage.setFloor(floor);
			storage.setArea(1);
			storage.setCount(1);
			storage.setTenantId(1);
        	storageService.addStorage(storage);
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testAddStorage");
        }
    }
    
    @Test
    public void testModifyStorageByOrderItem(){
    	
        try
        {
        	Order order =orderService.getOrderById(1);
        	List<OrderItem> orderItems = order.getItems();
        	for(OrderItem orderItem:orderItems){
            	orderItem.setArea(1);
            	orderItem.setAmount(1);
            	storageService.modifyStorageByOrderAdd(order);
        	}
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testModifyStorageByOrderItem");
        }
    }
    
    @Test
    public void testModifyStorageByInStorage(){
    	
        try
        {
        	InStorage inStorage=new InStorage();
        	Floor floor = new Floor();
			floor.setId(1);
        	inStorage.setFloor(floor);
        	inStorage.setQuantity(1);
        	inStorage.setArea(1);
        	inStorage.setCount(1);
        	inStorage.setTenantId(1);
        	storageService.modifyStorageByInStorageAdd(inStorage);
        }
        catch (EssException e)
        {
            logger.error(e);
            fail("failed to testModifyStorageByInStorage");
        }
    }
    
    @Test
    public void testGetStorages()
    {
        try 
        {
            Page page = new Page();
            page.setCurrentPage(0);
            page.setPageSize(5);
            FloorCategory category = new FloorCategory();
            category.setId(1);
            ColorCode colorCode = new ColorCode();
            colorCode.setId(1);
            Vein vein = new Vein();
            vein.setId(1);
            Spec spec = new Spec();
            spec.setId(1);
            Tenant tenant = new Tenant();
            tenant.setId(1);
            Floor floor = new Floor();
            floor.setId(1);
            floor.setTenant(tenant);
            floor.setCategory(category);
            floor.setColorCode(colorCode);
            floor.setVein(vein);
            floor.setSpec(spec);
            PageSupport<StorageInfo> list = storageService.getStorages(floor,page);
            for(StorageInfo storageInfo : list.getResult()){
            	System.out.println(storageInfo.getCountInStorage() + "/" + storageInfo.getCountOrder() 
            			+ "/" +storageInfo.getStorage().getArea()+"/"+storageInfo.getStorage().getVein().getName()
            			+ "/" +storageInfo.getStorage().getFloor().getName()
            			+ "/" +storageInfo.getCountInStorage()+ "/" +storageInfo.getCountOrder()
            	        + "/" +storageInfo.getCountOrderCancel()+ "/" +storageInfo.getStorage().getFloor().getSellPrice());
            }
        } 
        catch (EssException e) 
        {
            logger.error(e);
            fail("failed to testGetStorages");
        }
    }
    
    @Test
    public void testGetStorageDetail()
    {
        try 
        {
            StorageInfo s = storageService.getStorageDetail(1,3);
            System.out.println(s.getCountInStorage()+"/"+s.getStorage().getArea()
            		+"/"+s.getCountOrder()+"/"+s.getCountOrderCancel()
            		+ "/" +s.getCountOrderCancel()
            		+ "/" +s.getStorage().getFloor().getSellPrice());
            
        } 
        catch (EssException e) 
        {
            logger.error(e);
            fail("failed to testGetStorages");
        }
    }
    @Test
    public void testModifyStorageByInStorageAdd()
    {
    	try {
			List<OrderItem> items = orderItemDao.findItemsByOrderId(1);
			Order order = new Order();
			order.setId(1);
			order.setItems(items);
			storageService.modifyStorageByOrderAdd(order);
		} catch (EssException e) {
			logger.error(e);
            fail("failed to testModifyStorageByInStorageAdd");
		}
    	
    }
    
    
}
