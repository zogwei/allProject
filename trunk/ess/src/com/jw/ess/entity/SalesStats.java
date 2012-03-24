package com.jw.ess.entity;

public class SalesStats {
	
	private int tenantId;
	
	private int employeeId;
	
	private double salesAmount; 

	private int salesDate;

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public double getSalesAmount() {
		return salesAmount;
	}

	@Override
	public String toString() {
		return "SalesStats [employeeId=" + employeeId + ", salesAmount="
				+ salesAmount + ", salesDate=" + salesDate + ", tenantId="
				+ tenantId + "]";
	}

	public void setSalesAmount(double salesAmount) {
		this.salesAmount = salesAmount;
	}

	public int getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(int salesDate) {
		this.salesDate = salesDate;
	}
}
