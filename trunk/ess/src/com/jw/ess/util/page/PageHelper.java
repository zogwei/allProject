package com.jw.ess.util.page;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jw.ess.entity.Employee;
import com.jw.ess.util.ParameterMapKeys;

public class PageHelper {
	/**
	 * @author  issuser
	 * @see		整合Page和tenantId成为sqlMap对象,作为Dao层的调用参数
	 * @param	tenantId   int
	 * @param	beginIndex int
	 * @param   pageSize   
	 * @return  Map<String, Object>
	 * */
	public static Map<String, Object> getMapByTenantIdAndPage(int tenantId,int beginIndex,int pageSize){
		
		Map<String, Object> map=new HashMap<String,Object>();
		
		map.put(ParameterMapKeys.TENANT_ID,tenantId);
		
		map.put(ParameterMapKeys.BEGIN_INDEX,beginIndex);
		
		map.put(ParameterMapKeys.PAGE_SIZE,pageSize);
		
		return map;
	}
	
	/**
	 * @author  issuser
	 * @param	tenantId   int
	 * @param	categoryid int
	 * @param   page  
	 * @return  Map<String, Object>
	 * */
	public static Map<String,Object> getMapByFloorIdAndPage(int tenantId,int categoryId,int pageSize,int beginIndex)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(ParameterMapKeys.TENANT_ID, tenantId);
		map.put(ParameterMapKeys.CATEGORY_ID, categoryId);
		map.put(ParameterMapKeys.BEGIN_INDEX, beginIndex);
		map.put(ParameterMapKeys.PAGE_SIZE, pageSize);
		
		return map;
		
	}
	
		/**
	 * @author  issuser
	 * @see		整合Page以及Employee的属性成为sqlMap对象,作为Dao层的调用参数
	 * @param	tenantId   int
	 * @param	beginIndex int
	 * @param   pageSize   
	 * @return  Map<String, Object>
	 * */
	public static Map<String, Object> getEmployeeSqlMape(Employee employee,int beginIndex, int pageSize) {
		
		Map<String, Object> map=
			getMapByTenantIdAndPage(employee.getTenantId(),beginIndex,pageSize);
		
		if(StringUtils.isNotBlank(employee.getAccount())){
			map.put(ParameterMapKeys.EMPLOYEE_ACCOUNT,employee.getAccount());
		}
		if(StringUtils.isNotBlank(employee.getName())){
			map.put(ParameterMapKeys.EMPLOYEE_NAME,employee.getName());
		}
			
		if(StringUtils.isNotBlank(employee.getCardNo())){
			map.put(ParameterMapKeys.EMPLOYEE_CARD_NO,employee.getCardNo());
		}
	
		map.put(ParameterMapKeys.EMPLOYEE_ISVALID,employee.getIsValid());
		
		map.put(ParameterMapKeys.EMPLOYEE_SEX,employee.getSex());
		
		map.put(ParameterMapKeys.EMPLOYEE_STATE,employee.getState());
		
		map.put(ParameterMapKeys.EMPLOYEE_CATEGORY,employee.getCategory());
		
		return map;
	}
}
