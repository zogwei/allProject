package com.jw.ess.util.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.web.multipart.MultipartFile;

import com.jw.ess.entity.Floor;
import com.jw.ess.util.PathUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;

public class ImageUpLoad {
	
	private static final Log logger = LogFactory.getLog(ImageUpLoad.class);
	
	/** 只支持zip和rar类型压缩包文件上传  */
	public static final String ZIP_TYPE_FILE = "zip";
	public static final String RAR_TYPE_FILE = "rar";
	/** 用于判rar文件里是否含有中文字符 */
	public static final String FILE_NAME_CN_REGEX = "[\\u4e00-\\u9fa5]";
	/** 用于解压缩zip文件时设置的缓冲区大小 */
	public static final int BUFFER_SIZE = 1024;
	/** 记录已经存储到服务器的文件名 */
	public static List<String> fileNames = null;
	
	
	/**
	 * @author mcli
	 * 用于匹配图片类型的常量
	 * @return List 
	 * */
	public static List<String> ImageTypes()
	{
		List<String> imageTypes = new ArrayList<String>();
		imageTypes.add("jpg");
		imageTypes.add("gif");
		imageTypes.add("jpeg");
		imageTypes.add("png");
		imageTypes.add("bmp");
		return imageTypes;
	}
	
	/**
	 * @author mcli
	 * 用于创建图片文件
	 * @param folderName 租户名
	 * @param floorName 地板名
	 * @param pictureName 图片名字
	 * @return File 返回保存路径
	 * 
	 * */
	public static File CreatFolder(String folderName,String floorName,String pictureName, String rootPath) {  
        File file = null;  
        //folderName = folderName.replaceAll("/", "");               //去掉"/"  
        //folderName = folderName.replaceAll(" ", "");               //替换半角空格  
        //folderName = folderName.replaceAll(" ", "");               //替换全角空格  
         
        //pictureName = pictureName.replaceAll("/", "");             //去掉"/"  
        //pictureName = pictureName.replaceAll(" ", "");             //替换半角空格  
        //pictureName = pictureName.replaceAll(" ", "");             //替换全角空格  
         
        File firstFolder = new File(rootPath + folderName);         //一级文件夹  
        if(firstFolder.exists()) {                             //如果一级文件夹存在，则检测二级文件夹  
            File secondFolder = new File(firstFolder,floorName);  
            if(secondFolder.exists()) {                        //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder,pictureName);  
            }else {                                            //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();  
                file = new File(secondFolder,pictureName);        //创建完二级文件夹后，再合建文件  
            }  
        }else {                                                //如果一级不存在，则创建一级文件夹  
            firstFolder.mkdir();  
            File secondFolder = new File(firstFolder,floorName);  
            if(secondFolder.exists()) {                        //如果二级文件夹也存在，则创建文件  
                file = new File(secondFolder,pictureName);  
            }else {                                            //如果二级文件夹不存在，则创建二级文件夹  
                secondFolder.mkdir();  
                file = new File(secondFolder,pictureName);  
            }  
        }  
        return file;  
	}

	/**
	 * 判断表单提交时是否包含要上传的图片文件
	 * @param image
	 * @return
	 */
	public static boolean existFile(MultipartFile image) {
		if(image == null)
			return false;
		if(StringUtils.isBlank(image.getOriginalFilename()))
			return false;
		return true;
	}
	
	/**
	 * 判断文件是否为图片文件
	 * @param pictureName
	 * @return
	 */
	public static boolean isImageFile(String pictureName) {
		String suffix = pictureName.substring(pictureName.lastIndexOf(".") + 1,		// 获取文件后缀名
				pictureName.length());
		suffix = suffix.toLowerCase();
		List<String> imageTypes = ImageTypes();							// 判断扩展名是否属于允许上传的图片类型
		if(imageTypes.contains(suffix))
			return true;
		return false;
	}
	
	/**
	 * 判断文件是否为zip文件
	 * @param pictureName
	 * @return
	 */
	public static boolean isZipFile(String pictureName) {
		String suffix = pictureName.substring(pictureName.lastIndexOf(".") + 1,		// 获取文件后缀名
				pictureName.length());
		suffix = suffix.toLowerCase();
		if(suffix.equalsIgnoreCase(ZIP_TYPE_FILE))
			return true;
		return false;
	}
	
