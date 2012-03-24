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
import com.jw.ess.service.IMonthlySalesStatsService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;

@Controller
public class MonthlySalesStatsEndpoint {
	private static final Log logger = LogFactory.getLog(MonthlySalesStatsEndpoint.class);
	
	@Resource(name = "monthlySalesStatsService")
	private  IMonthlySalesStatsService monthlySalesStatsService;
	
	@Resource(name = "monthlySalesStatsListConverter")
	private XmlConverter<Map<String,Object>>  monthlySalesStatsListConverter;
	
	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@RequestMapping("/salesStats/c/monthlyList")
	public @ResponseBody
	String getMonthlySalesStatss(HttpEntity<String> entity) {
		String response;
		try {
			Map<String,Object> map = monthlySalesStatsListConverter.fromXml(entity.getBody());
			
			int beginDate=(Integer)map.get(ParameterMapKeys.BEGIN_DATE);
			
			int endDate=(Integer) map.get(ParameterMapKeys.END_DATE);
			
			int employeeId=(Integer) map.get(ParameterMapKeys.EMPLOYEE_ID);
			
			Map<String,Object> resultMap=new HashMap<String,Object>();
			
			resultMap.put("resultMap",monthlySalesStatsService
					.MonthlyStats(employeeId, beginDate, endDate));
			
			response = monthlySalesStatsListConverter.toXml(resultMap);
			
		} catch (EssException e) {
			logger.error("failed to getMonthlySalesStatss", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
}
