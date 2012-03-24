package com.jw.ess.controller.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jw.ess.entity.Employee;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

@Controller
public class SystemController {
	private static final Log logger =
		LogFactory.getLog(SystemController.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	
	@Resource(name="tenantService")
	private ITenantService tenantService;
	
	@RequestMapping("/system/add")
	public String addSystem(Employee employee,ModelMap map) {
		try {
			employee.setCreatedDate(DateUtil.currentTimeSecs());
			employee.setCategory(CommonConstant.EMPLOYEE_ROLE_ADMIN);
			employee.setState(CommonConstant.EMPLOYEE_MISSION);
			employee.setIsValid(CommonConstant.EMPLOYEE_MISSION);
			employeeService.addEmployee(employee);
			 map.addAttribute("tenantId", employee.getTenantId());
		} catch (EssException e) {
			logger.error("failed to addEmployee", e);
			map.addAttribute("errorMessage","&nbsp;&nbsp;&nbsp;添加管理员出错");	
		}
		return "redirect:/system/list";
	}
	
	@RequestMapping("/system/modify")
	public String modifySystem(Employee employee,ModelMap map) {
		try {
			 employeeService.modifyEmployee(employee);
		} catch (EssException e) {
			logger.error("failed to modifyEmployee", e);
			map.addAttribute("errorMessage","&nbsp;&nbsp;&nbsp;管理员信息修改出错");	
		}
		return "redirect:/system/list";
	}
	@RequestMapping("/system/editDetail")
	public String editDetail(int id,ModelMap map) {
		try {
			Employee employee = employeeService.getEmployeeById(id);
			 map.addAttribute("employee", employee );
			 map.addAttribute("createDate", DateUtil.transformString(employee.getCreatedDate(), DateUtil.INPUT_DATE_FORMAT));
		} catch (EssException e) {
			map.addAttribute("errorMessage", "获取信息出错");
			logger.error("failed to editDetail", e);
		}
		return "system/systemDetail";
	}
	
	// 根据员工id查询员工信息
	@RequestMapping("/system/one")
	public String getEmployeeById(int id,ModelMap map) {
		try {
			 map.addAttribute("employee",
					 employeeService.getEmployeeById(id));
			 map.addAttribute("tenants",tenantService.getAllTenants());
		} catch (EssException e) {
			logger.error("failed to getEmployeeById", e);
			map.addAttribute("errorMessage","&nbsp;&nbsp;&nbsp;获取管理员出错");	
		}
		return "system/systemEdit";
	}
	
	//根据条件查询员工列表
	@RequestMapping("/system/list")
	public String getEmployeesBy(Employee employee,Page page,ModelMap map,HttpSession session) {
		try {
			employee.setTenantId(-1);
			employee.setCategory(CommonConstant.EMPLOYEE_ROLE_ADMIN);
			employee.setState(CommonConstant.EMPLOYEE_MISSION);
			employee.setIsValid(CommonConstant.EMPLOYEE_MISSION);
			 map.addAttribute("pageSupport",
					 employeeService.getEmployeesBy(employee,page));
			 map.addAttribute("tenants",tenantService.getAllTenants());
			 map.addAttribute("name", employee.getName());
			 map.addAttribute("tenantId", employee.getTenantId());
			 map.addAttribute("query",employee);
		} catch (EssException e) {
			map.addAttribute("error","&nbsp;&nbsp;&nbsp;获取管理员列表出错");	
			logger.error("failed to getEmployeesBy", e);
		}
		return "system/systemMain";
	}
	
	@RequestMapping("/system/tenantList1")
	public String getTenantList1(Employee employee,Page page,ModelMap map,HttpSession session){
		try {
			employee.setTenantId(1);
			map.addAttribute("tenants",tenantService.getAllTenants());
			map.addAttribute("pageSupport", employeeService.getEmployeesBy(employee,page));
		} catch (EssException e) {
		map.addAttribute("error","&nbsp;&nbsp;&nbsp;获取租户列表出错");	
			logger.error("failed to getTenantList", e);
		}
		return "system/systemMain" ;
	}
	
	@RequestMapping("/system/tenantList2")
	public String getTenantList2(ModelMap map){
		try {
			
			map.addAttribute("tenants",tenantService.getAllTenants());
		} catch (EssException e) {
		map.addAttribute("errorr","&nbsp;&nbsp;&nbsp;获取租户列表出错");	
			logger.error("failed to getTenantList", e);
		}
		return "system/systemAdd" ;
	}
	@RequestMapping("/system/modifyPwd")
	public String modifyPwd(Employee employee) {
		try {
			 employee.setSex(-1);
			 employeeService.modifyEmployee(employee);
		} catch (EssException e) {
			logger.error("failed to modifyPwd", e);
		}
		return "redirect:/system/list";
	}
}
