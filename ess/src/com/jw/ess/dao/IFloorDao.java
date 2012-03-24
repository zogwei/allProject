package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.Floor;
import com.jw.ess.util.ex.EssException;

/**
 * 地板表数据库操作接口
 * 
 * @author huanglonghu
 * 
 */

public interface IFloorDao {

	/**
	 * 插入新的地板
	 * 
	 * @param floor
	 * @throws EssException
	 */
	int insertFloor(Floor floor) throws EssException;

	/**
	 * 更新地板
	 * 
	 * @param floor
	 * @return 地板对象
	 * @throws EssException
	 */
	void updateFloor(Floor floor) throws EssException;
	
	
	/**
	 * 查询地板名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param floorName
	 *            地板名称
	 * @return 地板名称
	 * @throws EssException
	 */
	Floor findFloorByName(int tenantId,String floorName) throws EssException;
	
	
	/**
	 * 查询地板名称
	 * 
	 * @param tenantId
	 *            租户id
	 * @param floorId
	 *            地板id
	 * @param floorName
	 *            地板名称
	 * @return 地板名称
	 * @throws EssException
	 */
	Floor findFloorName(int tenantId,int floorId,String floorName) throws EssException;

	
	/**
	 * 根据地板id查找地板信息
	 * 
	 * @param id
	 * @return 地板对象
	 * @throws EssException
	 */	
	Floor findById(int id) throws EssException;
	

	/**
	 * 根据租户id 查找  该租户的所有地板
	 * @param tenantId
	 * @return 地板列表
	 * @throws EssException
	 * 
	 * */
	List<Floor> findFloorsByTenantId(int tenantId) throws EssException;
	

	/**
	 * 查出总记录数
	 * @param tenantId
	 */
	int findCountOfFloor(Map<String, Object> param) throws EssException;
	
	
	/**
	 * 根据条件查询地板列表
	 * 
	 * @param param
	 *            条件
	 * @return 匹配的地板列表
	 * @throws EssException
	 */
	List<Floor> findFloorsBy(Map<String, Object> param) throws EssException;
}
