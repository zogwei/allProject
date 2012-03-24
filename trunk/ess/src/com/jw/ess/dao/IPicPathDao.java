package com.jw.ess.dao;

import java.util.List;

import com.jw.ess.entity.PicPath;
import com.jw.ess.util.ex.EssException;


/**
 * 图片表数据库操作接口
 * 
 * @author huanglonghu
 * 
 */
public interface IPicPathDao {
	
	/**
	 * 插入新的图片信息
	 * 
	 * @param 
	 * @throws EssException
	 */
	void insertPicPath(PicPath picPath) throws EssException;
	
	/**
	 * 查找数据库是否含有指定图片路径
	 * 
	 * @param 
	 * @throws EssException
	 */
	String findPicPathNameBy(PicPath picPath) throws EssException;
	
	/**
	 * 根据tenantId，floorId 查找某个地板的所有图片路径
	 * 
	 * @param  tenantId,floorId
	 * @throws EssException
	 */
	List<PicPath> findPicPathsBy(int floorId) throws EssException;
	
}
