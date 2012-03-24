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
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.entity.Customer;
import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Order;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.generator.DefaultNumberGenerator;

@Component("orderAddConverter")
public class OrderAddConverter extends DefaultXmlConverter<Order>
{
	private static final Log logger = LogFactory
			.getLog(EmployeeLoginConverter.class);

	@Override
	public Order fromXml(String xml)
	{
		Order order = new Order();
		try
		{
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			// order
			Element orderEle = conditionEle.element(OrderConstant.ORDER);
			// orderNo
			order.setOrderNo(DefaultNumberGenerator.OrderNumberGenerate());
			// isValid
			if (StringUtils.isBlank(orderEle
					.elementTextTrim(OrderConstant.ORDER_IS_VALID)))
			{
				order.setIsValid(CommonConstant.STATE_VALID);
			}
			else
			{
				order.setIsValid(Integer.parseInt(orderEle
						.elementTextTrim(OrderConstant.ORDER_IS_VALID)));
			}
			//imprest
			if(StringUtils.isBlank(orderEle.elementTextTrim(OrderConstant.ORDER_IMPREST)))
			{
				order.setImprest(0);
			}else
			{
				order.setImprest(Double.parseDouble(orderEle.elementTextTrim(OrderConstant.ORDER_IMPREST)));
			}
			//deliveryDate
			if(StringUtils.isBlank(orderEle.elementTextTrim(OrderConstant.ORDER_DELIVERYDATE)))
			{
				order.setDeliveryDate("");
			}else
			{
				order.setDeliveryDate(orderEle.elementTextTrim(OrderConstant.ORDER_DELIVERYDATE));
			}
			// Amount
			order.setAmount(Double.parseDouble(orderEle
					.elementTextTrim(OrderConstant.ORDER_AMOUNT)));
			// tenantId
			order.setTenantId(Integer.parseInt(orderEle
					.elementTextTrim(OrderConstant.ORDER_TENANT_ID)));
			// costomerId
			Customer customer = new Customer();
			customer.setId(Integer.parseInt(orderEle.elementTextTrim(
					OrderConstant.ORDER_CUSTOMER_ID)));
			order.setCustomer(customer);
			Employee operator = new Employee();
			operator.setId(Integer.parseInt(orderEle.elementTextTrim(
					OrderConstant.ORDER_OPERATOR_ID)));
			order.setOperator(operator);
			return order;

		}
		catch (DocumentException e)
		{
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	protected String toContent(Order t)
	{
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(OrderConstant.ORDER_START);
		content.append(OrderConstant.ORDER_ID_START);
		content.append(t.getId());
		content.append(OrderConstant.ORDER_ID_END);
		content.append(OrderConstant.ORDER_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
