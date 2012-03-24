package com.jw.ess.controller.client;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.converter.XmlConverter;
import com.jw.ess.entity.Employee;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Controller
public class EmployeeEndpoint {
	private static final Log logger = LogFactory.getLog(EmployeeEndpoint.class);

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;

	@Resource(name = "employeeLoginConverter")
	private XmlConverter<Employee> employeeLoginConverter;

	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;

	@RequestMapping("/employee/c/login")
	public @ResponseBody
	String login(HttpEntity<String> entity) {
		String response;
		try {
			Employee e = employeeLoginConverter.fromXml(entity.getBody());
			
			Employee employee = employeeService.login(e.getAccount(), e.getPassword());
			
			//如果是销售经理或者销售人员
			if(employee.getCategory()==CommonConstant.EMPLOYEE_ROLE_SALESMANAGER||
					employee.getCategory()==CommonConstant.EMPLOYEE_ROLE_SALESMAN){
				
				response = employeeLoginConverter.toXml(employee);
				
			}else{
				
				throw new EssException("account or password is wrong", MessageCode.EMPLOYEE_ACCOUNT_OR_PASSWORD_ERROR);
				
			}
		} catch (EssException e) {
			logger.error("failed to login", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
	public @ResponseBody
	String findOrderList( HttpEntity<String> entity)
	{
		return null;
	
	}
}
