package com.jw.ess.util;

public class CommonConstant
{

	// 员工性别
	public static final int EMPLOYEE_SEX_MAN = 1;// 男

	public static final int EMPLOYEE_SEX_WOMAN = 2;// 女

	// 员工是否在职
	public static final int EMPLOYEE_MISSION = 1;// 在职

	public static final int EMPLOYEE_DIMISSION = 2;// 离职

	// 员工角色
	public static final int EMPLOYEE_ROLE_ADMIN = 1;// 管理员

	public static final int EMPLOYEE_ROLE_SALESMANAGER = 2;// 销售经理

	public static final int EMPLOYEE_ROLE_SALESMAN = 3;// 销售人员

	// 有效性
	public static final int STATE_VALID = 1;// 有效

	public static final int STATE_INVALID = 2;// 无效
	
	//订单状态
	public static final int ORDER_STATE_BOOK = 1;//预定
	
	public static final int ORDER_STATE_PAY = 2; //付款
	
	public static final int ORDER_STATE_CANCEL = 3;//退货
	
	//定义每页显示数初始值
	public static final int PAGE_SIZE = 15;
}
