package com.jw.ess.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

import com.jw.ess.converter.constant.Commons;
import com.jw.ess.converter.constant.StorageInfoConstant;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.Storage;
import com.jw.ess.entity.StorageInfo;

@Component("storageOneConverter")
public class StorageOneConverter extends DefaultXmlConverter<StorageInfo> {

	private static final Log logger = LogFactory.getLog(StorageOneConverter.class);

	@Override
	public StorageInfo fromXml(String xml) {

		try {
			Document doc = DocumentHelper.parseText(xml);
			// root
			Element requestEle = doc.getRootElement();
			// condition
			Element conditionEle = requestEle.element(Commons.CONDITION);

			int floorId = Integer.parseInt(conditionEle.elementTextTrim(StorageInfoConstant.FLOOR_ID));

			int tenantId = Integer.parseInt(conditionEle.elementTextTrim(StorageInfoConstant.TENANT_ID));

			StorageInfo storageInfo = new StorageInfo();
			Storage storage = new Storage();
			storage.setTenantId(tenantId);
			Floor floor = new Floor();
			floor.setId(floorId);
			storage.setFloor(floor);

			storageInfo.setStorage(storage);

			return storageInfo;
		} catch (DocumentException e) {
			logger.error("failed to parse, xml is " + xml);
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected String toContent(StorageInfo storageInfo) {

		if (storageInfo == null) {
			return super.toContent(storageInfo);
		}
		StringBuilder content = new StringBuilder();
		content.append(Commons.RESULT_START);
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
		content.append(Commons.RESULT_END);
		return content.toString();

	}
}
