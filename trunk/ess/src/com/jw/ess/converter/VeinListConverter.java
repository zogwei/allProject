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
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;

@Component("veinListConverter")
public class VeinListConverter extends DefaultXmlConverter<Map<String, Object>> {
	private static final Log logger = LogFactory
			.getLog(VeinListConverter.class);

	@Override
	public Map<String, Object> fromXml(String xml) {
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
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tenantId", tenantId);
			return map;

		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String, Object> map) {
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
		content.append(FloorConstant.VEINS_START);
		List<Vein> veins = (List<Vein>) map.get("resultMap");
		for (Vein vein : veins) {
			content.append(FloorConstant.VEIN_START);
			content.append(FloorConstant.VEIN_ID_START);
			content.append(vein.getId());
			content.append(FloorConstant.VEIN_ID_END);

			content.append(FloorConstant.VEIN_NAME_START);
			content.append(vein.getName());
			content.append(FloorConstant.VEIN_NAME_END);

			content.append(FloorConstant.VEIN_END);
		}
		content.append(FloorConstant.VEINS_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}
}
