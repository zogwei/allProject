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

@Component("customerModifyConverter")
public class CustomerModifyConverter extends DefaultXmlConverter<Customer> {
	private static final Log logger = LogFactory.getLog(CustomerModifyConverter.class);

	@Override
	public Customer fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			
			int id	= Integer.parseInt(conditionEle.elementTextTrim(CustomerConstant.CUSTOMER_ID));
			
			String name = conditionEle.elementTextTrim(CustomerConstant.CUSTOMER_NAME);
			
			String linkman = conditionEle.elementTextTrim(CustomerConstant.LINK_MAN);
			
			String phone = conditionEle.elementTextTrim(CustomerConstant.PHONE);

			String address = conditionEle.elementTextTrim(CustomerConstant.ADDRESS);

			String desc = conditionEle.elementTextTrim(CustomerConstant.DESCRIPTION);
			
			Customer customer = new Customer();
			customer.setId(id);
			customer.setName(name);
			customer.setLinkman(linkman);
			customer.setPhone(phone);
			customer.setAddress(address);
			customer.setDesc(desc);
			
			return customer;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}


}
