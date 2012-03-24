package com.jw.ess.controller.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.entity.Supplier;
import com.jw.ess.service.ISupplierService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;

@Controller
public class SupplierController {
	
	private static final Log logger =
		LogFactory.getLog(SupplierController.class);
	
	@Resource(name="supplierService")
	private ISupplierService supplierService;
	
	//添加供应商
	@RequestMapping("/supplier/add")
	public String addSupplier(Supplier supplier,ModelMap map,HttpSession session) {
		try {
			supplier.setTenantId(SessionManager.getTenantFrom(session).getId());
			supplierService.addSupplier(supplier);
		} catch (EssException e) {
			logger.error("failed to addSupplier", e);
		}
		return "redirect:/supplier/list";
	}
	
	//查询供应商（按供应商名查询）
	@RequestMapping(value="/supplier/likeList", method=RequestMethod.POST)
	public String getSuppliersLike(Supplier supplier, ModelMap map,HttpSession session){
		try {
			supplier.setTenantId(SessionManager.getTenantFrom(session).getId());
			map.addAttribute("suppliers",
					supplierService.getAllSupplierLike(supplier) );
			 
		} catch (EssException e) {
			logger.error("failed to getSuppliersLike", e);
		}
		return "supplier/supplierList";
	}
	
	//查出所有列表
	@RequestMapping("/supplier/list")
	public String getAllSupplier(HttpSession session, ModelMap map){
		try {
			int tenantId = SessionManager.getTenantFrom(session).getId();
			Supplier supplier = new Supplier();
			supplier.setTenantId(tenantId);
			map.addAttribute("suppliers",
					supplierService.getAllSupplierLike(supplier) );
			 
		} catch (EssException e) {
			logger.error("failed to getAllSupplier", e);
		}
		return "supplier/supplierList";
	}
	
	//查询详细信息
	@RequestMapping("/supplier/one")
	public String getSupplierById(int id, ModelMap map){
		try {
			Supplier supplier = new Supplier();
			supplier = supplierService.getSupplier(id);
			String time = DateUtil.transformString(
					supplier.getCreatedDate(), DateUtil.INPUT_DATE_FORMAT);
			map.addAttribute("time",time);
			map.addAttribute("supplier",supplier);
		} catch (EssException e) {
			logger.error("failed to getSupplierById", e);
		}
		return "supplier/supplierDetail";
	}
	
	//跳到修改页面  传id
	@RequestMapping("/supplier/toModify")
	public String toModifySupplier(int id, ModelMap map){
		try {
			map.addAttribute("supplier",
					supplierService.getSupplier(id) );
		} catch (EssException e) {
			logger.error("failed to getSupplierById", e);
		}
		return "supplier/supplierModify";
	}
	
	//修改信息
	@RequestMapping("/supplier/modify")
	public String modifySupplier(Supplier supplier, ModelMap map){
		try {
			supplierService.modifySupplier(supplier);
		} catch (EssException e) {
			logger.error("failed to modifySupplier", e);
		}
		return "redirect:/supplier/list";
	}
	
	//查询供应商（按供应商名查询）
	@ResponseBody
	@RequestMapping("/supplier/json/list")
	public List<Supplier> getSuppliers(HttpSession session){
		List<Supplier> list = null;
		int tenantId = SessionManager.getTenantFrom(session).getId();
		try {
			Supplier supplier = new Supplier();
			supplier.setTenantId(tenantId);
			list = supplierService.getAllSupplierLike(supplier);
		} catch (EssException e) {
			logger.error("failed to getSuppliers", e);
		}
		return list;
	}
	
	//根据地板名和租户id查找地板,
	//用于在添加时判断供应商是否存在
	@ResponseBody
	@RequestMapping("/supplier/json/one")
	public boolean getSupplierByName(String name,int id,HttpSession session) {
		boolean flag = true;
		try {
			Supplier supplier = new Supplier();
			supplier.setTenantId(SessionManager.getTenantFrom(session).getId());
			supplier.setName(name);
			supplier.setId(id);
			if(supplierService.getSupplier(supplier)== null) {
				flag = false;
			}
		} catch (EssException e) {
			logger.error("failed to getSupplierById", e);
		}
		return flag;
	}
	
}
