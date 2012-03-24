package com.jw.ess.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IPicPathDao;
import com.jw.ess.entity.PicPath;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("picPathDao")
public class PicPathDao implements IPicPathDao {
	
	private static final Log logger = LogFactory.getLog(CustomerDao.class);
	
	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_PIC_PATH = MapperConstant.MAPPER_NAMESPACE_PIC_PATH
			+ ".insertPicPath";
	
	private static final String FIND_PIC_PATH_NAME = MapperConstant.MAPPER_NAMESPACE_PIC_PATH
			+ ".ifindPicPathName";
	
	private static final String FIND_PIC_PATHS = MapperConstant.MAPPER_NAMESPACE_PIC_PATH
			+ ".findPicPaths";
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
	{
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public String findPicPathNameBy(PicPath picPath) throws EssException {
		
		try
		{
			return (String) sqlSessionTemplate.selectOne(FIND_PIC_PATH_NAME, picPath);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to findPicPathNameBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertPicPath(PicPath picPath) throws EssException {
		try
		{
			sqlSessionTemplate.insert(INSERT_PIC_PATH, picPath);
		}
		catch (PersistenceException e)
		{
			logger.error("failed to insertPicPath", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PicPath> findPicPathsBy(int floorId)
			throws EssException {	
		
		return (List<PicPath>)sqlSessionTemplate.selectList(FIND_PIC_PATHS, floorId);
	}

}
