package com.jw.ess.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Order;
import com.jw.ess.entity.OrderItem;
import com.jw.ess.entity.OrderStateTrace;
import com.jw.ess.service.IEmployeeService;
import com.jw.ess.service.IOrderService;
import com.jw.ess.util.CommonConstant;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;

@Controller
public class OrderController {

	private static final Log logger = LogFactory.getLog(OrderController.class);

	@Resource(name = "orderService")
	private IOrderService orderService;

	@Resource(name = "employeeService")
	private IEmployeeService employeeService;

	@RequestMapping("/order/list")
	public String orderList(@Param(value = "tenantId") int tenantId,
			@Param(value="currentState") String currentState,
			@Param(value="startTime")String startTime,
			@Param(value="endTime")String endTime,
			@Param(value="minAmount")String minAmount,
			@Param(value="maxAmount")String maxAmount,
			@Param(value = "operatorId") int operatorId,
			@Param(value = "currentPage") int currentPage,
			@Param(value = "pageSize") int pageSize, ModelMap map) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		if(tenantId==1)
		{
			paramMap.put("tenantId", -1);
		}
		paramMap.put("operatorId", operatorId);
		paramMap.put("currentPage", currentPage);
		paramMap.put("pageSize", pageSize);
		if(StringUtils.isBlank(currentState)){
			currentState = "0";
		}else{
			paramMap.put("currentState", Integer.parseInt(currentState));
		}
		if(StringUtils.isBlank(endTime)){
			paramMap.put("endTime", 0);
			endTime = "0";
		}else{
			if(!endTime.equals("0")){
			paramMap.put("endTime", DateUtil.transformTimeSecs(endTime));
			}
			else{
				paramMap.put("endTime", 0);
			}
		}
		if(StringUtils.isBlank(startTime))	{
			paramMap.put("startTime", 0);
			startTime = "0";
		}else
		{
			if(!startTime.equals("0"))
			{
				paramMap.put("startTime", DateUtil.transformTimeSecs(startTime));
			}else{
				paramMap.put("startTime", 0);
			}
		}
		if(StringUtils.isBlank(minAmount))	{
			paramMap.put("minAmount", -1);
			minAmount = "0";
		}else{
			paramMap.put("minAmount", Double.parseDouble(minAmount));
		}
		if(StringUtils.isBlank(maxAmount))	{
			paramMap.put("maxAmount", -1);
			maxAmount = "0";
		}else{
			paramMap.put("maxAmount", Double.parseDouble(maxAmount));
		}
		Employee employee = new Employee();
		employee.setTenantId(tenantId);
		employee.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMAN);
		try {
			map.addAttribute("orders", orderService.getOrdersBy(paramMap));
			map.addAttribute("employees", employeeService.getEmployeesBy(
					employee, new Page()));
			map.addAttribute("operatorId", operatorId);
			map.addAttribute("currentState", currentState);
			map.addAttribute("endTime", endTime);
			map.addAttribute("startTime", startTime);
			map.addAttribute("minAmount", minAmount);
			map.addAttribute("maxAmount", maxAmount);
		} catch (EssException e) {

			e.printStackTrace();
		}
		return "order/orderMain";
	}

	@RequestMapping("/order/cancel")
	public String orderCancel(@Param(value = "cancels") Integer[] cancels,
			@Param(value = "floorId") Integer[] floorIds,
			@Param(value = "area") Double[] areas,
			@Param(value = "amount") Double[] amounts, Order order,
			@Param(value = "desc") String desc,
			@Param(value = "operatorId") int operatorId) {
		try {
			if(cancels == null||cancels.length==0){
				return "forward:/order/cancelDetail?orderId=" + order.getId();
			}
			OrderStateTrace state = new OrderStateTrace();
			state.setStateId(CommonConstant.ORDER_STATE_CANCEL);
			state.setOperateDate(DateUtil.currentTimeSecs());
			state.setOrderId(order.getId());
			state.setDesc(desc);
			List<OrderStateTrace> states = new ArrayList<OrderStateTrace>();
			states.add(state);
			order.setStateTraces(states);
			Employee employee = new Employee();
			employee.setId(operatorId);
			order.setOperator(employee);
			List<OrderItem> items = new ArrayList<OrderItem>();
			for (int j = floorIds.length, i = 0; i < j; i++) {
				OrderItem item = new OrderItem();
				Floor floor = new Floor();
				floor.setId(floorIds[i]);
				item.setFloor(floor);
				item.setArea(areas[i]);
				item.setAmount(amounts[i]);
				items.add(item);
			}
			
				for (int i = items.size() - 1; i > 0; i--) {

					for (int j = 0, k = cancels.length; j < k; j++) {

						if (cancels[j] != items.get(i).getId()) {
							items.remove(i);
						}
					}
				}
				order.setItems(items);
				System.out.println(items);
				orderService.cancelOrder(order);
		
			
			
		} catch (EssException e) {
			logger.error("fail to cancel", e);
		}
		return "forward:/order/editDetail?orderId=" + order.getId();
	}

	@RequestMapping("/order/confirm")
	public String orderConfirm(int orderId, @Param(value="received") int received) {
		try {
	
			orderService.orderConfirm(orderId,received);
		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "forward:/order/editDetail";
	}

	@RequestMapping("/order/employeeList")
	public String operatorList(int tenantId, ModelMap map) {
		Employee employee = new Employee();
		employee.setTenantId(tenantId);
		employee.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMAN);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		if(tenantId==1)
		{
			paramMap.put("tenantId", -1);
		}
		paramMap.put("operatorId", 0);
		paramMap.put("currentPage", 1);
		paramMap.put("pageSize", 15);
		try {
			map.addAttribute("operatorId",0);
			map.addAttribute("orders", orderService.getOrdersBy(paramMap));
			map.addAttribute("employees", employeeService.getEmployeesBy(
					employee, new Page()));
		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderMain";
	}

	@RequestMapping("/order/editDetail")
	public String orderDetail(int orderId, ModelMap map) {
		try {
			map.addAttribute("order", orderService.getOrderById(orderId));
			map.addAttribute("createDate", DateUtil.transformString(
					orderService.getOrderById(orderId).getOperateDate(),
					DateUtil.INPUT_DATE_FORMAT));

		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderDetail";
	}

	@RequestMapping("/order/one")
	public String getOrder(int orderId, ModelMap map) {
		try {
			map.addAttribute("order", orderService.getOrderById(orderId));
			map.addAttribute("createDate", DateUtil.transformString(
					orderService.getOrderById(orderId).getOperateDate(),
					DateUtil.INPUT_DATE_FORMAT));

		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderConfirm";
	}

	@RequestMapping("/order/cancelDetail")
	public String cancelOrder(int orderId, ModelMap map) {
		try {
			map.addAttribute("order", orderService.getOrderById(orderId));
			map.addAttribute("createDate", DateUtil.transformString(
					orderService.getOrderById(orderId).getOperateDate(),
					DateUtil.INPUT_DATE_FORMAT));

		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderCancel";
	}

}
