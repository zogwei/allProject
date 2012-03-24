package com.jw.ess.service;

import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Tenant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public interface IEmployeeService {

	boolean isExistAccount(String account)throws EssException;
		
	/**
	 * 添加新员工
	 * 
	 * @param employee
	 *            员工对象
	 * @throws EssException
	 */
	void addEmployee(Employee employee) throws EssException;

	/**
	 * 修改员工信息
	 * 
	 * @param employee
	 *            员工信息
	 * @throws EssException
	 */
	void modifyEmployee(Employee employee) throws EssException;

	/**
	 * 根据员工id查询员工信息
	 * 
	 * @param employeeId
	 *            员工id
	 * @return 员工对象
	 * @throws EssException
	 */
	Employee getEmployeeById(int employeeId) throws EssException;

	/**
	 * 员工登录
	 * <p>TODO</p>
	 * @param account 账号
	 * @param password 密码
	 * @return
	 * @throws EssException
	 * @author {替换成自己的中文名字}
	 */
	Employee login(String account, String password) throws EssException;

	/**
	 * 根据条件查询员工列表
	 * 
	 * @param employee
	 *            员工对象
	 * @param page
	 *            分页对象
	 * @return 员工列表
	 * @throws EssException
	 */
	PageSupport<Employee> getEmployeesBy(Employee employee, Page page) throws EssException;

	boolean isExistName(String employeeName,int tenantId,int employeeId)throws EssException;
	
	//获取tenant
	Tenant getTenantBy(int tenantId)throws EssException;
}
