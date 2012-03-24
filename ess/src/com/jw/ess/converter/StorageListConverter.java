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
import com.jw.ess.converter.constant.OrderConstant;
import com.jw.ess.converter.constant.StorageConstant;
import com.jw.ess.converter.constant.StorageInfoConstant;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.StorageInfo;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

@Component("storageListConverter")
public class StorageListConverter extends
		DefaultXmlConverter<Map<String, Object>> {
	private static final Log logger = LogFactory
			.getLog(StorageListConverter.class);

	@Override
	public Map<String, Object> fromXml(String xml) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			int tenantId = Integer.parseInt(conditionEle
					.elementTextTrim(StorageConstant.TENANT_ID));
			
			int specId = Integer.parseInt(conditionEle
					.elementTextTrim(StorageConstant.SPEC_ID));
			
			int veinId = Integer.parseInt(conditionEle
					.elementTextTrim(StorageConstant.VEIN_ID));
			
			int categoryId = Integer.parseInt(conditionEle
					.elementTextTrim(StorageConstant.CATEGORY_ID));
			
			int colorCodeId = Integer.parseInt(conditionEle
					.elementTextTrim(StorageConstant.COLOR_CODE_ID));
			
			FloorCategory category = new FloorCategory();
			category.setId(categoryId);
			
			Tenant tenant = new Tenant();
			tenant.setId(tenantId);
			
			ColorCode colorCode = new ColorCode();
			colorCode.setId(colorCodeId);
			
			Spec spec = new Spec();
			spec.setId(specId);
			
			Vein vein = new Vein();
			vein.setId(veinId);
			
			Floor floor = new Floor();
			floor.setTenant(tenant);
			floor.setCategory(category);
			floor.setColorCode(colorCode);
			floor.setVein(vein);
			floor.setSpec(spec);
			
			paramMap.put("floor", floor);
			
			Element pageEle = requestEle.element(Commons.PAGE);
			Page page = new Page();
			page.setCurrentPage(Integer.parseInt(pageEle
					.elementTextTrim(OrderConstant.ORDER_CURRENT_PAGE)));
			
			page.setPageSize(Integer.parseInt(pageEle
					.elementTextTrim(OrderConstant.ORDER_PAGE_SIZE)));
			
			paramMap.put("page", page);
			return paramMap;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected String toContent(Map<String, Object> t) {
		StringBuilder content = new StringBuilder();
		PageSupport<StorageInfo> storages = (PageSupport<StorageInfo>) t.get("storages");
		content.append(StorageInfoConstant.STORAGE_INFOS_START);
		List<StorageInfo> storagesInfoList = storages.getResult();
		if(storagesInfoList!=null)
		{
			for(StorageInfo storageInfo : storagesInfoList){
				content.append(StorageInfoConstant.STORAGE_INFO_START);
				
				content.append(StorageInfoConstant.FLOOR_ID_START);
				content.append(storageInfo.getStorage().getFloor().getId());
				content.append(StorageInfoConstant.FLOOR_ID_END);
				
				content.append(StorageInfoConstant.TENANT_ID_START);
				content.append(storageInfo.getStorage().getTenantId());
				content.append(StorageInfoConstant.TENANT_ID_END);
				
				content.append(StorageInfoConstant.FLOOR_NUMBER_START);
				content.append(storageInfo.getStorage().getFloor().getNumber());
				content.append(StorageInfoConstant.FLOOR_NUMBER_END);
				
				content.append(StorageInfoConstant.FLOOR_NAME_START);
				content.append(storageInfo.getStorage().getFloor().getName());
				content.append(StorageInfoConstant.FLOOR_NAME_END);
				
				content.append(StorageInfoConstant.SPEC_NAME_START);
				content.append(storageInfo.getStorage().getSpec().getName());
				content.append(StorageInfoConstant.SPEC_NAME_END);
				
				content.append(StorageInfoConstant.VEIN_NAME_START);		
				content.append(storageInfo.getStorage().getVein().getName());
				content.append(StorageInfoConstant.VEIN_NAME_END);
				
				content.append(StorageInfoConstant.SELLPRICE_START);		
				content.append(storageInfo.getStorage().getFloor().getSellPrice());
				content.append(StorageInfoConstant.SELLPRICE_END);
				
				content.append(StorageInfoConstant.COUNT_IN_STORAGE_START);
				content.append(storageInfo.getCountInStorage());
				content.append(StorageInfoConstant.COUNT_IN_STORAGE_END);
				
				content.append(StorageInfoConstant.COUNT_ORDER_START);
				content.append(storageInfo.getCountOrder());
				content.append(StorageInfoConstant.COUNT_ORDER_END);
				
				content.append(StorageInfoConstant.COUNT_ORDER_CANCEL_START);
				content.append(storageInfo.getCountOrderCancel());
				content.append(StorageInfoConstant.COUNT_ORDER_CANCEL_END);
				
				content.append(StorageInfoConstant.AREA_START);
				content.append(storageInfo.getStorage().getArea());
				content.append(StorageInfoConstant.AREA_END);
				
				content.append(StorageInfoConstant.COUNT_START);
				content.append(storageInfo.getStorage().getCount());
		        content.append(StorageInfoConstant.COUNT_END);
		        content.append(StorageInfoConstant.STORAGE_INFO_END);
			}
		}
		 
		content.append(StorageInfoConstant.STORAGE_INFOS_END);
		content.append(Commons.PAGE_START);
		content.append(Commons.CURRENT_PAGE_START);
		content.append(storages.getCurrentPage());
		content.append(Commons.CURRENT_PAGE_END);
		content.append(Commons.PAGE_SIZE_START);
		content.append(storages.getPageSize());
		content.append(Commons.PAGE_SIZE_END);
		content.append(Commons.COUNT_START);
		content.append(storages.getCount());
		content.append(Commons.COUNT_END);
		content.append(Commons.PAGE_END);
		return content.toString();
		}
}
