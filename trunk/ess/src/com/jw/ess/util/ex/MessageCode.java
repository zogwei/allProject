package com.jw.ess.util.ex;

/**
 * 消息码
 * 
 * @author j&w
 * 
 */
public class MessageCode {
	//租户名称已经存在
	public static final int TENANT_NAME_ALERADY_EXISTS = 10000;

	// ////////////////////////////////Floor//////////////////////////////////////
	public static final int FLOOR_MODULE = 11000;

	// ////////////////////////////////Cutomer//////////////////////////////////////

	// 客户名称已经存在
	public static final int CUSTOMER_NAME_ALREADY_EXISTS = 12000;

	// 添加客户成功
	public static final int ADD_CUSTOMER_SUCCESS = 12001;

	// ////////////////////////////////Employee//////////////////////////////////////

	// 员工账号已经存在
	public static final int EMPLOYEE_ACCOUNT_ALREADY_EXISTS = 13000;

	// 员工姓名已经存在
	public static final int EMPLOYEE_NAME_ALREADY_EXISTS = 13001;

	// 用户名或密码错误
	public static final int EMPLOYEE_ACCOUNT_OR_PASSWORD_ERROR = 13002;

	// 添加员工成功
	public static final int ADD_EMPLOYEE_SUCCESS = 13003;

	// ////////////////////////////////Supplier//////////////////////////////////////

	public static final int SUPPLIER_MODULE = 14000;

	//供应商已经存在
	public static final int SUPPLIER_NAME_ALREADY_EXISTS = 14001;

	// ////////////////////////////////Spec//////////////////////////////////////
	// 规格名称已经存在
	public static final int SPEC_NAME_ALREADY_EXISTS = 15000;

	// 添加规格成功
	public static final int ADD_SPEC_SUCCESS = 15001;

	// ////////////////////////////////ColorCode//////////////////////////////////////
	// 色号名称已经存在
	public static final int COLOR_CODE_ALREADY_EXISTS = 17000;

	// 添加色号成功
	public static final int ADD_COLOR_CODE_SUCCESS = 17001;

	// ////////////////////////////////Vein//////////////////////////////////////
	// 纹理名称已经存在
	public static final int VEIN_NAME_ALREADY_EXISTS = 18000;

	// 添加纹理成功
	public static final int ADD_VEIN_SUCCESS = 18001;

	public static final int FLOOR_CATEGORY_MODULE = 19000;

	// ////////////////////////////////InStorage//////////////////////////////////////

	public static final int IN_STORGE_MODULE = 20000;

	//地板编号已经存在
	public static final int IN_STORAGE_FLOORID_ALREADY_EXISTS = 20001;

	public static final int STORAGE_MODULE = 21000;

	public static final int STORAGE_FLOORID_ALREADY_EXISTS = 21001;

	public static final int ORDER_MODULE = 22000;
	//订单已确认
	public static final int ORDER_HAD_CONFIRM = 22001;
	
	//订单已取消
	public static final int OREDER_HAD_CANCEL = 22002;
	
	public static final int ORDER_ITEM_MODULE = 23000;

	public static final int ORDER_ITEM_ADD_ERROR = 23001;

	public static final int ORDER_ITEM_FIND_ERROR = 23002;

	public static final int ORDER_STATE_TRACE_MODULE = 24000;

	
	
	// ////////////////////////////////Floor//////////////////////////////////////
	// 地板名称已经存在
	public static final int FLOOR_NAME_ALREADY_EXISTS = 30001;
	// 地板类别名称已经存在
	public static final int FLOOR_CATEGORY_NAME_ALREADY_EXISTS = 30002;
	// 地板租户不存在
	public static final int FLOOR_TENANT_NOT_EXISTS = 30003;
	
	public static final int FLOOR_EXCEL_FILE_ERROR = 30004;
	
	// ////////////////////////////////PicPath//////////////////////////////////////
	public static final int PIC_PATH_MODULE = 31000;
	// 数据库中地板图片路径已经存在
	public static final int PIC_PATH_ALREADY_EXISTS = 31001;
	// 上传地板图片的图片文件发生异常(文件不存在或上传时发生会导致IO异常的问题)
	public static final int PIC_PATH_FILE_ERROR = 31002;
	// 地板图片的路径有错
	public static final int PIC_PATH_ERROR = 31003;
	// 地板图片的格式有错
	public static final int PIC_PATH_FILE_SUFFIX_ERROR = 31004;
	// 地板图片的压缩包解压缩出错
	public static final int PIC_PATH_FILE_PACKAGE_ERROR = 31004;
	
	// ////////////////////////////////DailySalesStats//////////////////////////////////////
	//此员工的天销售记录已经存在
	public static final int DAILY_SALES_STATS_SALESSTATS_ALREADY_EXISTS =40001;
	//此员工的天销售记录不存在
	public static final int DAILY_SALES_STATS_SALESSTATS_NOT_EXISTS = 40002;
	//退款金额大于销售统计金额
	public static final int DAILY_SALES_STATS_SALESSTATS_REFUND_ERROR = 40003;
	// ///////////////////////////////MonthlySalesStats////////////////////////////////////////
	//此员工的 月销售记录已经存在
	public static final int MONTHLY_SALES_STATS_SALESSTATS_ALREADY_EXISTS =50001;
	//此员工的月销售记录不存在
	public static final int MONTHLY_SALES_STATS_SALESSTATS_NOT_EXISTS = 50002;
	//退款金额大于销售统计金额
	public static final int MONTHLY_SALES_STATS_SALESSTATS_REFUND_ERROR = 50003;
	
	// ////////////////////////////////Other//////////////////////////////////////

	public static final int SYSTEM_ERROR = 90000;

	public static final int DATABASE_ERROR = 90001;

	public static final int ARGUMENT_ERROR = 90002;

	public static final int UNKNOWN_ERROR = 90003;

	public static final int OPERATION_SUCCESS = 90004;








}
