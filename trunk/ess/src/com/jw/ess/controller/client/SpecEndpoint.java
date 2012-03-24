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
import com.jw.ess.entity.Spec;
import com.jw.ess.service.ISpecService;
import com.jw.ess.util.ex.EssException;

@Controller
public class SpecEndpoint 
{
	public static Log logger = LogFactory.getLog(SpecEndpoint.class);
	
	
	@Resource(name = "specService")
	private ISpecService specService;

	@Resource(name = "specListConverter")
	private XmlConverter<Map<String,Object>> specListConverter;
	
	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@RequestMapping("/spec/c/list")
	public @ResponseBody
	String getSpecsBy(HttpEntity<String> entity){
		String response;
		try{
			int tenantId =(Integer)specListConverter.fromXml(entity.getBody()).get("tenantId");
			List<Spec> specs = specService.getfindAllSpecs(tenantId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("resultMap", specs);
			response = specListConverter.toXml(map);
		}catch(EssException e){
			logger.error("failed to getSpecsBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}

}
