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
import com.jw.ess.converter.constant.FloorCategoryConstant;
import com.jw.ess.converter.constant.FloorConstant;
import com.jw.ess.entity.FloorCategory;

@Component("categoryListConverter")
public class CategoryListConverter extends DefaultXmlConverter<Map<String,Object>> 
{
	public static final Log logger = LogFactory.getLog(CategoryListConverter.class);

	@Override
	public Map<String, Object> fromXml(String xml) 
	{

		try {
			Document doc = DocumentHelper.parseText(xml);
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);
			//tenantId
			int tenantId = Integer.parseInt(conditionEle.elementTextTrim(FloorConstant.TENANT_ID));
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("tenantId", tenantId);
			return map;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String, Object> t) 
	{
		StringBuilder content = new StringBuilder();
		List<FloorCategory> categorys = (List<FloorCategory>) t.get("categorys");
		content.append(FloorConstant.CATEGORYS_START);
		for(FloorCategory c:categorys)
		{
			content.append(FloorCategoryConstant.FLOOR_CATEGORY_START);
			content.append(FloorConstant.CATEGORY_ID_START);
			content.append(c.getId());
			content.append(FloorConstant.CATEGORY_ID_END);
			content.append(FloorConstant.CATEGORY_NAME_START);
			content.append(c.getName());
			content.append(FloorConstant.CATEGORY_NAME_END);
			content.append(FloorCategoryConstant.FLOOR_CATEGORY_END);
		}
		content.append(FloorConstant.CATEGORYS_END);
		return content.toString();
	}
	
	
}
