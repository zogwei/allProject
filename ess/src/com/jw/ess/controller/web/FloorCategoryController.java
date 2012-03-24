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

import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;

@Controller
public class FloorCategoryController {
	
	private static final Log logger =
		LogFactory.getLog(FloorCategoryController.class);
	
	@Resource(name="floorCategoryService")
	private IFloorCategoryService floorCategoryService;
	
	@RequestMapping("/floorCategory/add")
	public String addFloorCategory(FloorCategory floorCategory, HttpSession session) {
		floorCategory.setTenantId(SessionManager.getTenantFrom(session).getId());
		try {
			floorCategoryService.addFloorCategory(floorCategory);
			session.setAttribute("operationInfo", "添加地板类别成功");
		} catch (EssException e) {
			logger.error("failed to addFloorCategory", e);
			session.setAttribute("operationInfo", "添加地板类别失败，请稍后尝试");
		}
		return "redirect:/floorCategory/list";
	}
	
	@RequestMapping("/floorCategory/list")
	public String getAllFloorCategorys(HttpSession session, ModelMap map) {
		try {
			map.put("floorCategorys", floorCategoryService.getAllFloorCategorysByTenantId(SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getAllFloorCategorys", e);
		}
		return "product/productLB";
	}
	
	//查询所有地板类型
	@ResponseBody
	@RequestMapping("/floorCategory/json/list")
	public List<FloorCategory> getFloorCategorys(HttpSession session) {
		List<FloorCategory> list = null;
		Tenant tenant = SessionManager.getTenantFrom(session);
		try {
			list = floorCategoryService.getAllFloorCategorysByTenantId(tenant.getId());
		} catch (EssException e) {
			logger.error("failed to getFloorCategorys", e);
		}
		return list;
	}

}
