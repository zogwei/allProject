package com.jw.ess.converter;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.FloorConstant;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IPicPathService;
import com.jw.ess.util.ex.EssException;

@Component("floorListConverter")
public class FloorListConverter extends DefaultXmlConverter<Map<String,Object>> {

	private static final String IMG = "img";
	
	private static final String SEPARATOR = "/";
	
	private static final Log logger = LogFactory
			.getLog(FloorListConverter.class);
	
	@Resource(name="picPathService")
	private IPicPathService picPathService;

	@Override
	public Map<String,Object> fromXml(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			// tenantId
			int tenantId = Integer.parseInt(conditionEle
					.elementTextTrim(FloorConstant.TENANT_ID));
			Floor floor = new Floor();
			Tenant tenant = new Tenant();
			tenant.setId(tenantId);
			floor.setTenant(tenant);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("tenantId",tenantId);
			return map;

		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String,Object> map) {
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(FloorConstant.FLOORS_START);
		List<Floor> floors=(List<Floor>) map.get("resultMap");
		for (Floor floor : floors) {
			content.append(FloorConstant.FLOOR_START);
			content.append(FloorConstant.FLOOR_ID_START);
			content.append(floor.getId());
			content.append(FloorConstant.FLOOR_ID_END);
			
			content.append(FloorConstant.FLOOR_NAME_START);
			content.append(floor.getName());
			content.append(FloorConstant.FLOOR_NAME_END);
			
			content.append(FloorConstant.FLOOR_PICPATH_START);
			content.append(IMG+"/floor");
			content.append(SEPARATOR);
			content.append(floor.getTenant().getName());
			content.append(SEPARATOR);
			content.append(floor.getName());
			content.append(SEPARATOR);
			
			content.append(picPathToXML(floor.getId()));
			
			content.append(FloorConstant.FLOOR_PICPATH_END);
			content.append(FloorConstant.FLOOR_END);
		}
		content.append(FloorConstant.FLOORS_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}
	/**
	 * 把图片地址 以 1.jpg+2.jpg+3.jpg .... 形式 合成一个字符串
	 * */
	protected StringBuilder picPathToXML(int floorId){
		StringBuilder s1 = new StringBuilder();
		try {
			List<PicPath> picList=picPathService.getPicPathsBy(floorId);
			int size=picList.size();
			for(PicPath pic:picList){
				String s=pic.getPicPath();
				if(--size!=0){
					s+="+";
				}
				s1.append(s);
			}
		} catch (EssException e) {
			logger.error(e);
			fail("failed to findPicPathsBy");
		}
		return s1;
	}
	
	

}
