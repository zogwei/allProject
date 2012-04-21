package com.jw.ess.converter;

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

@Component("customerQueryConverter")
public class CustomerQueryConverter extends DefaultXmlConverter<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerQueryConverter.class);

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

			String telNum = conditionEle.elementTextTrim(CustomerConstant.TELNUM);
			
			String employeeIdStr = conditionEle.elementTextTrim("employeeId");
			
			Customer customer = new Customer();
			customer.setTelNum(telNum);
			customer.setName(name);
			customer.setLinkman(linkman);
			customer.setPhone(phone);
			customer.setEmployeeId(Integer.valueOf(employeeIdStr).intValue());
			
			return customer;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}


}
