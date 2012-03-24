package com.jw.ess.controller.client;

import java.util.HashMap;
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
import com.jw.ess.entity.Floor;
import com.jw.ess.service.IFloorService;
import com.jw.ess.util.ex.EssException;

@Controller
public class FloorEndpoint {
	
	private static final Log logger = LogFactory.getLog(FloorEndpoint.class);
	
	@Resource(name = "floorService")
	private IFloorService floorService;

	@Resource(name = "floorListConverter")
	private XmlConverter<Map<String,Object>> floorListConverter;
	
	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@RequestMapping("/floor/c/list")
	public @ResponseBody
	String getFloorsBy(HttpEntity<String> entity){
		String response;
		try{
			int tenantId =(Integer)floorListConverter.fromXml(entity.getBody()).get("tenantId");
			List<Floor> floors = floorService.getFloorsByTenantId(tenantId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("resultMap", floors);
			response = floorListConverter.toXml(map);
		}catch(EssException e){
			logger.error("failed to getFloorsBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
}
