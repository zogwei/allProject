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
import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;

@Component("floorMetaListConverter")
public class FloorMetaListConverter extends
		DefaultXmlConverter<Map<String, Object>> {

	private static final Log logger = LogFactory
			.getLog(FloorMetaListConverter.class);

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
		content.append(FloorConstant.FLOORMETA_START);
		
		content.append(FloorConstant.SPECS_START);
		List<Spec> specs = (List<Spec>) map.get("specs");
		for (Spec spec : specs) {
			content.append(FloorConstant.SPEC_START);
			content.append(FloorConstant.SPEC_ID_START);
			content.append(spec.getId());
			content.append(FloorConstant.SPEC_ID_END);

			content.append(FloorConstant.SPEC_NAME_START);
			content.append(spec.getName());
			content.append(FloorConstant.SPEC_NAME_END);

			content.append(FloorConstant.SPEC_END);
		}
		content.append(FloorConstant.SPECS_END);

		content.append(FloorConstant.VEINS_START);
		List<Vein> veins = (List<Vein>) map.get("veins");
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

		content.append(FloorConstant.CATEGORYS_START);
		List<FloorCategory> floorCategorys = (List<FloorCategory>) map
				.get("categorys");
		for (FloorCategory floorCategory : floorCategorys) {
			content.append(FloorCategoryConstant.FLOOR_CATEGORY_START);
			content.append(FloorConstant.CATEGORY_ID_START);
			content.append(floorCategory.getId());
			content.append(FloorConstant.CATEGORY_ID_END);
			content.append(FloorConstant.CATEGORY_NAME_START);
			content.append(floorCategory.getName());
			content.append(FloorConstant.CATEGORY_NAME_END);
			content.append(FloorCategoryConstant.FLOOR_CATEGORY_END);
		}
		content.append(FloorConstant.CATEGORYS_END);

		content.append(FloorConstant.COLOR_CODES_START);
		List<ColorCode> colorCodes = (List<ColorCode>) map.get("colorCodes");
		for (ColorCode colorCode : colorCodes) {
			content.append(FloorConstant.COLOR_CODE_START);
			content.append(FloorConstant.COLOR_CODE_ID_START);
			content.append(colorCode.getId());
			content.append(FloorConstant.COLOR_CODE_ID_EN);
			content.append(FloorConstant.COLOR_CODE_NAME_START);
			content.append(colorCode.getName());
			content.append(FloorConstant.COLOR_CODE_NAME_END);
			content.append(FloorConstant.COLOR_CODE_END);
		}
		content.append(FloorConstant.COLOR_CODES_END);
		content.append(FloorConstant.FLOORMETA_END);
		content.append(Commons.RESULT_END);
		return content.toString();
	}

}
