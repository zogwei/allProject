package com.jw.ess.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.SalesStatsConstant;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.ParameterMapKeys;
@Component("dailySalesStatsListConverter")
public class DailySalesStatsListConverter extends DefaultXmlConverter<Map<String,Object>> {
	private static final Log logger = LogFactory.getLog(DailySalesStatsListConverter.class);

	@Override
	public Map<String,Object> fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			int employeeId = Integer.parseInt(conditionEle.elementTextTrim(SalesStatsConstant.EMPLOYEE_ID));
			
			int beginDate = Integer.parseInt(conditionEle.elementTextTrim(SalesStatsConstant.START_DATE));
			
			int endDate = Integer.parseInt(conditionEle.elementTextTrim(SalesStatsConstant.END_DATE));
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			map.put(ParameterMapKeys.BEGIN_DATE, beginDate);
			
			map.put(ParameterMapKeys.END_DATE, endDate);
			
			map.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
			
			return map;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String,Object> map) {
		
		List<SalesStats> salesStats=(List<SalesStats>) map.get("resultMap");
		
		if(salesStats==null||salesStats.size()==0){
			
			return super.toContent(map);
		}
		
		StringBuilder content = new StringBuilder();
		
		content.append(Commons.RESULT_START);
		
		content.append(SalesStatsConstant.DAILY_SALES_STATS_LIST_START);
		
		for(SalesStats s:salesStats){
			
		content.append(SalesStatsConstant.DAILY_SALES_STATS_START);
		
		content.append(SalesStatsConstant.SALES_AMOUNT_START);
		
		content.append(s.getSalesAmount());
		
		content.append(SalesStatsConstant.SALES_AMOUNT_END);
		
		content.append(SalesStatsConstant.SALES_DATE_START);
		
		content.append(s.getSalesDate());
		
		content.append(SalesStatsConstant.SALES_DATE_END);
		
		content.append(SalesStatsConstant.DAILY_SALES_STATS_END);
		}
		content.append(SalesStatsConstant.DAILY_SALES_STATS_LIST_END);
		
		content.append(Commons.RESULT_END);
		
		return content.toString();
	}

}
