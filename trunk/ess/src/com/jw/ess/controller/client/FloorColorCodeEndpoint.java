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
import com.jw.ess.entity.ColorCode;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.util.ex.EssException;

@Controller
public class FloorColorCodeEndpoint 
{
	public static final Log logger = LogFactory.getLog(FloorColorCodeEndpoint.class);

	@Resource(name="colorCodeService")
	private IColorCodeService colorCodeService;
	
	@Resource(name="colorCodeListConverter")
	private XmlConverter<Map<String,Object>> colorCodeListConverter;
	
	@RequestMapping("/colorCode/c/list")
	public @ResponseBody
	String getColorCodeList(HttpEntity<String> entity)
	{
		String response ;
		Map<String,Object> map = colorCodeListConverter.fromXml(entity.getBody());
		Integer tenantId = (Integer) map.get("tenantId");
		try {
			List<ColorCode> codes = colorCodeService.getAllColorCodes(tenantId);
			map.put("codes", codes);
			
		} catch (EssException e) {
			logger.error("failed to get colorCodes", e);
			
		}
		response = colorCodeListConverter.toXml(map);
		return response;
	}
}
