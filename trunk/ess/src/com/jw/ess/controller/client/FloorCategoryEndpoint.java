package com.jw.ess.controller.client;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jw.ess.converter.XmlConverter;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.util.ex.EssException;

@Controller
public class FloorCategoryEndpoint
{
	public static final Log logger = LogFactory.getLog(FloorCategoryEndpoint.class);
	
	@Resource(name="floorCategoryService")
	private IFloorCategoryService floorCategoryService;
	
	@Resource(name="categoryListConverter")
	private XmlConverter<Map<String,Object>> categoryListConverter;
	

	@RequestMapping("/floorCategory/c/list")
	public @ResponseBody
	String getfloorCategoryList(HttpEntity<String> entity)
	{
		String response ;
		Map<String,Object> map = categoryListConverter.fromXml(entity.getBody());
		Integer tenantId = (Integer) map.get("tenantId");
		try {
			List<FloorCategory> categorys = floorCategoryService.getAllFloorCategorysByTenantId(tenantId);
			map.put("categorys", categorys);
			
		} catch (EssException e) {
			logger.error("failed to get colorCodes", e);
			
		}
		response = categoryListConverter.toXml(map);
		return response;
	}
}

