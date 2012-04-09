package com.jw.ess.entity;

/**
 * 订单项实体类
 * 
 * @author j&w
 * 
 */
public class OrderItem
{
	private int id;

	private Floor floor;// 地板

	private int orderId;// 订单id

	private double sellPrice;// 单价

	private double area;// 面积

	private double amount;// 订单项总价
	
	private double onearea;// 一块面积
	
	

	public double getOnearea() {
		return onearea;
	}

	public void setOnearea(double onearea) {
		this.onearea = onearea;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Floor getFloor()
	{
		return floor;
	}

	public void setFloor(Floor floor)
	{
		this.floor = floor;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public double getSellPrice()
	{
		return sellPrice;
	}

	public void setSellPrice(double sellPrice)
	{
		this.sellPrice = sellPrice;
	}

	public double getArea()
	{
		return area;
	}

	public void setArea(double area)
	{
		this.area = area;
	}

	public double getAmount()
	{
		if (amount > 0)
		{
			return amount;
		}
		else
		{
			return sellPrice * area;
		}
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public String toString()
	{
		return "[id=" + id + ", floor=" + floor + ", orderId=" + orderId
				+ ", area=" + area + ", amount=" + amount + ", sellPrice="
				+ sellPrice + "]";
	}

}
