package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.Employee;
import com.jw.ess.util.ex.EssException;

public interface IEmployeeDao
{
	/**
	 * @author issuser 功能 添加员工
	 * @param Employee
	 * @return Employee_id
	 * */
	int insertEmployee(Employee employee) throws EssException;

	/**
	 * 根据条件查询员工列表
	 * 
	 * @param param
	 *            条件
	 * @return 匹配的员工列表
	 * @throws EssException
	 */
	List<Employee> findEmployees(Map<String, Object> param) throws EssException;

	/**
	 * 根据员工id查询员工信息
	 * 
	 * @param employeeId
	 *            员工id
	 * @return 员工对象
	 * @throws EssException
	 */
	Employee findEmployeeById(int employeeId) throws EssException;

	/**
	 * @author issuser 功能:查询是否存在Account
	 * @param String
	 *            account
	 * @return String account
	 * */
	String findEmployeeAccount(String account) throws EssException;

	/**
	 * 查询员工姓名
	 * 
	 * @param tenantId
	 *            租户id
	 * @param employeeId
	 *            员工id
	 * @param employeeName
	 *            员工姓名
	 * @return 员工姓名
	 * @throws EssException
	 */
	String findEmployeeName(int tenantId, int employeeId, String employeeName)
			throws EssException;

	/**
	 * 查询员工姓名
	 * 
	 * @param tenantId
	 *            租户id
	 * @param employeeName
	 *            员工姓名
	 * @return 员工姓名
	 * @throws EssException
	 */
	String findEmployeeName(int tenantId, String employeeName)
			throws EssException;

	/**
	 * @author issuser 功能:依据account获取员工信息
	 * @param String
	 *            account
	 * @return String account
	 * */
	Employee findEmployeeBy(String account, String password)
			throws EssException;

	/**
	 * @author issuser 功能:依据account修改员工信息
	 * @param String
	 *            account and...
	 * @return 影响行数
	 * **/
	void updateEmployee(Employee employee) throws EssException;

	/**
	 * 根据条件获取员工总数
	 * 
	 * @param param
	 *            条件
	 * @return 员工总数
	 * @throws EssException
	 */
	int findCountOfEmployee(Map<String, Object> param) throws EssException;

}
