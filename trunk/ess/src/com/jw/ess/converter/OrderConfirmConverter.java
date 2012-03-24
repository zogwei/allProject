package com.jw.ess.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.entity.Order;

@Component("orderConfirmConverter")
public class OrderConfirmConverter extends DefaultXmlConverter<Order> {
	private static final Log logger = LogFactory.getLog(OrderConfirmConverter.class);

	@Override
	public Order fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			Element orderEle = conditionEle.element(OrderConstant.ORDER);
			Order order = new Order();
			order.setId(Integer.parseInt(orderEle.elementTextTrim(OrderConstant.ORDER_ID)));
			order.setReceived(Double.parseDouble(orderEle.elementTextTrim(OrderConstant.ORDERS_RECEIVED)));
			return order;

		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

}
