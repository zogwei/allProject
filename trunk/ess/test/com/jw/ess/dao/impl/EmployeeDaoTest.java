package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.IEmployeeDao;
import com.jw.ess.entity.Employee;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class EmployeeDaoTest
{
	private static final Log logger = LogFactory.getLog(EmployeeDaoTest.class);

	private IEmployeeDao employeeDao;

	@Before
	public void setUp() throws Exception
	{
		employeeDao = SpringAssisant.getBean(EmployeeDao.class);
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

		try
		{
			List<Employee> emps = employeeDao.findEmployees(param);
			logger.debug(emps);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindEmployees");
		}
	}

	@Test
	public void testFindEmployeeAccount()
	{
		String account = "wanghao";
		try
		{
			String matchedAccount = employeeDao.findEmployeeAccount(account);
			logger.debug("matched account : " + matchedAccount);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindEmployeeAccount");
		}
	}

	@Test
	public void testFindEmployeeName()
	{
		int tenantId = 1;
		String employeeName = "陈明";
		try
		{
			String matchedName = employeeDao.findEmployeeName(tenantId,
					employeeName);
			logger.debug("matched name : " + matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindEmployeeAccount");
		}
	}

	@Test
	public void testInsertEmployee()
	{
		Employee emp = new Employee();
		emp.setAccount("chenming");
		emp.setAddress("shangqiu");
		emp.setCardNo("12345");
		emp.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMAN);
		emp.setCreatedDate(DateUtil.currentTimeSecs());
		emp.setDesc("i am a salesman");
		emp.setIsValid(CommonConstant.STATE_VALID);
		emp.setName("李辉");
		emp.setPassword("123456");
		emp.setPhone("13567894125");
		emp.setSex(CommonConstant.EMPLOYEE_SEX_MAN);
		emp.setState(CommonConstant.EMPLOYEE_MISSION);
		emp.setTenantId(1);

		try
		{
			employeeDao.insertEmployee(emp);
			logger.debug(emp.getId());
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertEmployee");
		}
	}

	@Test
	public void testUpdateEmployee()
	{
		Employee emp = new Employee();
		emp.setId(2);
		emp.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMANAGER);
		emp.setCreatedDate(DateUtil.currentTimeSecs());
		emp.setDesc("i am a salesmanager");
		emp.setIsValid(CommonConstant.STATE_VALID);
		emp.setName("李芳");
		emp.setPassword("123456");
		emp.setPhone("13163743997");
		emp.setSex(CommonConstant.EMPLOYEE_SEX_WOMAN);
		emp.setState(CommonConstant.EMPLOYEE_MISSION);

		try
		{
			employeeDao.updateEmployee(emp);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testUpdateEmployee");
		}
	}

	@Test
	public void testFindCountOfEmployee()
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
		try
		{
			logger.debug("employee count : "
					+ employeeDao.findCountOfEmployee(param));
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCountOfEmployee");
		}
	}

	@Test
	public void testFindEmployeeBy()
	{
		String account = "wanghao";
		String password = "123456";

		try
		{
			Employee emp = employeeDao.findEmployeeBy(account, password);
			logger.debug(emp);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCountOfEmployee");
		}
	}

	@Test
	public void testFindEmployeeById()
	{
		int employeeId = 2;

		try
		{
			Employee emp = employeeDao.findEmployeeById(employeeId);
			logger.debug(emp);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCountOfEmployee");
		}
	}
}
