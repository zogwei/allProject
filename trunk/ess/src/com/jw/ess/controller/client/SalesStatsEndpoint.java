package com.jw.ess.controller.client;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jw.ess.converter.XmlConverter;
import com.jw.ess.service.IDailySalesStatsService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ParameterMapKeys;

@Controller
public class SalesStatsEndpoint {

	private static final Log logger = LogFactory.getLog(SalesStatsEndpoint.class);
	
	@Resource(name = "dailySalesStatsService")
	private  IDailySalesStatsService dailySalesStatsService;
	
	@Resource(name = "dailySalesStatsListConverter")
	private XmlConverter<Map<String,Object>> dailySalesStatsListConverter;

	private XmlConverter<EssException> exceptionConverter;

	@RequestMapping("/salesStats/c/dailyList")
	public @ResponseBody
	String getDailySalesStatss(HttpEntity<String> entity) {
		String response;
		try {
			Map<String,Object> map = dailySalesStatsListConverter.fromXml(entity.getBody());
			
			int beginDate=(Integer)map.get(ParameterMapKeys.BEGIN_DATE);
			
			int endDate=(Integer) map.get(ParameterMapKeys.END_DATE);
			
			int employeeId=(Integer) map.get(ParameterMapKeys.EMPLOYEE_ID);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			
			resultMap.put("resultMap",dailySalesStatsService
					.getStatss(employeeId, beginDate, endDate));
			
			response = dailySalesStatsListConverter.toXml(resultMap);
			
		} catch (EssException e) {
			logger.error("failed to getDailySalesStatss", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}


}
