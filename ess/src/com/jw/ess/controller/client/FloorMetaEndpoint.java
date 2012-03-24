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
import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Vein;
import com.jw.ess.service.IColorCodeService;
import com.jw.ess.service.IFloorCategoryService;
import com.jw.ess.service.ISpecService;
import com.jw.ess.service.IVeinService;
import com.jw.ess.util.ex.EssException;

@Controller
public class FloorMetaEndpoint {

private static final Log logger = LogFactory.getLog(FloorMetaEndpoint.class);
	
	@Resource(name = "veinService")
	private IVeinService veinService;
	
	@Resource(name = "specService")
	private ISpecService specService;
	
	@Resource(name="floorCategoryService")
	private IFloorCategoryService floorCategoryService;
	
	@Resource(name="colorCodeService")
	private IColorCodeService colorCodeService;
	
	@Resource(name = "floorMetaListConverter")
	private XmlConverter<Map<String,Object>> floorMetaListConverter;

	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@RequestMapping("/floorMeta/c/list")
	public @ResponseBody
	String getFloorMetaBy(HttpEntity<String> entity){
		String response;
		try{
			int tenantId =(Integer)floorMetaListConverter.fromXml(entity.getBody()).get("tenantId");
			List<Vein> veins = veinService.getAllVeins(tenantId);
			List<Spec> specs = specService.getfindAllSpecs(tenantId);
			List<FloorCategory> categorys = floorCategoryService.getAllFloorCategorysByTenantId(tenantId);
			List<ColorCode> colorCodes = colorCodeService.getAllColorCodes(tenantId);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("veins", veins);
			map.put("specs", specs);
			map.put("categorys", categorys);
			map.put("colorCodes", colorCodes);
			response = floorMetaListConverter.toXml(map);
			
		}catch(EssException e){
			logger.error("failed to getFloorMetaBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}	
	
}
