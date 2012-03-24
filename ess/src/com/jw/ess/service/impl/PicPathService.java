package com.jw.ess.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import com.jw.ess.dao.IPicPathDao;
import com.jw.ess.entity.Floor;
import com.jw.ess.entity.PicPath;
import com.jw.ess.service.IPicPathService;
import com.jw.ess.util.PathUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
import com.jw.ess.util.image.ImageUpLoad;

/**
 * 地板图片表服务接口实现类
 * @author xbchend
 *
 */
@Service("picPathService")
public class PicPathService implements IPicPathService{
	
	private static final Log logger = LogFactory.getLog(PicPathService.class);

	@Resource(name = "picPathDao")
	private IPicPathDao picPathDao;

	@Override
	public List<PicPath> getPicPathsBy(int floorId) throws EssException {
		
		return (List<PicPath>)picPathDao.findPicPathsBy(floorId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public void addPicPath(PicPath picPath) throws EssException {
		picPathDao.insertPicPath(picPath);
	}

	@Override
	public String getPicPathNameBy(PicPath picPath) throws EssException {
		return picPathDao.findPicPathNameBy(picPath);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	public Map<Integer, List<String>> uploadFloorImages(String tenantName,
			Floor floor, MultipartFile imgFile) throws EssException {
		
		// 如果表单中不存在上传文件路径则没有文件上传
		if(!ImageUpLoad.existFile(imgFile))
			return null;
		
		// 如果表单中存在上传文件路径则开始文件上传
		Map<Integer, List<String>> map = null;
		String pictureName = ImageUpLoad.getPictureName(imgFile.getOriginalFilename());
																					// 获取文件名并判断文名是否有误
		String rootPath = PathUtil.getWebRootImg();									// 获取工程的WebRoot用来存放地板图片的绝对路径
		File file = ImageUpLoad.CreatFolder(tenantName, floor.getName(), pictureName, rootPath);
																					// 将文件名与保存图片绝对路径拼成完整的路径
		
		if(file.exists()) {
			String message = "floor picture: " + pictureName + " is already exists";// 如果上传的文件已经存在则抛出异常
			logger.error(message);
			throw new EssException(message, MessageCode.PIC_PATH_ALREADY_EXISTS);
		}
		
		try {
			if (ImageUpLoad.isImageFile(pictureName)) {								// 上传文件为图片类型时
				
				ImageUpLoad.fileNames = new ArrayList<String>();					// 临时记录存储的单个文件名
				imgFile.transferTo(file);											// 图片名称保存到数据库后才保存上传的文件
				ImageUpLoad.fileNames.add(pictureName);
				savePicture(floor.getId(), pictureName);							// 保存图片名称到数据库
				
				
			} else if (ImageUpLoad.isZipFile(pictureName)) {						// 上传文件为zip压缩包类型时
				
				imgFile.transferTo(file);
				ImageUpLoad.unZip(file, floor, tenantName);							// 解压缩文件并返回已经存入到服务器的文件名集合
				savePictures(floor.getId(), ImageUpLoad.fileNames);
				
			} else if (ImageUpLoad.isRarFile(pictureName)) {						// 上传文件为rar压缩包类型时
				
				imgFile.transferTo(file);
				ImageUpLoad.unRar(file, floor, tenantName);							// 解压缩文件并返回已经存入到服务器的文件名集合
				savePictures(floor.getId(), ImageUpLoad.fileNames);	
			}else {
				String message = "floor picture file: " + pictureName
						+ " is not correct file(ex: image or *.zip or *.rar file)";
				logger.error(message);
				throw new EssException(message,
						MessageCode.PIC_PATH_FILE_SUFFIX_ERROR);
			}
			
			map = new HashMap<Integer, List<String>>();
		    map.put(floor.getId(), ImageUpLoad.fileNames);
			return map;
			
		} catch (IOException e) {
			logger.error("exception was happend when image file uploading",e);
			rollback(tenantName, floor.getName(), ImageUpLoad.fileNames);			//处理由MultipartFile导致的IOException
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
		} catch (EssException e) { 
			logger.error("exception was happend when image file uploading",e);
			rollback(tenantName, floor.getName(), ImageUpLoad.fileNames);			//处理方法体中所引用的方法抛出的EssException
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
	    } finally {
	    	ImageUpLoad.fileNames = null;
	    	if(ImageUpLoad.isRarFile(pictureName) || ImageUpLoad.isZipFile(pictureName))
	    		if(file.exists())
	    			file.getAbsoluteFile().delete();
	    }
	    
	}

	@Override
	public void rollback(String tenantName, String floorName, List<String> fileNames)
			throws EssException {
		if(fileNames != null) {
			File file = null;
			String rootPath = PathUtil.getWebRootImg();
			for(String pictureName : fileNames) {
				file = ImageUpLoad.CreatFolder(tenantName, floorName, pictureName, rootPath);
				if(file.exists()) {
					try {
						file.getAbsoluteFile().delete();
					} catch (SecurityException e) {
						logger.error("As uploading picture file happened an exeception," +
							"but when deleting file" + pictureName + " which had uploaded happened another exception",e);
						throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
					}
					
				}
			}
		}
	}
	
	/**
	 * 保存图片名称到数据库(单个文件)
	 * 
	 * @param floorId
	 * @param pictureName
	 * @param file
	 * @throws EssException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	private void savePicture(int floorId, String pictureName)
			throws EssException {
		PicPath picPath = new PicPath();
		picPath.setFloorId(floorId);
		picPath.setPicPath(pictureName);
		if (StringUtils.isBlank(getPicPathNameBy(picPath))) {							// 判断图片名是否存在
			addPicPath(picPath);
		} else {
			String message = "floor picture: " + pictureName + " is already exists";	// 如果图片已经存在则记录异常
			logger.error(message);														//如果只是部分文件名相同,则不应立刻结束
			throw new EssException(message, MessageCode.PIC_PATH_ALREADY_EXISTS);
		}
	}
	
	/**
	 * 保存图片名称到数据库(多个个文件)
	 * 
	 * @param floorId
	 * @param fileNames
	 * @throws EssException
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = EssException.class)
	private void savePictures(int floorId, List<String> fileNames)
			throws EssException {
		PicPath picPath = null;
		
		for(String pictureName : fileNames) {
			picPath = new PicPath();
			picPath.setFloorId(floorId);
			picPath.setPicPath(pictureName);
			if (StringUtils.isBlank(getPicPathNameBy(picPath))) {							// 判断图片名是否存在
				addPicPath(picPath);
			} else {
				String message = "floor picture: " + pictureName + " is already exists";	// 如果图片已经存在则记录异常
				logger.error(message);														//如果只是部分文件名相同,则不应立刻结束
				throw new EssException(message, MessageCode.PIC_PATH_ALREADY_EXISTS);
			}
		}
	}

}
