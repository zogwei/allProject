package com.jw.ess.dao.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.dao.ICustomerDao;
import com.jw.ess.entity.Customer;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class CustomerDaoTest
{
	private static final Log logger = LogFactory.getLog(CustomerDaoTest.class);

	private ICustomerDao customerDao;

	@Before
	public void setUp() throws Exception
	{
		customerDao = SpringAssisant.getBean(CustomerDao.class);
	}

	@Test
	public void testInsertCustomer()
	{
		Customer customer = new Customer();
		customer.setAddress("郎山路中");
		customer.setCreatedDate(DateUtil.currentTimeSecs());
		customer.setEmployeeId(1);
		customer.setIsValid(1);
		customer.setLinkman("赵明刚");
		customer.setName("易思博");
		customer.setPhone("13548795462");
		customer.setTenantId(1);

		try
		{
			customerDao.insertCustomer(customer);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testInsertCustomer");
		}
	}

	@Test
	public void testUpdateCustomer()
	{
		Customer customer = new Customer();
		customer.setId(2);
		customer.setAddress("深大北门");
		customer.setEmployeeId(1);
		customer.setIsValid(1);
		customer.setLinkman("简浩");
		customer.setName("易思博");
		customer.setPhone("13521195462");
		customer.setTenantId(1);

		try
		{
			customerDao.updateCustomer(customer);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testUpdateCustomer");
		}
	}

	@Test
	public void testFindCustomerById()
	{
		int customerId = 1;
		try
		{
			Customer customer = customerDao.findCustomerById(customerId);
			logger.debug(customer);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCustomerById");
		}
	}

	@Test
	public void testFindCustomersBy()
	{
		int employeeId = 1;
		try
		{
			List<Customer> customers = customerDao.findCustomersBy(employeeId);
			logger.debug(customers);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCustomersBy");
		}
	}

	@Test
	public void testFindCustomerName()
	{
		int employeeId = 1;
		String customerName = "易思博";
		try
		{
			String matchedName = customerDao.findCustomerName(employeeId,
					customerName);
			logger.debug(matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCustomerName");
		}
	}

	@Test
	public void testFindCustomerExcludeSelf()
	{
		int employeeId = 1;
		int customerId = 1;
		String customerName = "易思博";

		try
		{
			String matchedName = customerDao.findCustomerName(employeeId,
					customerId, customerName);
			logger.debug(matchedName);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testFindCustomerName");
		}
	}
}
