package com.jw.ess.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jw.ess.dao.IFloorDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.entity.Tenant;
import com.jw.ess.service.IFloorInfoService;
import com.jw.ess.service.IFloorService;
import com.jw.ess.service.IPicPathService;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
import com.jw.ess.util.importer.Importer;
import com.jw.ess.util.importer.ImporterUtil;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

@Service("floorService")
public class FloorService implements IFloorService {

	private static final Log logger = LogFactory.getLog(FloorService.class);

	@Resource(name = "floorDao")
	private IFloorDao floorDao;
	@Resource(name = "picPathService")
	private IPicPathService picPathService;
	@Resource(name="floorExcelImporter")
	private Importer<Floor> floorExcelImporter;
	@Resource(name="floorInfoService")
	private IFloorInfoService floorInfoService;
	
	private void checkFloorName(Floor floor) throws EssException {
		if (floor != null && StringUtils.isNotBlank(floor.getName())) {
			logger.error("floor name " + floor.getName() + " is already exists");
			throw new EssException("floor name " + floor.getName() + " is already exists",
					MessageCode.FLOOR_NAME_ALREADY_EXISTS);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addFloor(Floor floor, MultipartFile image) throws EssException {
		// check floorName exists or not
		checkFloorName(getFloorByName(floor.getTenant().getId(), floor.getName()));
		int floorId = floorDao.insertFloor(floor);
		// 插入地板信息时包含要上传的文件
		uploadFloorImages(floor.getTenant().getName(), floorId, image);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addFloors(Tenant tenant, MultipartFile excel)
			throws EssException {
		String excelName = excel.getOriginalFilename();
		if(ImporterUtil.isExcelFile(excelName))
			try {
				List<Floor> list = floorExcelImporter.importFrom(excel.getInputStream() ,excelName);
				if(list != null) {
					floorInfoService.init(tenant.getId());
					floorInfoService.addFloorsInfo(tenant, list);
					floorInfoService.destory(tenant.getId());
				} else {
					String message = "Importing floor excel file " + excelName + " happened an exeception";
					logger.error(message);
					throw new EssException(message, MessageCode.FLOOR_EXCEL_FILE_ERROR);
				}
				
			} catch (IOException e) {
				logger.error("Importing floor excel file " + excelName + " happened an exeception",e);
				throw new EssException(e, MessageCode.FLOOR_EXCEL_FILE_ERROR);
			}
		else {
			String message = "floor picture file: " + excelName
			+ " is not correct file(ex: *.xls or *.xlsx file)";
				logger.error(message);
			throw new EssException(message, MessageCode.FLOOR_EXCEL_FILE_ERROR);
		}
			
	}


	@Override
	public Floor getFloorById(int Id,int tenantId) throws EssException {
		return floorDao.findById(Id,tenantId);
	}

	public Floor getFloorById(int Id) throws EssException {
		return floorDao.findById(Id);
	}
	
	@Override
	public Floor getFloorByName(int tenantId, String floorName)
			throws EssException {
		return floorDao.findFloorByName(tenantId, floorName);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void modifyFloor(Floor floor, MultipartFile image) throws EssException {
		// check floor name exists or not
		checkFloorName(floorDao.findFloorName(floor.getTenant().getId(), floor.getId(), floor.getName()));
		floorDao.updateFloor(floor);
		// 插入地板信息时包含要上传的文件
		uploadFloorImages(floor.getTenant().getName(), floor.getId(), image);
	}

	@Override
	public List<Floor> getFloorsByTenantId(int tenantId) throws EssException {
		return (List<Floor>) floorDao.findFloorsByTenantId(tenantId);
	}


	@Override
	public PageSupport<Floor> getFloorsBy(Floor floor, Page page,int tenantId)
			throws EssException {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, floor.getTenant().getId());
		if(floor.getName() != null)
			param.put(ParameterMapKeys.FLOOR_NAME, floor.getName());
		if(floor.getSpec() != null)
			param.put(ParameterMapKeys.SPEC_ID, floor.getSpec().getId());
		if(floor.getVein() != null)
			param.put(ParameterMapKeys.VEIN_ID, floor.getVein().getId());
		if(floor.getCategory() != null)
			param.put(ParameterMapKeys.CATEGORY_ID, floor.getCategory().getId());
		if(floor.getColorCode() != null)
			param.put(ParameterMapKeys.COLOR_CODE_ID, floor.getColorCode().getId());

		PageSupport<Floor> ps = new PageSupport<Floor>();
		ps.setCurrentPage(page.getCurrentPage());
		ps.setPageSize(page.getPageSize());

		// get floor count
		int count = floorDao.findCountOfFloor(param);
		ps.setCount(count);
		// check count equals 0 or not
		if (count != 0) {
			param.put(ParameterMapKeys.BEGIN_INDEX, ps.beginIndexOf());
			param.put(ParameterMapKeys.PAGE_SIZE, ps.pageSize);
			List<Floor> floors = floorDao.findFloorsBy(param,tenantId);
			ps.setResult(floors);
		}
		return ps;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public Map<Integer, List<String>> uploadFloorImages(String tenantName, int floorId,
			MultipartFile image) throws EssException {
		return picPathService.uploadFloorImages(tenantName, getFloorById(floorId), image);
	}

	@Override
	public List<PicPath> getFloorImages(int floorId) throws EssException {
		return picPathService.getPicPathsBy(floorId);
	}

}