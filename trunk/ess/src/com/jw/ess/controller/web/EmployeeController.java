package com.jw.ess.controller.web;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.entity.Employee;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SessionManager;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
@Controller
public class EmployeeController {
	private static final Log logger =
		LogFactory.getLog(EmployeeController.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	
	@RequestMapping("/employee/add")
	public String addEmployee(Employee employee,ModelMap map) {
		try {
			 employee.setState(CommonConstant.EMPLOYEE_MISSION);
			 employee.setIsValid(1);
			 employee.setCreatedDate(DateUtil.currentTimeSecs());
			 employeeService.addEmployee(employee);
		} catch (EssException e) {
			
			map.put("addError","添加员工有误!");
			logger.error("failed to addEmployee", e);
			return "employee/employeeAdd";
		}
		return "redirect:/employee/list";
	}
	
	@RequestMapping("/employee/modify")
	public String modifyEmployee(Employee employee,ModelMap map) {
		try {
			 employeeService.modifyEmployee(employee);
		} catch (EssException e) {
			map.put("editError","修改员工有误!");
			logger.error("failed to login", e);
			return "employee/employeeAdd";
			
		}
		return "redirect:/employee/list";
	}
	@RequestMapping("/employee/modifyPwd")
	public String modifyPwd(Employee employee) {
		try {
			 employee.setSex(-1);
			 employeeService.modifyEmployee(employee);
		} catch (EssException e) {
			logger.error("failed to modifyPwd", e);
		}
		return "redirect:/employee/list";
	}
	@RequestMapping("/employee/editDetail")
	public String editDetail(int id,ModelMap map) {
		try {
			 map.addAttribute("employee",
					 employeeService.getEmployeeById(id));
		} catch (EssException e) {
			logger.error("failed to editDetail", e);
		}
		return "employee/employeeEdit";
	}
	
	// 根据员工id查询员工详细信息
	@RequestMapping("/employee/one")
	public String getEmployeeById(int id,ModelMap map) {
		try {
			 map.addAttribute("employee",
					 employeeService.getEmployeeById(id));
		} catch (EssException e) {
			logger.error("failed to getEmployeeById", e);
		}
		return "employee/employeeDetail";
	}
	
	//根据条件查询员工列表
	@RequestMapping("/employee/list")
	public String getEmployeesBy(Employee employee,Page page,ModelMap map,HttpSession session) {
		try {
			employee.setTenantId(SessionManager.getEmployeeFrom(session).getTenantId());
		     map.addAttribute("query",employee);
			 map.addAttribute("pageSupport",
					 employeeService.getEmployeesBy(employee,page));
		} catch (EssException e) {
			logger.error("failed to getEmployeesBy", e);
		}
		return "employee/employeeMain";
	}
	//ajax
	@RequestMapping(value="/employee/accountCheck",method=RequestMethod.POST)
	public @ResponseBody
	boolean isExistAccount(String account) {
		boolean isExist = false;
		try {
			isExist=employeeService.isExistAccount(account);
		} catch (EssException e) {
			logger.error("failed to isExistAccount", e);
		}
		return isExist;
	}
	//ajax name
	@RequestMapping(value="/employee/nameCheck",method=RequestMethod.POST)
	public @ResponseBody
	boolean isExistName(int id,String name,HttpSession session) {
		boolean isExist = false;
		try {
			int tenantId=((Employee)session.getAttribute("userSession")).getTenantId();
			isExist=employeeService.isExistName(name, tenantId, id);
		} catch (EssException e) {
			logger.error("failed to isExistName", e);
		}
		return isExist;
	}
}
