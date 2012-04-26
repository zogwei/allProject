package com.jw.ess.controller.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jw.ess.converter.XmlConverter;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.service.IOrderService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.PageSupport;

@Controller
public class OrderEndpoint
{
	private static final Log logger = LogFactory.getLog(OrderEndpoint.class);

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "orderAddConverter")
	private XmlConverter<Order> orderAddConverter;

	@Resource(name = "orderItemAddConverter")
	private XmlConverter<List<OrderItem>> orderItemAddConverter;

	@Resource(name = "orderConfirmConverter")
	private XmlConverter<Order> orderConfirmConverter;

	@Resource(name = "orderListConverter")
	private XmlConverter<Map<String, Object>> orderListConverter;

	@Resource(name = "orderOneConverter")
	private XmlConverter<Order> orderOneConverter;

	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;

	@Resource(name = "orderCancelConver")
	private XmlConverter<Order> orderCancelConver;

	@RequestMapping("/order/c/add")
	public @ResponseBody
	String addOrder(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Order o = orderAddConverter.fromXml(entity.getBody());
			List<OrderItem> items = orderItemAddConverter.fromXml(entity
					.getBody());
			o.setItems(items);
			orderService.addOrder(o);
			response = orderAddConverter.toXml(o);
		}
		catch (EssException e)
		{
			logger.error("failed to add order", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
	@RequestMapping("/order/c/cancel")
	public @ResponseBody
	String cancelOrder(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Order order = orderCancelConver.fromXml(entity.getBody());
			orderService.cancelOrder(order,order.getRemain());
			response = orderCancelConver.toXml(order);
		}
		catch (EssException e)
		{
			logger.error("failed to cancelOrder", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}

	@RequestMapping("/order/c/list")
	public @ResponseBody
	String getOrdersBy(HttpEntity<String> entity)
	{
		String response = null;
		try
		{
			Map<String, Object> paramMap = orderListConverter.fromXml(entity
					.getBody());
			PageSupport<Order> orders = orderService.getOrdersBy(paramMap);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("orders", orders);
			response = orderListConverter.toXml(resultMap);

		}
		catch (EssException e)
		{
			logger.error("failed to list orders", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}

	// 根据订单id查询订单
	@RequestMapping("/order/c/one")
	public @ResponseBody
	String getOrderById(HttpEntity<String> entity)
	{
		String response;
		try
		{
			int id = orderOneConverter.fromXml(entity.getBody()).getId();
			Order order = orderService.getOrderById(id);
			response = orderOneConverter.toXml(order);
		}
		catch (EssException e)
		{
			logger.error("failed to getOrderById", e);
			response = exceptionConverter.toXml(e);
		}
		return response;

	}

	// 签收订单
	@RequestMapping("/order/c/confirm")
	public @ResponseBody
	String signforOrder(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Order order = orderConfirmConverter.fromXml(entity.getBody());
			orderService.orderConfirm(order.getId(),order.getReceived());
			response = orderConfirmConverter.toXml(null);
		}
		catch (EssException e)
		{
			logger.error("failed to confirm order", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
	// 新增修改订单
	@RequestMapping("/order/c/orderUpdate")
	public @ResponseBody
	String orderUpdate(HttpEntity<String> entity)
	{
		String response;
		try
		{
			Order o = orderAddConverter.fromXml(entity.getBody());
			List<OrderItem> items = orderItemAddConverter.fromXml(entity
					.getBody());
			o.setItems(items);
			orderService.addOrder(o);
			response = orderAddConverter.toXml(o);
		}
		catch (EssException e)
		{
			logger.error("failed to add order", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
}
