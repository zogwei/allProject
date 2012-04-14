package com.jw.ess.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.entity.Tenant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.page.Page;
import com.jw.ess.util.page.PageSupport;

public interface IFloorService 
{
	/**@author mcli
	 * 功能 添加地板
	 * @param Floor
	 * */
	void addFloor (Floor floor, MultipartFile image) throws  EssException;

	/**@author mcli
	 * 功能 通过Id查找地板
	 * @param id tenantId
	 * */
	Floor getFloorById (int Id,int tenantId) throws EssException;
	
	/**@author mcli
	 * 功能 通过租户id和地板名称查找地板
	 * @param tenantId floorName
	 * */
	Floor getFloorByName(int tenantId, String floorName) throws EssException;
	

	/**@author mcli
	 * 功能 更新地板
	 * @param Floor
	 * */
	void modifyFloor(Floor floor, MultipartFile image)throws EssException;
	
	
	
	/**@author mcli
	 * 功能 根据tenantId 查找 全部地板
  	 * @param tarenaId 
	 * */
	List<Floor> getFloorsByTenantId(int tenantId) throws EssException;
	
	
	/**
	 * 根据条件查询地板列表
	 * 
	 * @param floor
	 *            地板对象
	 * @param page
	 *            分页对象
	 * @return 地板列表
	 * @throws EssException
	 */
	PageSupport<Floor> getFloorsBy(Floor floor, Page page,int tenantId) throws EssException;
	
	/**
	 * 根据地板id获取地板所有图片名称
	 * @param floorId
	 * @return
	 * @throws EssException
	 */
	List<PicPath> getFloorImages(int floorId) throws EssException;
	
	/**
	 * 地板上传图片调用方法
	 * @param tenantName
	 * @param floorId
	 * @param image
	 */
	Map<Integer, List<String>> uploadFloorImages(String tenantName, int floorId, MultipartFile image) throws EssException;

	/**
	 * 从excel批量插入地板信息
	 * @param id
	 * @param excel
	 * @throws EssException
	 */
	void addFloors(Tenant tenant, MultipartFile excel) throws EssException;
	
}
