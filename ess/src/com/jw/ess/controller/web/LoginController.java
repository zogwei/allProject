package com.jw.ess.controller.web;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
@Controller
public class LoginController {
	private static final Log logger =
		LogFactory.getLog(LoginController.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;
	
	@RequestMapping("/userlogin")
	public String login(String loginName,String loginPwd,ModelMap map,HttpSession session) {
		try {
			
			Employee employee = employeeService.login(loginName,loginPwd);
			
			//如果不是管理员
			if(employee.getCategory()!=CommonConstant.EMPLOYEE_ROLE_ADMIN){
				
				throw new EssException("account or password is wrong", MessageCode.EMPLOYEE_ACCOUNT_OR_PASSWORD_ERROR);
				
			}else{
				Tenant tenant=employeeService.getTenantBy(employee.getTenantId());
				session.setAttribute("tenantSession",tenant);
				session.setAttribute("userSession", employee);
				return "../manage";
			}
		} catch (EssException ex) {
			map.addAttribute("login_error","&nbsp;&nbsp;&nbsp;用户名或密码错误");
			logger.error("failed to login", ex);
			return "../login";
		}
	}
	
	@RequestMapping("/login")
	public String login(HttpSession session) {
		return "../login";
	}
	
	@RequestMapping("/logout")
	public 
	String logout(HttpSession session) {
		session.invalidate();
		return "../login";
	}
}
