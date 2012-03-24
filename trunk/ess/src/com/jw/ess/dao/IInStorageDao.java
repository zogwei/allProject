package com.jw.ess.dao;

import java.util.List;
import java.util.Map;

import com.jw.ess.entity.InStorage;
import com.jw.ess.util.ex.EssException;

public interface IInStorageDao {
	
	/**
	 * 添加InStorage实例
	 * @param inStorage InStorage实例
	 */
    void insertInStorage(InStorage inStorage) throws EssException;
	
	/**
	 * 查看入库信息
	 * @param id唯一标识
	 */
	InStorage findInStorageBy(int id) throws EssException;
	
	/**
	 * 更新InStorage实体
	 * @param
	 */
	void updateInStorage(InStorage inStorage) throws EssException;
	
	/**
	 * 查出总记录数
	 * @param tenantId
	 */
	int findCountOfInStorage(Map<String, Object> param) throws EssException;

	/**
	 * 用于分页,模糊查询
	 * @param 
	 * @return 返回集合
	 */
	List<InStorage> findInStoragesBy(Map<String, Object> map) throws EssException;
	
	/**
	 * 用于分页,列出所有的信息
	 * @param 根据tenantId
	 * @return 返回集合
	 */
	List<InStorage> findInStorages(Map<String, Object> map) throws EssException;
	
}
