package com.jw.ess.converter;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;
import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.converter.constant.OrderItemConstant;
import com.jw.ess.converter.constant.OrderStateTraceConstant;
import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;

@Component("orderCancelConver")
public class OrderCancelConverter extends DefaultXmlConverter<Order> {

	private static final Log logger = LogFactory.getLog(OrderCancelConverter.class);

	@SuppressWarnings("unchecked")
	@Override
	public Order fromXml(String xml) {
		try {
			Order order = new Order();
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			Element orderEle = conditionEle.element(OrderConstant.ORDER);
			order.setId(Integer.parseInt(orderEle.elementTextTrim(OrderStateTraceConstant.ORDER_STATE_TRACE_ORDER_ID)));
			Employee operator = new Employee();
			operator.setId(Integer.parseInt(orderEle
					.elementTextTrim(OrderStateTraceConstant.ORDER_STATE_TRACE_OPERATOR_ID)));
			order.setOperator(operator);
			List<OrderItem> items = new ArrayList<OrderItem>();
			Element itemsEle = orderEle.element(OrderItemConstant.ORDER_ITEMS);
			List<Element> itemEle = itemsEle.elements(OrderItemConstant.ORDER_ITEM);
			for (Element e : itemEle) {
				OrderItem item = new OrderItem();
				item.setAmount(Double.parseDouble(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_AMOUNT)));
				Floor floor = new Floor();
				floor.setId(Integer.parseInt(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_FLOOR_ID)));
				item.setFloor(floor);
				item.setArea(Double.parseDouble(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_AREA)));
				items.add(item);
			}
			order.setItems(items);
			OrderStateTrace stateTrace = new OrderStateTrace();
			stateTrace.setStateId(CommonConstant.ORDER_STATE_CANCEL);
			stateTrace.setOrderId(order.getId());
			stateTrace.setOperateDate(DateUtil.currentTimeSecs());
			stateTrace.setDesc(orderEle.elementTextTrim(OrderStateTraceConstant.DESCRIPTION));
			List<OrderStateTrace> states = new ArrayList<OrderStateTrace>();
			states.add(stateTrace);
			order.setStateTraces(states);
			return order;

		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
	}

}
