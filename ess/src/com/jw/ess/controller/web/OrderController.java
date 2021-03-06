package com.jw.ess.controller.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.jw.ess.util.ExcelUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

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
		if(tenantId==1)
		{
			employee.setTenantId(-1);
		}
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
	
	@RequestMapping("/order/exportExcel")
	public void exportExcel(@Param(value = "tenantId") int tenantId,
			@Param(value="currentState") String currentState,
			@Param(value="startTime")String startTime,
			@Param(value="endTime")String endTime,
			@Param(value="minAmount")String minAmount,
			@Param(value="maxAmount")String maxAmount,
			@Param(value = "operatorId") int operatorId,
			@Param(value = "exportPage") int exportPage,
			@Param(value = "exportSize") int exportSize, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		if(tenantId==1)
		{
			paramMap.put("tenantId", -1);
		}
		paramMap.put("operatorId", operatorId);
		paramMap.put("exportPage", exportPage);
		paramMap.put("exportSize", exportSize);
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
		if(tenantId==1)
		{
			employee.setTenantId(-1);
		}
		employee.setCategory(CommonConstant.EMPLOYEE_ROLE_SALESMAN);
		try {
			PageSupport<Order> orders = orderService.getOrdersByForExport(paramMap);
			exportOrderList(orders,request,response);
		} catch (EssException e) {
			e.printStackTrace();
		}
	}
	
	private void exportOrderList(PageSupport<Order> orders, HttpServletRequest request, HttpServletResponse response)
	{
		try{
			List datalist = coverToExportList(orders);
			List headList = getOrderExportHead();
			 HSSFWorkbook workBook=ExcelUtil.createExcelBook(datalist,headList);
	         OutputStream out=null;
	         response.reset();
	         response.setContentType("application/vnd.ms-excel");
	         response.setHeader ( "Content-Disposition" ,"attachment;filename=订单清单.xls") ;
	         out = response.getOutputStream();
	         workBook.write(out);
	         out.flush();
	         out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private List<List> coverToExportList(PageSupport<Order> orders)
	{
		List<List> returnList = new ArrayList<List>();
		List<Order> list = orders.getResult();
		String status = "";
		for(Order order : list)
		{
			List orderItemList = new ArrayList();
			orderItemList.add(order.getOrderNo()+""); //
			orderItemList.add(order.getCustomer().getName()+"");
			orderItemList.add(order.getOperator().getName()+"");
			orderItemList.add(DateUtil.transformString(order.getBookDate(), DateUtil.INPUT_DATE_FORMAT));
			status = order.getCurrentState()+"";
			if(status.equals("1"))
			{
				orderItemList.add("已下单");
			}
			else if(status.equals("2")){
				orderItemList.add("已确认");
			}
			else if(status.equals("3")){
				orderItemList.add("已退货");
			}
			else if(status.equals("4")){
				orderItemList.add("修改待确认");
			}
			
			orderItemList.add(order.getImprest()+"");
			orderItemList.add(order.getAmount()+"");
			if(status.equals("2")){
				orderItemList.add(DateUtil.transformString(order.getStateTraces().get(0).getOperateDate(), DateUtil.INPUT_DATE_FORMAT));
			}
			else{
				orderItemList.add("");
			}
			
			
			returnList.add(orderItemList);
		}
		return returnList;
	}
	
	
	private List<String> getOrderExportHead()
	{
		List<String> returnList = new ArrayList<String>();
		returnList.add("订单编号");
		returnList.add("客户名");
		returnList.add("经办人");
		returnList.add("下单日期");
		returnList.add("订单状态");
		returnList.add("订金");
		returnList.add("订单总价");
		returnList.add("完成时间");
		
		return returnList;
	}

	@RequestMapping("/order/cancel")
	public String orderCancel(@Param(value = "cancels") Integer[] cancels,
			@Param(value = "floorId") Integer[] floorIds,
			@Param(value = "area") Double[] areas,
			@Param(value = "sellPrice") Double[] sellPrice,
			@Param(value = "amount") Double[] amounts, Order order,
			@Param(value = "desc") String desc,
			@Param(value = "operatorId") int operatorId,HttpServletRequest request) {
		try {
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
			
			//获得需要退货的item ,剩下的item
			List<OrderItem> items = new ArrayList<OrderItem>();
			List<OrderItem> remainItems = new ArrayList();
			
			for (int j = floorIds.length, i = 0; i < j; i++) {
				
				//不是劝退，判读是不是有部分退，将退的部分加入退货item，不退的加入remainItems
				String cancelFlag = request.getParameter(floorIds[i]+"_cancelFlag");
				if("yes".equals(cancelFlag))
				{
					//判断是否劝退，如果劝退，加入退货item
					OrderItem item = new OrderItem();
					Floor floor = new Floor();
					floor.setId(floorIds[i]);
					item.setFloor(floor);
					item.setArea(areas[i]);
					item.setAmount(amounts[i]);
					items.add(item);
				}
				else
				{
					String cancelblock = request.getParameter(floorIds[i]+"_cancelblock");
					if(!"0".equals(cancelblock))
					{
						//部分退货，将退的部分加入退货item，不退的加入remainItems
						OrderItem item = new OrderItem();
						Floor floor = new Floor();
						floor.setId(floorIds[i]);
						double cancelPrice = Double.parseDouble(request.getParameter(floorIds[i]+"_cancelPrice"));
						double cancelArea = Double.parseDouble(request.getParameter(floorIds[i]+"_cancelArea"));
						
						//退货item
						item.setFloor(floor);
						item.setArea(cancelArea);
						item.setAmount(cancelPrice);
						items.add(item);
						//remainItems
						OrderItem remainitem = new OrderItem();
						remainitem.setFloor(floor);
						remainitem.setArea(areas[i]-cancelArea);
						remainitem.setAmount(amounts[i]-cancelPrice);
						remainitem.setSellPrice(sellPrice[i]);
						remainitem.setOrderId(order.getId());
						remainItems.add(remainitem);
					}
					else
					{
						//不退
						OrderItem remainitem = new OrderItem();
						Floor floor = new Floor();
						floor.setId(floorIds[i]);
						remainitem.setFloor(floor);
						remainitem.setArea(areas[i]);
						remainitem.setAmount(amounts[i]);
						remainitem.setSellPrice(sellPrice[i]);
						remainitem.setOrderId(order.getId());
						remainItems.add(remainitem);
					}
				}
			}
			if(items.size()==0){
				return "forward:/order/cancelDetail?orderId=" + order.getId();
			}
			order.setItems(items);
			
			System.out.println(items);
			System.out.println(remainItems);
			orderService.cancelOrder(order,remainItems);
			
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
		if(tenantId==1)
		{
			employee.setTenantId(-1);
		}
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
	
	@RequestMapping("/order/orderUpdateConfirmPage")
	public String orderUpdateConfirmPage(int orderId,int newOrderId, ModelMap map) {
		try {
			map.addAttribute("order", orderService.getOrderById(orderId));
			map.addAttribute("createDate", DateUtil.transformString(
					orderService.getOrderById(orderId).getOperateDate(),
					DateUtil.INPUT_DATE_FORMAT));
			
			Map parameter = new HashMap();
			parameter.put("oldOrderId", orderId+"");
			parameter.put("status", "1");
			Map newOrderMap = orderService.selectOrderUpdate(parameter);
			newOrderId = ((Integer)newOrderMap.get("newOrderId")).intValue();
			
			map.addAttribute("orderNew", orderService.getOrderById(newOrderId));
			map.addAttribute("createDateNew", DateUtil.transformString(
					orderService.getOrderById(newOrderId).getOperateDate(),
					DateUtil.INPUT_DATE_FORMAT));

		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderUpdateconfirm";
	}
	
	@RequestMapping("/order/orderUpdateConfirm")
	public String orderUpdateConfirm(int orderId,int newOrderId,int result, ModelMap map) {
		try {
			Map param = new HashMap();
			param.put("oldOrderId", orderId);
			param.put("newOrderId", newOrderId);
			if(result == 1)
			{
				orderService.updateOrderAuth(param, true);
			}
			else
			{
				orderService.updateOrderAuth(param, false);
			}
			
			

		} catch (EssException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "order/orderMain";
	}
	
	

}
