package com.jw.ess.service;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.ColorCode;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.FloorCategory;
import com.jw.ess.entity.Spec;
import com.jw.ess.entity.Supplier;
import com.jw.ess.entity.Tenant;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.ex.EssException;

public interface IFloorInfoService {
	
	/**
	 * 初始化租户的信息，session绑定租户
	 * 包括地板类别，规格，色号，纹理，供应商
	 * 保存在util包下FloorInfo的map对象
	 * @param session
	 */
	void init(int tenantId) throws EssException;
	
	/**
	 * 销毁租户的信息，解除session绑定租户
	 * 包括地板类别，规格，色号，纹理，供应商
	 * @param session
	 */
	void destory(int tenantId) throws EssException;
	
	/**
	 * 根据名字获取相应保存的List集合
	 * 可获取集合有:地板类别，规格，色号，纹理，供应商
	 * @param map util包下FloorInfo的map对象
	 * @param name 地板类别:floorCategory，规格:spec，色号:colorCode，纹理:vein，供应商:supplier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List getListFromInfo(int tenantId, String name) throws EssException;
	
	/**
	 * 转换相应的List集合为Map
	 * 用于地板信息从excel导入，数据中地板类别，规格，色号，纹理，供应商的名字的查询id
	 * @param list
	 * @return
	 */
	Map<String, Integer> getFloorCategoryIdsMap(List<FloorCategory> list) throws EssException;
	
	Map<String, Integer> getColorCodeIdsMap(List<ColorCode> list) throws EssException;
	
	Map<String, Integer> getSpecIdsMap(List<Spec> list) throws EssException;
	
	Map<String, Integer> getVeinIdsMap(List<Vein> list) throws EssException;
	
	Map<String, Integer> getSupplierIdsMap(List<Supplier> list) throws EssException;
	
	/**
	 * 当数据的地板类别，规格，色号，纹理，供应商信息改变
	 * 更新util包下FloorInfo的map对象
	 * @param tenantId
	 * @param list
	 */
	void setFloorCategorysInfo(int tenantId, List<FloorCategory> list) throws EssException;
	
	void setColorCodesInfo(int tenantId, List<ColorCode> list) throws EssException;
	
	void setSpecsInfo(int tenantId, List<Spec> list) throws EssException;
	
	void setVeinsInfo(int tenantId, List<Vein> list) throws EssException;
	
	void setSuppliersInfo(int tenantId, List<Supplier> list) throws EssException;
	
	/**
	 * 批量插入地板信息处理
	 * @param floors
	 * @throws EssException
	 */
	void addFloorsInfo(Tenant tenant, List<Floor> floors) throws EssException;
	
	/**
	 * 在批量插入地板信息的时候如果遇到新的添加的对象调用的方法
	 * 包括地板类别，规格，色号，纹理，供应商
	 * @param tenantId
	 * @param floorCategoryName
	 * @throws EssException
	 */
	void addFloorCategoryInfo(int tenantId, String floorCategoryName) throws EssException;
	
	void addColorCodeInfo(int tenantId, String colorCodeName) throws EssException;
	
	void addSpecInfo(int tenantId, String specName) throws EssException;
	
	void addVeinInfo(int tenantId, String veinName) throws EssException;
	
	void addSupplierInfo(int tenantId, String supplierName) throws EssException;

}
