package com.jw.ess.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.util.ex.EssException;

/**
 * 地板图片表服务接口
 * @author xbchend
 *
 */
public interface IPicPathService {
	
	/**
	 * 根据tenantId，floorId 查找某个地板的所有图片路径
	 * 
	 * @param  tenantId,floorId
	 * @throws EssException
	 */
	List<PicPath> getPicPathsBy(int floorId) throws EssException;
	
	/**
	 * 插入新的图片信息到数据库
	 * 
	 * @param 
	 * @throws EssException
	 */
	void addPicPath(PicPath picPath) throws EssException;
	
	/**
	 * 查找数据库是否含有指定图片名称
	 * 
	 * @param 
	 * @throws EssException
	 */
	String getPicPathNameBy(PicPath picPath) throws EssException;
	
	/**
	 * 上传图片到服务器
	 * @param tenantName
	 * @param floor
	 * @param imgFile
	 * @return Map<地板id, 地板图片名称集合>
	 * @throws EssException
	 */
	Map<Integer,List<String>> uploadFloorImages(String tenantName,Floor floor,MultipartFile imgFile) throws EssException;
	
	/**
	 * 当上传图片发生异常的回滚处理
	 * @param tenantName
	 * @param floorName
	 * @param fileNames
	 * @throws EssException
	 */
	void rollback(String tenantName, String floorName, List<String> fileNames) throws EssException;
	
}
