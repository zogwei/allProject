package com.jw.ess.controller.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.InStorage;
import com.jw.ess.service.IInStorageService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

@Controller()
public class InStorageController {

	private static final Log logger =
		LogFactory.getLog(InStorageController.class);

	@Resource(name = "inStorageService")
	private IInStorageService inStorageService;
	
	//添加入库信息
	@RequestMapping("/inStorage/add")
	public String addInStorage(InStorage inStorage,HttpSession session) {
		try {
			inStorage.setCreatedDate(DateUtil.currentTimeSecs());
			inStorage.setIsValid(1);
			inStorage.setTenantId(SessionManager.getTenantFrom(session).getId());
			inStorageService.addInStorage(inStorage);
		} catch (EssException e) {
			logger.error("failed to addInStorage", e);
		}
		return "redirect:/inStorage/list";
	}
	
	//跳到修改入库信息
	@RequestMapping("/inStorage/toModify")
	public String toModifyInStorage(int id, ModelMap map) {
		try {
			 map.addAttribute("inStorage",
					 inStorageService.getInStorage(id));
		} catch (EssException e) {
			logger.error("failed to toModifyInStorage", e);
		}
		return "inStorage/inStorageModify";
	}
	
	//修改入库信息成功
	@RequestMapping(value="/inStorage/modify",method=RequestMethod.POST)
	public String modifyInStorage(InStorage inStorage,ModelMap map,HttpSession session,Page page) {
		try {
			 inStorageService.modifyInStorage(inStorage);
		} catch (EssException e) {
			logger.error("failed to modifyInStorage", e);
		}
		return "redirect:/inStorage/list";
	}
	
	// 根据员工id查询入库详细信息
	@RequestMapping("/inStorage/one")
	public String getInStorage(int id, ModelMap map) {
		try {
			InStorage inStorage = new InStorage();
			inStorage = inStorageService.getInStorage(id);
			String time = DateUtil.transformString(
					inStorage.getCreatedDate(), DateUtil.INPUT_DATE_FORMAT);
			map.addAttribute("time",time);
			map.addAttribute("inStorage",inStorage);
		} catch (EssException e) {
			logger.error("failed to getInStorage", e);
		}
		return "inStorage/inStorageDetail";
	}
	
	//列出所有信息
	@RequestMapping("/inStorage/list")
	public String getInStorages(Page page,ModelMap map, HttpSession session) {
		try {
			 map.addAttribute("pageSupport",
					 (inStorageService.getInStorages
					 (SessionManager.getTenantFrom(session).getId(), page)));
		} catch (EssException e) {
			logger.error("failed to getInStorages", e);
		}
		return "inStorage/inStorageList";
	}
	
	//根据条件查询入库列表
	@RequestMapping("/inStorage/listSearch")
	public String getInStoragesBy(String firstDate,String lastDate,Floor floor,Page page,ModelMap map, HttpSession session) {
		try {
			int iFirstDate = 0;
			int iLastDate = 0;
			if(!StringUtils.isBlank(firstDate)){
				map.addAttribute("firstDate",firstDate);
				iFirstDate=DateUtil.transformTimeSecs(firstDate+" 00:00:00", DateUtil.DEFAULT_FORMAT);
			}
			if(!StringUtils.isBlank(lastDate)){
				map.addAttribute("lastDate",lastDate);
				iLastDate=DateUtil.transformTimeSecs(lastDate+" 23:59:59", DateUtil.DEFAULT_FORMAT);
			}
			floor.setTenant(SessionManager.getTenantFrom(session));
			map.addAttribute("pageSupport",
					inStorageService.getInStoragesBy(floor, iFirstDate, iLastDate, page));
		} catch (EssException e) {
			logger.error("failed to getInStoragesBy", e);
		}
		return "inStorage/inStorageList";
	}
}
