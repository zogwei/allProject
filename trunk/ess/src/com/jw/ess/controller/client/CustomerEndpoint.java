package com.jw.ess.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.converter.XmlConverter;
import com.jw.ess.entity.Customer;
import com.jw.ess.service.ICustomerService;
import com.jw.ess.util.ex.EssException;

@Controller
public class CustomerEndpoint
{

	private static final Log logger = LogFactory.getLog(CustomerEndpoint.class);
	@Resource(name = "customerService")
	private ICustomerService customerService;
	
	@Resource(name = "customerAddConverter")
	private XmlConverter<Customer> customerAddConverter;
	
	@Resource(name = "customerModifyConverter")
	private XmlConverter<Customer> customerModifyConverter;
	
	@Resource(name = "customerQueryConverter")
	private XmlConverter<Customer> customerQueryConverter;
	
	@Resource(name = "customerOneConverter")
	private XmlConverter<Customer> customerOneConverter;
	
	@Resource(name = "customerListConverter")
	private XmlConverter<List<Customer>> customerListConverter;

	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;

	
	@RequestMapping("/customer/c/add")
	public @ResponseBody
	String addCustomer(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Customer customer=customerAddConverter.fromXml(entity.getBody());
			customerService.addCustomer(customer);
			response = customerAddConverter.toXml(customer);
		}
		catch (EssException e)
		{
			logger.error("failed to addCustomer", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
	@RequestMapping("/customer/c/modify")
	public @ResponseBody
	String modifyCustomer(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Customer customer=customerModifyConverter.fromXml(entity.getBody());
			customerService.modifyCustomer(customer);
			response = customerModifyConverter.toXml(null);
		}
		catch (EssException e)
		{
			logger.error("failed to modifyCustomer", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	@RequestMapping("/customer/c/one")
	public @ResponseBody
	String getCustomerById(HttpEntity<String> entity)
	{
		System.out.println("=================================");
		String response;
		try
		{
			int id =customerOneConverter.fromXml(entity.getBody()).getId();
			Customer customer=customerService.getCustomerById(id);
			response = customerOneConverter.toXml(customer);
		}
		catch (EssException e)
		{
			logger.error("failed to getCustomerById", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	@RequestMapping("/customer/c/list")
	public @ResponseBody
	String getCustomersBy(HttpEntity<String> entity)
	{
		String response;
		try
		{
			int employeeId=customerListConverter.fromXml(entity.getBody()).get(0).getEmployeeId();
			List<Customer> customers=customerService.getCustomersBy(employeeId);
			response = customerListConverter.toXml(customers);
		}
		catch (EssException e)
		{
			logger.error("failed to getCustomersBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
		

	@RequestMapping("/customer/c/querylist")
	public @ResponseBody
	String getCustomers(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Customer customer=customerQueryConverter.fromXml(entity.getBody());
			List<Customer> customers=customerService.getCustomers(customer);
			response = customerListConverter.toXml(customers);
		}
		catch (EssException e)
		{
			logger.error("failed to getCustomersBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}


}
