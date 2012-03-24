package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.ICustomerDao;
import com.jw.ess.entity.Customer;
import com.jw.ess.service.ICustomerService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Service("customerService")
public class CustomerService implements ICustomerService
{
	private static final Log logger = LogFactory.getLog(CustomerService.class);

	@Resource(name = "customerDao")
	private ICustomerDao customerDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addCustomer(Customer customer) throws EssException
	{
		String matchedName = customerDao.findCustomerName(customer
				.getEmployeeId(), customer.getName());
		if (matchedName != null)
		{
			logger.error("customer name " + customer.getName()
					+ " is already exists");
			throw new EssException("customer name " + customer.getName()
					+ " is already exists",
					MessageCode.CUSTOMER_NAME_ALREADY_EXISTS);
		}

		customerDao.insertCustomer(customer);
	}

	@Override
	public Customer getCustomerById(int customerId) throws EssException
	{
		return customerDao.findCustomerById(customerId);
	}

	@Override
	public List<Customer> getCustomersBy(int employeeId) throws EssException
	{
		// TODO Auto-generated method stub
		return customerDao.findCustomersBy(employeeId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyCustomer(Customer customer) throws EssException
	{
		String matchedName = customerDao.findCustomerName(customer
				.getEmployeeId(), customer.getId(), customer.getName());
		if (matchedName != null)
		{
			logger.error("customer name " + customer.getName()
					+ " is already exists");
			throw new EssException("customer name " + customer.getName()
					+ " is already exists",
					MessageCode.CUSTOMER_NAME_ALREADY_EXISTS);
		}

		customerDao.updateCustomer(customer);
	}

}
