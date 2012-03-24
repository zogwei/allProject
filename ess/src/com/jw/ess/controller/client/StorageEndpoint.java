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
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.service.IStorageService;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;


@Controller
public class StorageEndpoint {
	private static final Log logger = LogFactory.getLog(StorageEndpoint.class);

	@Resource(name = "storageService")
	private IStorageService storageService;
	
	@Resource(name = "exceptionConverter")
	private XmlConverter<EssException> exceptionConverter;
	
	@Resource(name = "storageListConverter")
	private XmlConverter<Map<String,Object>> storageListConverter;
	
	@Resource(name = "storageOneConverter")
	private XmlConverter<StorageInfo> storageOneConverter;
	
	@RequestMapping("/storage/c/list")
	public @ResponseBody
	String getStoragesBy(HttpEntity<String> entity){
		String response;
		try {
			Map<String,Object> map = storageListConverter.fromXml(entity.getBody());
			PageSupport<StorageInfo> storages =  storageService.getStorages((Floor)map.get("floor"), 
					(Page)map.get("page"));
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("storages", storages);
			response = storageListConverter.toXml(resultMap);
		} catch (EssException e) {
			logger.error("failed to getStoragesBy", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
	
	@RequestMapping("/storage/c/one")
	public @ResponseBody
	String getStorage(HttpEntity<String> entity){
		String response;
		try {
			StorageInfo s = storageOneConverter.fromXml(entity.getBody());
			s=storageService.getStorageDetail(s.getStorage().getTenantId(), 
					s.getStorage().getFloor().getId());
			response = storageOneConverter.toXml(s);
		} catch (EssException e) {
			logger.error("failed to getStorage", e);
			response = exceptionConverter.toXml(e);
		}
		return response;
	}
}
