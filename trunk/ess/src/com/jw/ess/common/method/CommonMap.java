package com.jw.ess.common.method;

import java.util.HashMap;
import java.util.Map;

import com.jw.ess.util.ParameterMapKeys;

public class CommonMap {
	
	/**
	 * @author  issuser
	 * @see		保存tenantId,beginIndex,beginIndex的Map方法
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
	
}
