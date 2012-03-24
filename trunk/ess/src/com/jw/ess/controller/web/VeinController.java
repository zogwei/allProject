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

import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;

@Controller
public class VeinController {
	
	private static final Log logger =
		LogFactory.getLog(VeinController.class);
	
	@Resource(name="veinService")
	private IVeinService veinService;
	
	@RequestMapping("/vein/add")
	public String addVein(Vein vein, HttpSession session) {
		vein.setTenantId(SessionManager.getTenantFrom(session).getId());
		try {
			veinService.addVein(vein);
			session.setAttribute("operationInfo", "添加纹理成功");
		} catch (EssException e) {
			logger.error("failed to addVein", e);
			session.setAttribute("operationInfo", "添加纹理失败，请稍后尝试");
		}
		return "redirect:/vein/list";
	}
	
	@RequestMapping("/vein/list")
	public String getAllVeins(HttpSession session, ModelMap map) {
		try {
			map.put("veins", veinService.getAllVeins(SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getAllVeins", e);
		}
		return "product/productWL";
	}
	
	
	//查询所有色号类型
	@ResponseBody
	@RequestMapping("/vein/json/list")
	public List<Vein> getVeins(HttpSession session) {
		List<Vein> list = null;
		Tenant tenant = SessionManager.getTenantFrom(session);
		try {
			list = veinService.getAllVeins(tenant.getId());
		} catch (EssException e) {
			logger.error("failed to getveins", e);
		}
		return list;
	}

}
