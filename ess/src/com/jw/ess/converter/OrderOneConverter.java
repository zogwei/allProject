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
import com.jw.ess.converter.constant.EmployeeConstant;
import com.jw.ess.converter.constant.FloorCategoryConstant;
import com.jw.ess.converter.constant.FloorConstant;
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.converter.constant.OrderItemConstant;
import com.jw.ess.converter.constant.OrderStateTraceConstant;
import com.jw.ess.entity.Customer;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.OrderStateTrace;

@Component("orderOneConverter")
public class OrderOneConverter extends DefaultXmlConverter<Order> {

	private static final Log logger = LogFactory.getLog(OrderOneConverter.class);
	@Override
	public Order fromXml(String xml) {

		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			
			int id = Integer.parseInt(conditionEle.elementTextTrim(OrderConstant.ORDER_ID));
			Order order = new Order();
			order.setId(id);
			return order;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
		
	}

	@Override
	protected String toContent(Order o) {
		if(o==null){
			return super.toContent(o);
		}
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(OrderConstant.ORDER_START);
		
		content.append(OrderConstant.ORDER_ID_START);
		content.append(o.getId());
		content.append(OrderConstant.ORDER_ID_END);
		
		content.append(OrderConstant.ORDER_NO_START);
		content.append(o.getOrderNo());
		content.append(OrderConstant.ORDER_NO_END);
		
		content.append(OrderConstant.ORDER_AMOUNT_START);
		content.append(o.getAmount());
		content.append(OrderConstant.ORDER_AMOUNT_END);
		
		content.append(OrderConstant.ORDER_IMPREST_START);
		content.append(o.getImprest());
		content.append(OrderConstant.ORDER_IMPREST_END);
		
		content.append(OrderConstant.ORDER_REFUND_START);
		content.append(o.getRefund());
		content.append(OrderConstant.ORDER_REFUND_END);
		
		content.append(OrderConstant.ORDER_RECEIVED_START);
		content.append(o.getReceived());
		content.append(OrderConstant.ORDER_RECEIVED_END);
		
		Customer customer=o.getCustomer();
		content.append(CustomerConstant.CUSTOMER_START);
		
		content.append(CustomerConstant.CUSTOMER_NAME_START);
		content.append(customer.getName());
		content.append(CustomerConstant.CUSTOMER_NAME_END);
		
		content.append(CustomerConstant.LINK_MAN_START);
		content.append(customer.getLinkman());
		content.append(CustomerConstant.LINK_MAN_END);
	
		
		content.append(CustomerConstant.PHONE_START);
		content.append(customer.getPhone());
		content.append(CustomerConstant.PHONE_END);
		
		content.append(CustomerConstant.ADDRESS_START);
		content.append(customer.getAddress());
		content.append(CustomerConstant.ADDRESS_END);
		
		content.append(CustomerConstant.CUSTOMER_END);
		
		content.append(EmployeeConstant.OPERATOR_START);
		content.append(o.getOperator().getName());
		content.append(EmployeeConstant.OPERATOR_END);
		
		// deliveryDate
		content.append(OrderConstant.ORDER_DELIVERYDATE_START);
		content.append(o.getDeliveryDate()!=null?o.getDeliveryDate():"");
		content.append(OrderConstant.ORDER_DELIVERYDATE_END);
		
		int items_size=o.getItems().size();
			if(items_size>0)
			  content.append(OrderItemConstant.ORDER_ITEMS_START);
		
		for(OrderItem item:o.getItems()){
			content.append(OrderItemConstant.ORDER_ITEM_START);
			
			content.append(FloorConstant.FLOOR_ID_START);
			content.append(item.getFloor().getId());
			content.append(FloorConstant.FLOOR_ID_END);
			
			content.append(FloorConstant.FLOOR_NAME_START);
			content.append(item.getFloor().getName());
			content.append(FloorConstant.FLOOR_NAME_END);
			
			content.append(FloorCategoryConstant.CATEGORY_NAME_START);
			content.append(item.getFloor().getCategory().getName());
			content.append(FloorCategoryConstant.CATEGORY_NAME_END);
			
			content.append(FloorConstant.FLOOR_SPEC_START);
			content.append(item.getFloor().getSpec().getName());
			content.append(FloorConstant.FLOOR_SPEC_END);
			
			content.append(FloorConstant.COLOR_CODE_START);
			content.append(item.getFloor().getColorCode().getName());
			content.append(FloorConstant.COLOR_CODE_END);
			
			content.append(FloorConstant.VEIN_START);
			content.append(item.getFloor().getVein().getName());
			content.append(FloorConstant.VEIN_END);
			
//			content.append(OrderItemConstant.ORDER_ITEM_QUANTITY_START);
//			content.append(item.getQuantity());
//			content.append(OrderItemConstant.ORDER_ITEM_QUANTITY_END);
			
			content.append(OrderItemConstant.ORDER_ITEM_AREA_START);
			content.append(item.getArea());
			content.append(OrderItemConstant.ORDER_ITEM_AREA_END);
			
			content.append(FloorConstant.SELL_PRICE_START);
			content.append(item.getFloor().getSellPrice());
			content.append(FloorConstant.SELL_PRICE_END);
			
			content.append(OrderItemConstant.ORDER_ITEM_AMOUNT_START);
			content.append(item.getAmount());
			content.append(OrderItemConstant.ORDER_ITEM_AMOUNT_END);
			
			content.append(OrderItemConstant.ORDER_ITEM_END);
		}
			if(items_size>0)
				  content.append(OrderItemConstant.ORDER_ITEMS_END);
			
		int trace_size=o.getStateTraces().size();
		if(trace_size>0)
			content.append(OrderStateTraceConstant.ORDER_TRACES_START);
		for(OrderStateTrace trace:o.getStateTraces()){
			content.append(OrderStateTraceConstant.ORDER_TRACE_START);
			
			content.append(OrderStateTraceConstant.ORDER_STATE_TRACE_STATE_ID_START);
			content.append(trace.getStateId());
			content.append(OrderStateTraceConstant.ORDER_STATE_TRACE_STATE_ID_END);
			
			content.append(OrderStateTraceConstant.ORDER_STATE_TRACE_ORDER_OPERATE_DATE_START);
			content.append(trace.getStateId());
			content.append(OrderStateTraceConstant.ORDER_STATE_TRACE_ORDER_OPERATE_DATE_END);
			
			content.append(OrderStateTraceConstant.ORDER_TRACE_END);
			
		}
		if(trace_size>0)
			content.append(OrderStateTraceConstant.ORDER_TRACES_END);
		content.append(OrderConstant.ORDER_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
