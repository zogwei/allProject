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
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.OrderItem;

@Component("orderItemAddConverter")
public class OrderItemAddConverter extends DefaultXmlConverter<List<OrderItem>> {


	private static final Log logger = LogFactory.getLog(OrderItemAddConverter.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItem> fromXml(String xml) {
		List<OrderItem> items = new ArrayList<OrderItem>();
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			//order
			Element order = conditionEle.element(OrderConstant.ORDER);
			// items
			Element itemsEle = order.element(OrderItemConstant.ORDER_ITEMS);

			List<Element> orderItems = itemsEle.elements(OrderItemConstant.ORDER_ITEM);
			for (Element e : orderItems) {

				OrderItem item = new OrderItem();
				//amount
				item.setAmount(Double.parseDouble(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_AMOUNT)));
				//area
				item.setArea(Double.parseDouble(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_AREA)));
				//sellPrice
				item.setSellPrice(Double.parseDouble(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_SELLPRICE)));
				//floorId
				Floor floor = new Floor();
				floor.setId(Integer.parseInt(e.elementTextTrim(OrderItemConstant.ORDER_ITEM_FLOOR_ID)));
				item.setFloor(floor);
				items.add(item);
			}
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

		return items;
	}

}
