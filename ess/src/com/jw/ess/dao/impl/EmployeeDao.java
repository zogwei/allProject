package com.jw.ess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IEmployeeDao;
import com.jw.ess.entity.Employee;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.TypeUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("employeeDao")
public class EmployeeDao implements IEmployeeDao
{

	private static final Log logger = LogFactory.getLog(EmployeeDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_EMPLOYEE = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".insertEmployee";

	private static final String FIND_EMPLOYEE_ACCOUNT = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployeeAccount";

	private static final String FIND_EMPLOYEES = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployees";

	private static final String UPDATE_EMPLOYEE = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".updateEmployee";

	private static final String FIND_EMPLOYEES_COUNT = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findCountOfEmployee";

	private static final String FIND_EMPLOYEE = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployee";

	private static final String FIND_EMPLOYEE_NAME_EXCLUDE_SELF = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployeeNameExcludeSelf";

	private static final String FIND_EMPLOYEE_NAME = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployeeName";

	private static final String FIND_EMPLOYEE_BY_ID = MapperConstant.MAPPER_NAMESPACE_EMPLOYEE
			+ ".findEmployeeById";

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> findEmployees(Map<String, Object> param)
			throws EssException
	{
		try
		{
			return (List<Employee>) sqlSessionTemplate.selectList(
					FIND_EMPLOYEES, param);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findAllEmployees", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findEmployeeAccount(String account) throws EssException
	{
		try
		{
			return (String) sqlSessionTemplate.selectOne(FIND_EMPLOYEE_ACCOUNT,
					account);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findEmployeeAccount", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public int insertEmployee(Employee employee) throws EssException
	{
		try
		{
			sqlSessionTemplate.insert(INSERT_EMPLOYEE, employee);
			return employee.getId();
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertEmployee", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateEmployee(Employee employee) throws EssException
	{
		try
		{
			sqlSessionTemplate.update(UPDATE_EMPLOYEE, employee);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to updateEmployee", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public int findCountOfEmployee(Map<String, Object> param)
			throws EssException
	{
		try
		{
			return TypeUtil.toInt(sqlSessionTemplate.selectOne(
					FIND_EMPLOYEES_COUNT, param));
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findCountOfEmployee", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Employee findEmployeeBy(String account, String password)
			throws EssException
	{
		Employee employee = new Employee();
		employee.setAccount(account);
		employee.setPassword(password);
		try
		{
			return (Employee) sqlSessionTemplate.selectOne(FIND_EMPLOYEE,
					employee);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findEmployeeBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findEmployeeName(int tenantId, String employeeName)
			throws EssException
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.EMPLOYEE_NAME, employeeName);

		try
		{
			return (String) sqlSessionTemplate.selectOne(FIND_EMPLOYEE_NAME,
					param);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findEmployeeName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findEmployeeName(int tenantId, int employeeId,
			String employeeName) throws EssException
	{
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		param.put(ParameterMapKeys.EMPLOYEE_NAME, employeeName);

		try
		{
			return (String) sqlSessionTemplate.selectOne(
					FIND_EMPLOYEE_NAME_EXCLUDE_SELF, param);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findEmployeeNameExcludeSelf", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Employee findEmployeeById(int employeeId) throws EssException
	{
		try
		{
			return (Employee) sqlSessionTemplate.selectOne(FIND_EMPLOYEE_BY_ID,
					employeeId);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findEmployeeById", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}


}
