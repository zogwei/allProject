package com.jw.ess.controller.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Price;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IFloorService;
import com.jw.ess.service.IPriceService;
import com.jw.ess.service.ISpecService;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

@Controller
public class PriceController {
	
	private static final Log logger =
		LogFactory.getLog(PriceController.class);
	
	@Resource(name="priceService")
	private IPriceService priceService;
	
	@Resource(name = "tenantService")
	private ITenantService tenantService;
	
	@Resource(name = "floorService")
	private IFloorService floorService;
	
	@RequestMapping("/price/add")
	public String addPrice(Price price, HttpSession session) {
		try {
			
			priceService.addPrice(price);
			session.setAttribute("operationInfo", "添加价格成功");
		} catch (EssException e) {
			logger.error("failed to addSpec", e);
			session.setAttribute("operationInfo", "添加价格失败，请稍后尝试");
		}
		return "forward:/price/list";
	}
	
	@RequestMapping("/price/list")
	public String getpriceList(HttpSession session, Price price,ModelMap map) {
		try {
			//获得所有商户.
			map.addAttribute("tenants",tenantService.getAllTenants());
			//获得所有地板
			Floor floor = new Floor();
			Tenant tenant = new Tenant();
			tenant.setId(SessionManager.getTenantFrom(session).getId());
			floor.setTenant(tenant);
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(Integer.MAX_VALUE);
			map.put("floors", floorService.getFloorsBy(floor,page,SessionManager.getTenantFrom(session).getId()));
			
			map.put("prices",  priceService.findPrice(price));
			
		} catch (EssException e) {
			logger.error("failed to getAllSpecs", e);
		}
		return "price/priceMain";
	}
	
	@RequestMapping("/price/update")
	public String updatePrice(HttpSession session, Price price,ModelMap map) {
		try {
			//获得所有商户.
			map.addAttribute("tenants",tenantService.getAllTenants());
			//获得所有地板
			Floor floor = new Floor();
			Tenant tenant = new Tenant();
			tenant.setId(SessionManager.getTenantFrom(session).getId());
			floor.setTenant(tenant);
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(Integer.MAX_VALUE);
			map.put("floors", floorService.getFloorsBy(floor,page,SessionManager.getTenantFrom(session).getId()));
			
			priceService.updatePrice(price);
		} catch (EssException e) {
			logger.error("failed to getAllSpecs", e);
		}
		return "forward:/price/list";
	}
	
	@RequestMapping("/price/toadd")
	public String toAddPrice(HttpSession session, Price price,ModelMap map) {
		try {
			//获得所有商户.
			map.addAttribute("tenants",tenantService.getAllTenants());
			//获得所有地板
			Floor floor = new Floor();
			Tenant tenant = new Tenant();
			tenant.setId(SessionManager.getTenantFrom(session).getId());
			floor.setTenant(tenant);
			Page page = new Page();
			page.setCurrentPage(0);
			page.setPageSize(Integer.MAX_VALUE);
			map.put("floors", floorService.getFloorsBy(floor,page,SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getAllSpecs", e);
		}
		return "price/priceAdd";
	}
	
	@RequestMapping("/price/delete")
	public String deletePrice(HttpSession session, Price price,ModelMap map) {
		try {
			priceService.deletePrice(price);
		} catch (EssException e) {
			logger.error("failed to getAllSpecs", e);
		}
		return "forward:/price/list";
	}
	
	

}
