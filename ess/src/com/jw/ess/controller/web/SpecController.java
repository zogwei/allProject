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

import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.ISpecService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;

@Controller
public class SpecController {
	
	private static final Log logger =
		LogFactory.getLog(SpecController.class);
	
	@Resource(name="specService")
	private ISpecService specService;
	
	@RequestMapping("/spec/add")
	public String addSpec(Spec spec, HttpSession session) {
		spec.setTenantId(SessionManager.getTenantFrom(session).getId());
		try {
			specService.addSpec(spec);
			session.setAttribute("operationInfo", "添加规格成功");
		} catch (EssException e) {
			logger.error("failed to addSpec", e);
			session.setAttribute("operationInfo", "添加规格失败，请稍后尝试");
		}
		return "redirect:/spec/list";
	}
	
	@RequestMapping("/spec/list")
	public String getAllSpecs(HttpSession session, ModelMap map) {
		try {
			map.put("specs", specService.getfindAllSpecs(SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getAllSpecs", e);
		}
		return "product/productGG";
	}
	
	
	//查询所有色号类型
	@ResponseBody
	@RequestMapping("/spec/json/list")
	public List<Spec> getSpecs(HttpSession session) {
		List<Spec> list = null;
		Tenant tenant = SessionManager.getTenantFrom(session);
		try {
			list = specService.getfindAllSpecs(tenant.getId());
		} catch (EssException e) {
			logger.error("failed to getspecs", e);
		}
		return list;
	}

}
