package com.jw.ess.entity;

import java.util.List;

/**
 * 订单实体类
 * 
 * @author j&w
 * 
 */
public class Order {
	private int id;

	private String orderNo;// 订单编号

	private int isValid;// 是否有效，1-有效，2-无效

	private double amount;// 订单总价

	private double imprest;// 预付款(可能为0)

	private double refund;// 退款金额

	private double received;// 收款金额(收款金额<=订单总价-预付款)

	private int currentState;//订单的当前状态

	private int operateDate;//订单操作时间
	
	private int bookDate;//订单操作时间
	
	private String deliveryDate;//订单操作时间
	
	private String desription ;//描述

	private int tenantId;// 租户ID

	private Customer customer;// 客户

	private Employee operator;// 操作者

	private List<OrderItem> items;// 订单项

	private List<OrderStateTrace> stateTraces;// 订单状态跟踪

	public Customer getCustomer() {
		return customer;
	}

	
	
	public int getBookDate() {
		return bookDate;
	}



	public void setBookDate(int bookDate) {
		this.bookDate = bookDate;
	}



	public String getDesription() {
		return desription;
	}


	public void setDesription(String desription) {
		this.desription = desription;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getIsValid() {
		return isValid;
	}

	public void setIsValid(int isValid) {
		this.isValid = isValid;
	}

	public double getAmount() {
		if (amount > 0) {
			return amount;
		} else {
			double totalAmount = 0;
			if (items != null) {
				for (OrderItem item : items) {
					totalAmount += item.getAmount();
				}
			}
			return totalAmount;
		}
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public List<OrderStateTrace> getStateTraces() {
		return stateTraces;
	}

	public void setStateTraces(List<OrderStateTrace> stateTraces) {
		this.stateTraces = stateTraces;
	}

	public double getImprest() {
		return imprest;
	}

	public void setImprest(double imprest) {
		this.imprest = imprest;
	}

	public double getReceived() {
		return received;
	}

	public void setReceived(double received) {
		this.received = received;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public Employee getOperator() {
		return operator;
	}

	public void setOperator(Employee operator) {
		this.operator = operator;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(int operateDate) {
		this.operateDate = operateDate;
	}

	public String toString() {
		return "[id=" + id + ", orderNo=" + orderNo + ", amount=" + amount + ", imprest=" + imprest + ", refund="
				+ refund + ", reveived=" + received + ", customer=" + customer + ", operator=" + operator + ", items="
				+ items + ", stateStraces=" + stateTraces + ", tenantId=" + tenantId + ", isValid=" + isValid + "]";
	}

	public  String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate( String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
}
