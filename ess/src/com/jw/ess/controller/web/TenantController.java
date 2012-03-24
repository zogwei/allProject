package com.jw.ess.controller.web;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.entity.Tenant;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ex.EssException;
@Controller
public class TenantController {
	private static final Log logger =
		LogFactory.getLog(TenantController.class);
	@Resource(name = "tenantService")
	private ITenantService tenantService;
	
	@RequestMapping("/tenant/add")
	public String addTenant(Tenant tenant,ModelMap map) {
		try {
			tenant.setCreatedDate(DateUtil.currentTimeSecs());
			tenant.setIsDefault(2);
			tenant.setIsValid(1);
			tenantService.addTenant(tenant);
			map.addAttribute("tenants",tenantService.getAllTenants());
		} catch (EssException e) {
			logger.error("failed to addTenant", e);
		}
		return "tenant/tenantMain";
	}
	
	@RequestMapping("/tenant/list")
	public String getAllTenants(ModelMap map) {
		try {
			map.addAttribute("tenants",tenantService.getAllTenants());
		} catch (EssException e) {
			logger.error("failed to getAllTenant", e);
		}
		return "tenant/tenantMain";
	}
	
	@RequestMapping("/tenant/getTenantByName")
	public String getTenantByName(String tenantName,ModelMap map) {
		try {
			if(tenantName.trim().equals("")){
				map.addAttribute("tenants",tenantService.getAllTenants());
			}else{
				map.addAttribute("tenants", tenantService.getTenantsByName(tenantName));
			}
		} catch (EssException e) {
			logger.error("failed to getTenantByName", e);
		}
		return "tenant/tenantMain";
	}
	
	@RequestMapping("/tenant/one")
	public String getTenantsById(int id,ModelMap map) {
		try {
			map.addAttribute("tenant", tenantService.getTenantsById(id));
		} catch (EssException e) {
			logger.error("failed to getTenantsByid", e);
		}
		return "tenant/tenantEdit";
	}
		
	@RequestMapping("/tenant/edit")
	public String modifyTenant(Tenant tenant,ModelMap map) {
		try {
			tenantService.modifyTenant(tenant);
			map.addAttribute("tenants",tenantService.getAllTenants());
		} catch (EssException e) {
			logger.error("failed to modifyTenants", e);
		}
		return "tenant/tenantMain";
	}
	
	@ResponseBody
	@RequestMapping("/tenant/json/checkName")
	public boolean checkName(String tenantName) {
		boolean flag = true;
		Tenant tenant = new Tenant();
		tenant.setName(tenantName);
		try {
			if(tenantService.getTenantName(tenant)!=null){
				flag = false;
			}	
		} catch (EssException e) {
			logger.error("failed to checkName", e);
		}
		return flag;
	}
	
	@ResponseBody
	@RequestMapping("/tenant/json/check")
	public boolean check(String tenantName,int id) {
		boolean flag = true;
		Tenant tenant = new Tenant();
		tenant.setId(id);
		tenant.setName(tenantName);
		try {
			if(tenantService.checkTenant(tenant)==false){
				flag = false;
			}	
		} catch (EssException e) {
			logger.error("failed to checkName", e);
		}
		return flag;
	}
}
