package com.jw.ess.service.impl;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import com.jw.ess.entity.Customer;
import com.jw.ess.service.ICustomerService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.SpringAssisant;
import com.jw.ess.util.ex.EssException;

public class CustomerServiceTest
{
	private static final Log logger = LogFactory
			.getLog(CustomerServiceTest.class);

	private ICustomerService customerService;

	@Before
	public void setUp() throws Exception
	{
		customerService = (ICustomerService) SpringAssisant
				.getBean("customerService");
	}

	@Test
	public void testAddCustomer()
	{
		Customer customer = new Customer();
		customer.setAddress("威新软件园");
		customer.setCreatedDate(DateUtil.currentTimeSecs());
		customer.setEmployeeId(1);
		customer.setIsValid(1);
		customer.setLinkman("李芳");
		customer.setName("文思创新");
		customer.setPhone("13163743997");
		customer.setTenantId(1);

		try
		{
			customerService.addCustomer(customer);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testAddCustomer");
		}
	}

	@Test
	public void testModifyCustomer()
	{
		Customer customer = new Customer();
		customer.setId(2);
		customer.setAddress("郎山路中");
		customer.setEmployeeId(1);
		customer.setIsValid(1);
		customer.setLinkman("赵明刚");
		customer.setName("文思创新");
		customer.setPhone("13521195462");
		customer.setTenantId(1);

		try
		{
			customerService.modifyCustomer(customer);
		}
		catch (EssException e)
		{
			logger.error(e);
			fail("failed to testModifyCustomer");
		}
	}

}
