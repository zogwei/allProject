package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.Customer;
import com.jw.ess.util.ex.EssException;

public interface ICustomerDao
{
	/**
	 * @author issuser 功能 添加客户
	 * @param Customer
	 * @return Customer_id
	 * */
	void insertCustomer(Customer customer) throws EssException;

	/**
	 * 修改客户信息
	 * 
	 * @param customer
	 *            新的客户信息
	 * @throws EssException
	 */
	void updateCustomer(Customer customer) throws EssException;

	/**
	 * 根据id查询客户信息
	 * 
	 * @param customerId
	 *            客户id
	 * @return 客户对象
	 * @throws EssException
	 */
	Customer findCustomerById(int customerId) throws EssException;

	/**
	 * 根据销售人员id查询客户
	 * 
	 * @param employeeId
	 *            销售人员id
	 * @return 客户列表
	 * @throws EssException
	 */
	List<Customer> findCustomersBy(int employeeId) throws EssException;

	/**
	 * 查询客户名称
	 * 
	 * @param employeeId
	 *            销售人员id
	 * @param customerName
	 *            客户名称
	 * @return 客户名称
	 * @throws EssException
	 */
	String findCustomerName(int employeeId, String customerName)
			throws EssException;

	/**
	 * 查询客户名称
	 * 
	 * @param employeeId
	 *            销售人员id
	 * @param customerId
	 *            客户id
	 * @param customerName
	 *            客户名称
	 * @return 客户名称
	 */
	String findCustomerName(int employeeId, int customerId, String customerName)
			throws EssException;
			
		/**
	 * 
	 * @param param
	 * @return
	 * @throws EssException
	 */
	List<Customer> findCustomers(Customer param) throws EssException;
}
