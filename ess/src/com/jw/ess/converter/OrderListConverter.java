package com.jw.ess.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.converter.constant.OrderStateTraceConstant;
import com.jw.ess.entity.Order;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.page.PageSupport;

@Component("orderListConverter")
public class OrderListConverter extends
		DefaultXmlConverter<Map<String, Object>> {
	private static final Log logger = LogFactory
			.getLog(OrderListConverter.class);

	@Override
	public Map<String, Object> fromXml(String xml) {
		Document doc;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			paramMap.put(ParameterMapKeys.OPERATOR_ID, conditionEle
					.elementTextTrim(OrderConstant.ORDER_OPERATOR_ID));
			if (StringUtils
					.isBlank(conditionEle
							.elementTextTrim(OrderStateTraceConstant.ORDER_STATE_TRACE_STATE_ID))) {
				paramMap.put(ParameterMapKeys.CURRENT_STATE, 0);
			} else {
				paramMap
						.put(
								ParameterMapKeys.CURRENT_STATE,
								Integer
										.parseInt(conditionEle
												.elementTextTrim(OrderStateTraceConstant.ORDER_STATE_TRACE_STATE_ID)));
			}
			if (StringUtils
					.isBlank(conditionEle
							.elementTextTrim(OrderStateTraceConstant.ORDER_OPERATE_DATE_BEGIN))) {
				paramMap.put(ParameterMapKeys.START_TIME, 0);
			} else {
				paramMap
						.put(
								ParameterMapKeys.START_TIME,
								Integer
										.parseInt(conditionEle
												.elementTextTrim(OrderStateTraceConstant.ORDER_OPERATE_DATE_BEGIN)));
			}
			if (StringUtils
					.isBlank(conditionEle
							.elementTextTrim(OrderStateTraceConstant.ORDER_OPERATE_DATE_END))) {
				paramMap.put(ParameterMapKeys.END_TIME, 0);
			} else {
				paramMap
						.put(
								ParameterMapKeys.END_TIME,
								Integer
										.parseInt(conditionEle
												.elementTextTrim(OrderStateTraceConstant.ORDER_OPERATE_DATE_END)));
			}
			if (StringUtils.isBlank(conditionEle
					.elementTextTrim(OrderConstant.ORDER_MIN_AMOUNT))) {
				paramMap.put(ParameterMapKeys.MIN_AMOUNT, 0);
			} else {
				paramMap
						.put(
								ParameterMapKeys.MIN_AMOUNT,
								Double
										.parseDouble(conditionEle
												.elementTextTrim(OrderConstant.ORDER_MIN_AMOUNT)));
			}
			if (StringUtils.isBlank(conditionEle
					.elementTextTrim(OrderConstant.ORDER_MAX_AMOUNT))) {
				paramMap.put(ParameterMapKeys.MAX_AMOUNT, 0);
			} else {
				paramMap
						.put(
								ParameterMapKeys.MAX_AMOUNT,
								Double
										.parseDouble(conditionEle
												.elementTextTrim(OrderConstant.ORDER_MAX_AMOUNT)));
			}
			Element pageEle = requestEle.element(Commons.PAGE);

			paramMap
					.put(
							ParameterMapKeys.CURRENT_PAGE,
							Integer
									.parseInt(pageEle
											.elementTextTrim(OrderConstant.ORDER_CURRENT_PAGE)));
			paramMap.put(ParameterMapKeys.PAGE_SIZE, Integer.parseInt(pageEle
					.elementTextTrim(OrderConstant.ORDER_PAGE_SIZE)));

		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
		return paramMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String, Object> t) {
		StringBuilder content = new StringBuilder();
		PageSupport<Order> orders = (PageSupport<Order>) t.get("orders");
		content.append(OrderConstant.ORDERS_START);
		if (orders.getResult().isEmpty()) {
			
		} else {
			List<Order> os = orders.getResult();
			for (Order o : os)

			{

				content.append(OrderConstant.ORDER_START);
				content.append(OrderConstant.ORDER_ID_START);
				content.append(o.getId());
				content.append(OrderConstant.ORDER_ID_END);
				// orderNo
				content.append(OrderConstant.ORDER_NO_START);
				content.append(o.getOrderNo());
				content.append(OrderConstant.ORDER_NO_END);
				// amount
				content.append(OrderConstant.ORDER_AMOUNT_START);
				content.append(o.getAmount());
				content.append(OrderConstant.ORDER_AMOUNT_END);
				// imprest
				content.append(OrderConstant.ORDER_IMPREST_START);
				content.append(o.getImprest());
				content.append(OrderConstant.ORDER_IMPREST_END);
				// refund
				content.append(OrderConstant.ORDER_REFUND_START);
				content.append(o.getRefund());
				content.append(OrderConstant.ORDER_REFUND_END);
				// received
				content.append(OrderConstant.ORDER_RECEIVED_START);
				content.append(o.getReceived());
				content.append(OrderConstant.ORDER_RECEIVED_END);
				// operateDate
				content
						.append(OrderStateTraceConstant.ORDER_STATE_TRACE_ORDER_OPERATE_DATE_START);
				content.append(o.getStateTraces().get(0).getOperateDate());
				content
						.append(OrderStateTraceConstant.ORDER_STATE_TRACE_ORDER_OPERATE_DATE_END);
				
				// deliveryDate
				content.append(OrderConstant.ORDER_DELIVERYDATE_START);
				content.append(o.getDeliveryDate()!=null?o.getDeliveryDate():"");
				content.append(OrderConstant.ORDER_DELIVERYDATE_END);
				// customer
				content.append(OrderConstant.ORDER_CUSTOMER_START);
				// name
				content.append(CustomerConstant.CUSTOMER_NAME_START);
				content.append(o.getCustomer().getName());
				content.append(CustomerConstant.CUSTOMER_NAME_END);
				// linkman
				content.append(CustomerConstant.LINK_MAN_START);
				content.append(o.getCustomer().getLinkman());
				content.append(CustomerConstant.LINK_MAN_END);
				// phone
				content.append(CustomerConstant.PHONE_START);
				content.append(o.getCustomer().getPhone());
				content.append(CustomerConstant.PHONE_END);
				// address
				content.append(CustomerConstant.ADDRESS_START);
				content.append(o.getCustomer().getAddress());
				content.append(CustomerConstant.ADDRESS_END);
				content.append(OrderConstant.ORDER_CUSTOMER_END);

				content.append(OrderConstant.ORDER_END);

			}
		}
		content.append(OrderConstant.ORDERS_END);
		content.append(Commons.PAGE_START);
		content.append(Commons.CURRENT_PAGE_START);
		content.append(orders.getCurrentPage());
		content.append(Commons.CURRENT_PAGE_END);
		content.append(Commons.PAGE_SIZE_START);
		content.append(orders.getPageSize());
		content.append(Commons.PAGE_SIZE_END);
		content.append(Commons.COUNT_START);
		content.append(orders.getCount());
		content.append(Commons.COUNT_END);
		content.append(Commons.PAGE_END);
		return content.toString();
	}

}
