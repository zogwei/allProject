package com.jw.ess.converter;

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
import com.jw.ess.util.DateUtil;

@Component("customerAddConverter")
public class CustomerAddConverter extends DefaultXmlConverter<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerAddConverter.class);

	@Override
	public Customer fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			
			String name = conditionEle.elementTextTrim(CustomerConstant.CUSTOMER_NAME);
			
			String linkman = conditionEle.elementTextTrim(CustomerConstant.LINK_MAN);
			
			String phone = conditionEle.elementTextTrim(CustomerConstant.PHONE);

			String address = conditionEle.elementTextTrim(CustomerConstant.ADDRESS);

			int isValid = Integer.parseInt(conditionEle.elementTextTrim(CustomerConstant.IS_VALID));


			String desc = conditionEle.elementTextTrim(CustomerConstant.DESCRIPTION);

			int  tenantId= Integer.parseInt(conditionEle.elementTextTrim(CustomerConstant.TENANT_ID));

			int employeeId = Integer.parseInt(conditionEle.elementTextTrim(CustomerConstant.EMPLOYEE_ID));

			String createdDateStr = conditionEle.elementTextTrim(CustomerConstant.CREATE_DATE);
			
			int createdDate = DateUtil.currentTimeSecs();
			
			if (StringUtils.isNotBlank(createdDateStr))
			{
			createdDate = Integer.valueOf(createdDateStr);
			}
			
			Customer customer = new Customer();
			customer.setName(name);
			customer.setLinkman(linkman);
			customer.setPhone(phone);
			customer.setAddress(address);
			customer.setIsValid(isValid);
			customer.setCreatedDate(createdDate);
			customer.setDesc(desc);
			customer.setTenantId(tenantId);
			customer.setEmployeeId(employeeId);
			return customer;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	protected String toContent(Customer customer) {
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(CustomerConstant.CUSTOMER_START);
		
		content.append(CustomerConstant.CUSTOMER_ID_START);
		content.append(customer.getId());
		content.append(CustomerConstant.CUSTOMER_ID_END);
		
		content.append(CustomerConstant.CUSTOMER_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
