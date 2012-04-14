package com.jw.ess.controller.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.service.IFloorService;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

@Controller
public class FloorController {

	private static final Log logger =
		LogFactory.getLog(FloorController.class);
	
	
	@Resource(name = "floorService")
	private IFloorService floorService;
	
	//插入地板信息(可能要上传图片)
	@RequestMapping("/floor/add")
	public String addFloor(@RequestParam("image") MultipartFile image, Floor floor, HttpSession session) {
		floor.setTenant(SessionManager.getTenantFrom(session));
		try {
			floorService.addFloor(floor, image);
			session.setAttribute("operationInfo", "添加地板信息成功");
		} catch (EssException e) {
			logger.error("failed to addFloor", e);
			session.setAttribute("operationInfo", "添加地板信息失败，请稍后尝试");
		}
		return "redirect:/floor/list";
	}
	
	//从excel批量插入地板信息
	@RequestMapping("/floor/adds")
	public String addFloors(@RequestParam("excel") MultipartFile excel, HttpSession session, ModelMap map) {
		try {
			floorService.addFloors(SessionManager.getTenantFrom(session), excel);
		} catch (EssException e) {
			logger.error("failed to addFloor", e);
			session.setAttribute("operationInfo", "批量插入地板信息失败，文件格式不正确或系统中已有该地板的信息");
		}
		return "redirect:/floor/list";
	}
	
	//修改地板信息(可能要上传新的图片)
	@RequestMapping("/floor/modify")
	public String modifyFloor(@RequestParam("image") MultipartFile image, Floor floor, HttpSession session) {
		floor.setTenant(SessionManager.getTenantFrom(session));
		try {
			 floorService.modifyFloor(floor, image);
			 session.setAttribute("operationInfo", "修改地板信息成功");
		} catch (EssException e) {
			logger.error("failed to modifyFloor", e);
			session.setAttribute("operationInfo", "修改地板信息失败，请稍后尝试");
		}
		return "redirect:/floor/list";
	}
	
	// 根据地板id查询地板详细信息
	@RequestMapping("/floor/one")
	public String getFloorForDetail(int id,ModelMap map, HttpSession session) {
		try {
			 map.addAttribute("floor", floorService.getFloorById(id,SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getFloorForDetail", e);
		}
		return "product/productDetail";
	}
	
	// 根据地板id查询地板修改信息
	@RequestMapping("/floor/edit")
	public String getFloorForEdit(int id,ModelMap map, HttpSession session) {
		try {
			 map.addAttribute("floor", floorService.getFloorById(id,SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getFloorForEdit", e);
		}
		return "product/productEdit";
	}
	
	//根据条件查询地板列表
	@RequestMapping("/floor/list")
	public String getFloorBy(Floor floor,Page page,ModelMap map,HttpSession session) {
		floor.setTenant(SessionManager.getTenantFrom(session));
		try {
			 //返回查询条件用于页面显示
			 map.addAttribute("floor", floor);
			 map.addAttribute("pageSupport",floorService.getFloorsBy(floor, page,SessionManager.getTenantFrom(session).getId()));
		} catch (EssException e) {
			logger.error("failed to getFloorBy", e);
		}
		return "product/productMain";
	}
	
	//根据地板名和租户id查找地板
	@ResponseBody
	@RequestMapping("/floor/json/one")
	public boolean getFloorByName(Floor floor, HttpSession session) {
		boolean flag = false;
		floor.setTenant(SessionManager.getTenantFrom(session));
		try {
			if(floorService.getFloorByName(floor.getTenant().getId(), floor.getName()) != null) {
				flag = true;
			}
		} catch (EssException e) {
			logger.error("failed to getFloorByName", e);
		}
		return flag;
	}
	
	//根据条件查询地板列表
	@ResponseBody
	@RequestMapping("/floor/json/list")
	public List<Floor> getFloors(Floor floor,Page page,HttpSession session) {
		List<Floor> list = null;
		floor.setTenant(SessionManager.getTenantFrom(session));
		try {
			 //返回查询条件用于输入提示显示
			list = floorService.getFloorsBy(floor, page,SessionManager.getTenantFrom(session).getId()).getResult();
		} catch (EssException e) {
			logger.error("failed to getFloors", e);
		}
		return list;
	}
	
	//根据地板id查询该地板的所有图片
	@ResponseBody
	@RequestMapping("/floor/json/images")
	public List<PicPath> getFloorPictures(int floorId, HttpSession session) {
		List<PicPath> list = null;
		try {
			list = floorService.getFloorImages(floorId);
		} catch (EssException e) {
			logger.error("failed to getFloorPictures", e);
		}
		return list;
	}
	
	//插入地板图片
	@RequestMapping("/floor/upload")
	public String addFloorPictures(@RequestParam("image") MultipartFile image,int id, HttpSession session) {
		try {
			String tenantName = SessionManager.getTenantFrom(session).getName();
			floorService.uploadFloorImages(tenantName, id, image);
			session.setAttribute("operationInfo", "添加图片成功");
		} catch (EssException e) {
			logger.error("failed to addFloorPictures", e);
			session.setAttribute("operationInfo", "添加图片失败，请稍后尝试");
		}
		return "redirect:/floor/list";
	}
	
}
