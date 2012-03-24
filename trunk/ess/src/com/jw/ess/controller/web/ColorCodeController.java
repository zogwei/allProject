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

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;

@Controller
public class ColorCodeController {
	
	private static final Log logger =
		LogFactory.getLog(ColorCodeController.class);
	
	@Resource(name="colorCodeService")
	private IColorCodeService colorCodeService;
	
	@RequestMapping("/colorCode/add")
	public String addColorCode(ColorCode colorCode, HttpSession session) {
		colorCode.setTenantId(SessionManager.getTenantFrom(session).getId());
		try {
			colorCodeService.addColorCode(colorCode);
			session.setAttribute("operationInfo", "添加色号成功");
		} catch (EssException e) {
			logger.error("failed to addColorCode", e);
			session.setAttribute("operationInfo", "添加色号失败，请稍后尝试");
		}
		return "redirect:/colorCode/list";
	}
	
	@RequestMapping("/colorCode/list")
	public String getAllColorCodes(HttpSession session, ModelMap map) {
		try {
			map.put("colorCodes", colorCodeService.getAllColorCodes(SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getAllColorCodes", e);
		}
		return "product/productSH";
	}
	
	//查询所有色号类型
	@ResponseBody
	@RequestMapping("/colorCode/json/list")
	public List<ColorCode> getColorCodes(HttpSession session) {
		List<ColorCode> list = null;
		Tenant tenant = SessionManager.getTenantFrom(session);
		try {
			list = colorCodeService.getAllColorCodes(tenant.getId());
		} catch (EssException e) {
			logger.error("failed to getcolorCodes", e);
		}
		return list;
	}

}
