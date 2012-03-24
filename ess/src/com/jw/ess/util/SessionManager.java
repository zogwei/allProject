package com.jw.ess.util;

import javax.servlet.http.HttpSession;

import com.jw.ess.entity.Employee;
import com.jw.ess.entity.Tenant;

public class SessionManager{
	
	public static Tenant getTenantFrom(HttpSession session) 
	{
		return (Tenant) session.getAttribute("tenantSession");
	}
	
	
	public static Employee getEmployeeFrom(HttpSession session)
	{
		return (Employee)session.getAttribute("userSession");
	}
	
}