	/**
	 * 判断文件是否为rar文件
	 * @param pictureName
	 * @return
	 */
	public static boolean isRarFile(String pictureName) {
		String suffix = pictureName.substring(pictureName.lastIndexOf(".") + 1,		// 获取文件后缀名
				pictureName.length());
		suffix = suffix.toLowerCase();
		if(suffix.equalsIgnoreCase(RAR_TYPE_FILE))
			return true;
		return false;
	}
	
	/**
	 * 获取zip压缩包中的实际文件名
	 * @param pictureURL 压缩包的相对路径
	 * @return
	 */
	public static String getZipPictureName(String pictureURL) {
		return pictureURL.substring(pictureURL.lastIndexOf("/") + 1);
	}
	
	/**
	 * 获取rar压缩包中的实际文件名
	 * @param pictureURL 压缩包的相对路径
	 * @return
	 */
	public static String getRarPictureName(String pictureURL) {
		return pictureURL.substring(pictureURL.lastIndexOf(File.separator) + 1);
	}
	
	/**
	 * 为上传的图片名称检测然后返回
	 * @param pictureName
	 * @return
	 */
	public static String getPictureName(String pictureName) throws EssException{
		if (StringUtils.isBlank(pictureName)) {
			String message = "the floor picture or path is not found";
			logger.error(message);
			throw new EssException(message, MessageCode.PIC_PATH_ERROR);
		}
		if(pictureName.lastIndexOf(".") < 0) {
			String message = "floor picture file: " + pictureName
				+ " is not correct file(ex: image or *.zip or *.rar file)";
			logger.error(message);
			throw new EssException(message,
					MessageCode.PIC_PATH_FILE_SUFFIX_ERROR);
		}
		return pictureName;
	}
	
	/**
	 * 解压缩Zip文件,只保存图片文件到服务器
	 * 在把图片名称存放到数据库
	 * 使用apache的ZIP工具
	 * 当压缩包里包含文件夹时
	 * ZIP工具的getName()方法实际获取的是
	 * 普通文件:xxx.jpg
	 * 含有文件夹:xxx/xxx.jpg (类推)
	 * @param zip
	 * @param floorId
	 * @param folderName
	 * @throws EssException
	 */
	@SuppressWarnings("unchecked")
	public static void unZip(File zip, Floor floor, String folderName) throws EssException {
		
		File file = null;
		ZipFile zipFile = null;
		ZipEntry entry = null;
		Enumeration<ZipEntry> enu = null;
		String pictureName = null;
		String rootPath = PathUtil.getWebRootImg();
		InputStream inStream = null;
		FileOutputStream outStream = null;
		byte[] buf = new byte[BUFFER_SIZE];
		
		try {
			
			if(!zip.exists() && (zip.length() <= 0)) {								//判断文件是否不存在或长度为0
				String message = "the floor picture or path is not found";
				logger.error(message);
				throw new EssException(message, MessageCode.PIC_PATH_ERROR);
			}
			
			fileNames = new ArrayList<String>();
			zipFile = new ZipFile(zip);
			enu = (Enumeration<ZipEntry>) zipFile.getEntries();
			
			Label:
			while(enu.hasMoreElements()) {
				
				entry = (ZipEntry) enu.nextElement();
				if(entry.isDirectory()) {											  //如果当前文件是目录
					continue Label;
				} else {
					pictureName = getPictureName(getZipPictureName(entry.getName()));//获取真正的图片名称
					if(!isImageFile(pictureName)) {
						continue Label;
					}
					
					file = CreatFolder(folderName, floor.getName(), pictureName, rootPath);
					if(file.exists()) {												// 如果图片已经存在则抛出异常
						String message = "floor picture: " + pictureName + " is already exists";	
						logger.error(message);														
						throw new EssException(message, MessageCode.PIC_PATH_ALREADY_EXISTS);
					}
					fileNames.add(pictureName);										//记录要保存到服务器的图片名称
					inStream = zipFile.getInputStream(entry);						//只保存图片到服务器
					outStream = new FileOutputStream(file);
					
					int tempNum = -1;
					while((tempNum = inStream.read(buf)) != -1) {
						outStream.write(buf, 0, tempNum);
					}
				}
				outStream.close();
				inStream.close();
			}
		} catch (ZipException e) {
			logger.error("exception was happend when image file uploading",e);
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
		} catch (IOException e) {
			logger.error("exception was happend when image file uploading",e);
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
		}finally {
			try {
				if(zipFile != null)
					zipFile.close();
				if(entry != null)
					entry.clone();
				if(outStream != null)
					outStream.close();
				if(inStream != null)
					inStream.close();
				if(zip.exists())
					zip.getAbsoluteFile().delete();
			} catch (IOException e) {
				logger.error("exception was happend when image file uploading",e);
				throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
			}
		}
		
	}
	
