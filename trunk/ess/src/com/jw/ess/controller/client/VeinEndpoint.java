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
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.ex.EssException;

@Controller
public class VeinEndpoint {

public static Log logger = LogFactory.getLog(VeinEndpoint.class);
	
	
	@Resource(name = "veinService")
	private IVeinService veinService;

	@Resource(name = "veinListConverter")
	private XmlConverter<Map<String,Object>> veinListConverter;
	
	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@RequestMapping("/vein/c/list")
	public @ResponseBody
	String getVeinsBy(HttpEntity<String> entity){
		String response;
		try{
			int tenantId =(Integer)veinListConverter.fromXml(entity.getBody()).get("tenantId");
			List<Vein> veins = veinService.getAllVeins(tenantId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("resultMap", veins);
			response = veinListConverter.toXml(map);
		}catch(EssException e){
			logger.error("failed to getVeinsBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
}
