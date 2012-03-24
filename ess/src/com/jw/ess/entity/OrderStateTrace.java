package com.jw.ess.entity;

/**
 * 订单状态跟踪实体类
 * 
 * @author j&w
 * 
 */
public class OrderStateTrace
{
	private int stateId;// 状态id

	private int orderId;// 订单id

	private String desc;// 备注

	private int operateDate;// 操作时间

	public int getStateId()
	{
		return stateId;
	}

	public void setStateId(int stateId)
	{
		this.stateId = stateId;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getOperateDate()
	{
		return operateDate;
	}

	public void setOperateDate(int operateDate)
	{
		this.operateDate = operateDate;
	}

	public String toString()
	{
		return "[stateId=" + stateId + ", orderId=" + orderId
				+ ", operateDate=" + operateDate + ", desc=" + desc + "]";
	}
}
