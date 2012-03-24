package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Employee;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

public class EmployeeServiceTest
{
	private static final Log logger = LogFactory
			.getLog(EmployeeServiceTest.class);

	private IEmployeeService employeeService;

	@Before
	public void setUp() throws Exception
	{
		employeeService = (IEmployeeService) SpringAssisant
				.getBean("employeeService");
	}
	@Test
	public void testFindEmployees()
	{
		int tenantId = 1;
		String name = "王浩";
		int role = 0;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.EMPLOYEE_NAME, name);
		param.put(ParameterMapKeys.EMPLOYEE_CATEGORY, role);
		param.put(ParameterMapKeys.BEGIN_INDEX, 5);
		param.put(ParameterMapKeys.PAGE_SIZE, 10);
		Page page=new Page();
		Employee employee =new Employee();
		employee.setTenantId(1);
		try
		{
			System.out.println(employeeService.getEmployeesBy(employee, page));
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindEmployees");
		}
	}
	@Test
	public void testAddEmployee()
	{
		Employee emp = new Employee();
		emp.setAccount("liuhui");
		emp.setAddress("shangqiu");
		emp.setCardNo("12345");
		emp.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMAN);
		emp.setCreatedDate(DateUtil.currentTimeSecs());
		emp.setDesc("i am a salesman");
		emp.setIsValid(CommonConstant.STATE_VALID);
		emp.setName("陈明");
		emp.setPassword("123456");
		emp.setPhone("13567894125");
		emp.setSex(CommonConstant.EMPLOYEE_SEX_MAN);
		emp.setState(CommonConstant.EMPLOYEE_MISSION);
		emp.setTenantId(1);

		try
		{
			employeeService.addEmployee(emp);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddEmployee");
		}
	}

	@Test
	public void testGetEmployeesBy()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testModifyEmployee()
	{
		Employee emp = new Employee();
		emp.setTenantId(1);
		emp.setId(2);
		emp.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMANAGER);
		emp.setCreatedDate(DateUtil.currentTimeSecs());
		emp.setDesc("i am a salesmanager");
		emp.setIsValid(CommonConstant.STATE_VALID);
		emp.setName("王浩");
		emp.setPassword("123456");
		emp.setPhone("13163743997");
		emp.setSex(CommonConstant.EMPLOYEE_SEX_WOMAN);
		emp.setState(CommonConstant.EMPLOYEE_MISSION);

		try
		{
			employeeService.modifyEmployee(emp);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testModifyEmployee");
		}
	}

}
