package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Customer;
import com.jw.ess.util.ex.EssException;

public interface ICustomerService
{
	/**
	 * 功能 添加客户
	 * 
	 * @param Customer
	 * @return Customer_id
	 * */
	void addCustomer(Customer customer) throws EssException;

	/**
	 * 修改客户信息
	 * 
	 * @param customer
	 *            新的客户对象
	 * @throws EssException
	 */
	void modifyCustomer(Customer customer) throws EssException;

	/**
	 * 根据id查询客户信息
	 * 
	 * @param customerId
	 *            客户id
	 * @return 客户对象
	 * @throws EssException
	 */
	Customer getCustomerById(int customerId) throws EssException;

	/**
	 * 根据销售人员id查询客户
	 * 
	 * @param employeeId
	 *            销售人员id
	 * @return 客户列表
	 * @throws EssException
	 */
	List<Customer> getCustomersBy(int employeeId) throws EssException;

	List<Customer> getCustomers(Customer customer)throws EssException;
}