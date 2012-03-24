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
import com.jw.ess.converter.constant.EmployeeConstant;
import com.jw.ess.entity.Employee;

@Component("employeeLoginConverter")
public class EmployeeLoginConverter extends DefaultXmlConverter<Employee> {
	
	private static final Log logger = LogFactory.getLog(EmployeeLoginConverter.class);

	@Override
	public Employee fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			// account
			String account = conditionEle.elementTextTrim(EmployeeConstant.ACCOUNT);
			// password
			String password = conditionEle.elementTextTrim(EmployeeConstant.PASSWORD);

			Employee emp = new Employee();
			emp.setAccount(account);
			emp.setPassword(password);
			return emp;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	protected String toContent(Employee e) {
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(EmployeeConstant.EMPLOYEE_START);
		
		content.append(EmployeeConstant.EMPLOYEE_ID_START);
		content.append(e.getId());
		content.append(EmployeeConstant.EMPLOYEE_ID_END);
		
		content.append(EmployeeConstant.ACCOUNT_START);
		content.append(e.getAccount());
		content.append(EmployeeConstant.ACCOUNT_END);
		
		content.append(EmployeeConstant.PASSWORD_START);
		content.append(e.getPassword());
		content.append(EmployeeConstant.PASSWORD_END);
		
		content.append(EmployeeConstant.EMPLOYEE_NAME_START);
		content.append(e.getName());
		content.append(EmployeeConstant.EMPLOYEE_NAME_END);
		
		content.append(EmployeeConstant.SEX_START);
		content.append(e.getSex());
		content.append(EmployeeConstant.SEX_END);
		
		content.append(EmployeeConstant.PHONE_START);
		content.append(StringUtils.defaultString(e.getPhone()));
		content.append(EmployeeConstant.PHONE_END);
		
		content.append(EmployeeConstant.ADDRESS_START);
		content.append(StringUtils.defaultString(e.getAddress()));
		content.append(EmployeeConstant.ADDRESS_END);
		
		content.append(EmployeeConstant.CARD_NO_START);
		content.append(StringUtils.defaultString(e.getCardNo()));
		content.append(EmployeeConstant.CARD_NO_END);
		
		content.append(EmployeeConstant.STATE_START);
		content.append(e.getState());
		content.append(EmployeeConstant.STATE_END);
		
		content.append(EmployeeConstant.CATEGORY_START);
		content.append(e.getCategory());
		content.append(EmployeeConstant.CATEGORY_END);
		
		content.append(EmployeeConstant.IS_VALID_START);
		content.append(e.getIsValid());
		content.append(EmployeeConstant.IS_VALID_END);
		
		content.append(EmployeeConstant.CREATE_DATE_START);
		content.append(e.getCreatedDate());
		content.append(EmployeeConstant.CREATE_DATE_END);
		
		content.append(EmployeeConstant.DESCRIPTION_START);
		content.append(StringUtils.defaultString(e.getDesc()));
		content.append(EmployeeConstant.DESCRIPTION_END);
		
		content.append(EmployeeConstant.TENANT_ID_START);
		content.append(e.getTenantId());
		content.append(EmployeeConstant.TENANT_ID_END);
		
		content.append(EmployeeConstant.EMPLOYEE_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