	/**
	 * 解压缩rar文件，在把图片名称存放到数据库
	 * 试用java-unrar0.3.jar包,加上解决文件名中文问题处理
	 * 当压缩包里包含文件夹时
	 * RAR工具的getFileNameW()和getFileNameString()方法实际获取的是
	 * 普通文件:xxx.jpg
	 * 含有文件夹:xxx/xxx.jpg (类推)
	 * @param rar
	 * @param floorId
	 * @param folderName
	 * @throws EssException
	 */
	public static void unRar(File rar, Floor floor, String folderName) throws EssException {
		
		File file = null;
		Archive archive = null;
		FileHeader fileHeader = null;
		String pictureURL = null;
		String pictureName = null;
		String rootPath = PathUtil.getWebRootImg();
		FileOutputStream outStream = null;
		try {
			
			if(!rar.exists() && (rar.length() <= 0)) {
				String message = "the floor picture or path is not found";
				logger.error(message);
				throw new EssException(message, MessageCode.PIC_PATH_ERROR);
			}
			fileNames = new ArrayList<String>();
			archive = new Archive(rar);
			fileHeader = archive.nextFileHeader();
			
			Label:
			while(fileHeader != null) {
				
				if(fileHeader.isDirectory()) {
					fileHeader = archive.nextFileHeader();									//判断当前文件是否目录
					continue Label;
				} else { 
					pictureURL = fileHeader.getFileNameW().trim();							//根据文件名是否有中文判断要调用的获取文件名方法
					if(!existZH(pictureURL))												//如果压缩包里没有英文名文件则当使用getFileNameW()不能获取文件名
						pictureURL = fileHeader.getFileNameString().trim();
					pictureName = getPictureName(getRarPictureName(pictureURL));			//获取真正的图片名称
					
					if(!isImageFile(pictureName)) {
						fileHeader = archive.nextFileHeader();
						continue Label;
					}
					
					file = CreatFolder(folderName, floor.getName(), pictureName, rootPath);
					if(file.exists()) {														// 如果图片已经存在则抛出异常
						String message = "floor picture: " + pictureName + " is already exists";	
						logger.error(message);												
						throw new EssException(message, MessageCode.PIC_PATH_ALREADY_EXISTS);
					}
					fileNames.add(pictureName);												//记录成功保存到服务器的图片名称
					outStream = new FileOutputStream(file);									//保存图片到服务器
					archive.extractFile(fileHeader, outStream);
					
					fileHeader = archive.nextFileHeader();
				}
				outStream.close();
			}
		} catch (RarException e) {
			logger.error("exception was happend when image file uploading",e);
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
		} catch (IOException e) {
			logger.error("exception was happend when image file uploading",e);
			throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
		} finally {
			try {
				if(outStream != null)
					outStream.close();
				if(archive != null)
					archive.close();
				if(rar.exists())
					rar.getAbsoluteFile().delete();
			} catch (IOException e) {
				logger.error("exception was happend when image file uploading",e);
				throw new EssException(e, MessageCode.PIC_PATH_FILE_ERROR);
			}
		}
	}
	
	/**
	 * 用于判断rar文件里是否中文名字文件
	 * @param str
	 * @return
	 */
	public static boolean existZH(String str) {
		String regEx = FILE_NAME_CN_REGEX;
		Pattern pattern = Pattern.compile(regEx); 
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			return true;
		}
		return false;
	}
	
}
