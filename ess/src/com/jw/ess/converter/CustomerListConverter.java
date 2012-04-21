package com.jw.ess.converter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.CustomerConstant;
import com.jw.ess.entity.Customer;
@Component("customerListConverter")
public class CustomerListConverter extends DefaultXmlConverter<List<Customer>> {
	private static final Log logger = LogFactory.getLog(CustomerListConverter.class);

	@Override
	public List<Customer> fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			int employeeId = Integer.parseInt(conditionEle.elementTextTrim(CustomerConstant.EMPLOYEE_ID));

			Customer customer = new Customer();
			customer.setEmployeeId(employeeId);
			List<Customer> list=new ArrayList<Customer>();
			list.add(customer);
			return list;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	protected String toContent(List<Customer> customers) {
		if(customers.size()==0){
			return super.toContent(customers);
		}
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(CustomerConstant.CUSTOMERS_START);
		for(Customer c:customers){
		content.append(CustomerConstant.CUSTOMER_START);
		content.append(CustomerConstant.CUSTOMER_ID_START);
		content.append(c.getId());
		content.append(CustomerConstant.CUSTOMER_ID_END);
		
		content.append(CustomerConstant.CUSTOMER_NAME_START);
		content.append(c.getName());
		content.append(CustomerConstant.CUSTOMER_NAME_END);
		
		content.append(CustomerConstant.LINK_MAN_START);
		content.append(StringUtils.defaultString(c.getLinkman()));
		content.append(CustomerConstant.LINK_MAN_END);
		
		content.append(CustomerConstant.PHONE_START);
		content.append(StringUtils.defaultString(c.getPhone()));
		content.append(CustomerConstant.PHONE_END);
		
		content.append(CustomerConstant.TELNUM_START);
		content.append(StringUtils.defaultString(c.getTelNum()));
		content.append(CustomerConstant.TELNUM_END);
		
		content.append(CustomerConstant.ADDRESS_START);
		content.append(StringUtils.defaultString(c.getAddress()));
		content.append(CustomerConstant.ADDRESS_END);
		
		content.append(CustomerConstant.IS_VALID_START);
		content.append(c.getIsValid());
		content.append(CustomerConstant.IS_VALID_END);
		
		content.append(CustomerConstant.CREATE_DATE_START);
		content.append(c.getCreatedDate());
		content.append(CustomerConstant.CREATE_DATE_END);
		
		content.append(CustomerConstant.DESCRIPTION_START);
		content.append(StringUtils.defaultString(c.getDesc()));
		content.append(CustomerConstant.DESCRIPTION_END);
		
		content.append(CustomerConstant.TENANT_ID_START);
		content.append(c.getTenantId());
		content.append(CustomerConstant.TENANT_ID_END);
		
		content.append(CustomerConstant.EMPLOYEE_ID_START);
		content.append(c.getEmployeeId());
		content.append(CustomerConstant.EMPLOYEE_ID_END);
		
		content.append(CustomerConstant.CUSTOMER_END);
		}
		content.append(CustomerConstant.CUSTOMERS_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
