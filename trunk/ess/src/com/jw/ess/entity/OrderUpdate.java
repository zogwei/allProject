package com.jw.ess.entity;

public class OrderUpdate {

	private int id ;
	private int newOrderId;
	private int oldOrderId;
	private int operatorId;
	private int operateDate;
	private String  status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public int getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(int operateDate) {
		this.operateDate = operateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNewOrderId() {
		return newOrderId;
	}
	public void setNewOrderId(int newOrderId) {
		this.newOrderId = newOrderId;
	}
	public int getOldOrderId() {
		return oldOrderId;
	}
	public void setOldOrderId(int oldOrderId) {
		this.oldOrderId = oldOrderId;
	}
	
	
}
