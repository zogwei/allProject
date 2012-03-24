package com.jw.ess.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.IEmployeeDao;
import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.service.ITenantService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

@Service("employeeService")
@Transactional(rollbackFor = EssException.class)
public class EmployeeService implements IEmployeeService {
	private static final Log logger = LogFactory.getLog(EmployeeService.class);

	@Resource(name = "employeeDao")
	private IEmployeeDao employeeDao;
	
	@Resource(name = "tenantService")
	private ITenantService tenantService;

	@Override
	public void addEmployee(Employee employee) throws EssException {
		// check account exists or not
		checkEmployeeAccount(employeeDao.findEmployeeAccount(employee.getAccount()));
		// check name exists or not
		checkEmployeeName(employeeDao.findEmployeeName(employee.getTenantId(), employee.getName()));

		employeeDao.insertEmployee(employee);
	}

	private void checkEmployeeAccount(String account) throws EssException {
		if (account != null) {
			logger.error("employee account " + account + " is already exists");
			throw new EssException("employee account " + account + " is already exists",
					MessageCode.EMPLOYEE_ACCOUNT_ALREADY_EXISTS);
		}
	}

	private void checkEmployeeName(String name) throws EssException {
		if (name != null) {
			logger.error("employee name " + name + " is already exists");
			throw new EssException("employee name " + name + " is already exists",
					MessageCode.EMPLOYEE_NAME_ALREADY_EXISTS);
		}
	}

	@Override
	public Employee getEmployeeById(int employeeId) throws EssException {
		// TODO Auto-generated method stub
		return employeeDao.findEmployeeById(employeeId);
	}

	@Override
	public PageSupport<Employee> getEmployeesBy(Employee employee, Page page) throws EssException {
		System.out.println("employee:::"+employee.toString());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, employee.getTenantId());
		param.put(ParameterMapKeys.EMPLOYEE_SHORT_NAME, employee.getName());
		param.put(ParameterMapKeys.EMPLOYEE_STATE, employee.getState());
		param.put(ParameterMapKeys.EMPLOYEE_CATEGORY, employee.getCategory());
		param.put(ParameterMapKeys.EMPLOYEE_ISVALID, employee.getIsValid());

		PageSupport<Employee> ps = new PageSupport<Employee>();
		ps.setCurrentPage(page.getCurrentPage());
		ps.setPageSize(page.getPageSize());
		// get employee count
		int count = employeeDao.findCountOfEmployee(param);
		ps.setCount(count);
		// check count equals 0 or not
		if (count != 0) {
			param.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			param.put(ParameterMapKeys.PAGE_SIZE, page.getPageSize());
			List<Employee> employees = employeeDao.findEmployees(param);
			ps.setResult(employees);
		}

		return ps;
	}

	@Override
	public Employee login(String account, String password) throws EssException {
		// TODO Auto-generated method stub
		Employee employee = employeeDao.findEmployeeBy(account, password);
		if (employee == null) {
			throw new EssException("account or password is wrong", MessageCode.EMPLOYEE_ACCOUNT_OR_PASSWORD_ERROR);
		}
		return employee;
	}

	@Override
	public void modifyEmployee(Employee employee) throws EssException {
		// check employee name exists or not
		checkEmployeeName(employeeDao.findEmployeeName(employee.getTenantId(), employee.getId(), employee.getName()));

		employeeDao.updateEmployee(employee);
	}

	@Override
	public boolean isExistAccount(String account) throws EssException {
		String result=employeeDao.findEmployeeAccount(account);
		if(!StringUtils.isBlank(result)){
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistName(String employeeName,int tenantId,int employeeId) throws EssException {
		String result=null;
		if(employeeId !=0 )
			result=employeeDao.findEmployeeName(tenantId, employeeId, employeeName);
		else
			result=employeeDao.findEmployeeName(tenantId, employeeName);
		if(!StringUtils.isBlank(result)){
			return true;
		}
		return false;
	}

	@Override
	public Tenant getTenantBy(int tenantId) throws EssException {
		return tenantService.getTenantsById(tenantId);
	}
}
