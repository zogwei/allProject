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
import com.jw.ess.converter.constant.FloorConstant;
import com.jw.ess.entity.ColorCode;

@Component("colorCodeListConverter")
public class ColorCodeListConverter extends DefaultXmlConverter<Map<String,Object>>
{
	private static final Log logger = LogFactory.getLog(ColorCodeListConverter.class);
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
		List<ColorCode> codes = (List<ColorCode>) t.get("codes");
		content.append(FloorConstant.COLOR_CODES_START);
		for(ColorCode c: codes){
			content.append(FloorConstant.COLOR_CODE_START);
			content.append(FloorConstant.COLOR_CODE_ID_START);
			content.append(c.getId());
			content.append(FloorConstant.COLOR_CODE_ID_EN);
			content.append(FloorConstant.COLOR_CODE_NAME_START);
			content.append(c.getName());
			content.append(FloorConstant.COLOR_CODE_NAME_END);
			content.append(FloorConstant.COLOR_CODE_END);
		}
		content.append(FloorConstant.COLOR_CODES_END);
		return content.toString();
	}

	
}
