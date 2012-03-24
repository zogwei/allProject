package com.jw.ess.util;



public class UriGetter
{
	private static final String URI_PREFIX = "http://localhost:8080/ess";

	public static final String EMPLOYEE_LOGIN = URI_PREFIX
			+ "/employee/c/login";
	
	public static final String EMPLOYEE_WEB_LIST = URI_PREFIX
			+ "/employee/list";
	
	public static final String CUSTOMER_ADD = URI_PREFIX
			+ "/customer/c/add";
	
	public static final String CUSTOMER_MODIFY = URI_PREFIX
			+ "/customer/c/modify";
	
	public static final String CUSTOMER_ONE = URI_PREFIX
			+ "/customer/c/one";
	
	public static final String CUSTOMER_LIST = URI_PREFIX
			+ "/customer/c/list";
	
	public static final String ORDER_ADD = URI_PREFIX
			+ "/order/c/add";
	
	public static final String ORDER_ONE = URI_PREFIX
	        + "/order/c/one";

	public static final String ORDER_LIST = URI_PREFIX
			+ "/order/c/list";

	public static final String FLOOR_LIST = URI_PREFIX
			+ "/floor/c/list";
	
	public static final String SPEC_LIST = URI_PREFIX
			+ "/spec/c/list";
	
	public static final String VEIN_LIST = URI_PREFIX
			+ "/vein/c/list";
	
	public static final String COLOR_CODE_LIST = URI_PREFIX 
			+"/colorCode/c/list";
	
	public static final String CATEGORY_LIST = URI_PREFIX
			+"/floorCategory/c/list";
	
	public static final String ORDER_CONFIRM = URI_PREFIX
			+ "/order/c/confirm";

	public static final String ORDER_CANCEL = URI_PREFIX
			+ "/order/c/cancel";

	public static final String  GET_SALES_STATS = URI_PREFIX 
	        + "/sale/c/salesStats" ;
	
	public static final String  GET_STORAGE = URI_PREFIX 
			+ "/storage/c/one" ;
	
	public static final String  GET_STORAGES = URI_PREFIX 
			+ "/storage/c/list" ;

	public static final String DAILY_LIST = URI_PREFIX 
			+ "/salesStats/c/dailyList" ;
	
	public static final String MONTHLY_LIST = URI_PREFIX
	        + "/salesStats/c/monthlyList";
	
	public static final String FLOOR_META = URI_PREFIX
    		+ "/floorMeta/c/list";
}
