package com.jw.ess.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IColorCodeDao;
import com.jw.ess.entity.ColorCode;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 色号表数据库操作接口实现类
 * 
 * @author chenxiangbin
 * 
 */
@Repository("colorCodeDao")
public class ColorCodeDao implements IColorCodeDao {
	private static final Log logger = LogFactory.getLog(ColorCodeDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_COLOR_CODE = MapperConstant.MAPPER_NAMESPACE_COLOR_CODE + ".insertColorCode";

	private static final String FIND_ALL_COLOR_CODES = MapperConstant.MAPPER_NAMESPACE_COLOR_CODE
			+ ".findAllColorCodes";

	private static final String FIND_COLOR_CODE_NAME = MapperConstant.MAPPER_NAMESPACE_COLOR_CODE + ".findColorCode";

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void insertColorCode(ColorCode colorCode) throws EssException {
		try {
			this.sqlSessionTemplate.insert(INSERT_COLOR_CODE, colorCode);
		} catch (PersistenceException e) {
			logger.error("failed to insertColorCode", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public List<ColorCode> findAllColorCodes(int tenantId) throws EssException {
		try {
			@SuppressWarnings("unchecked")
			List<ColorCode> colorCodes = (List<ColorCode>) sqlSessionTemplate
					.selectList(FIND_ALL_COLOR_CODES, tenantId);
			return colorCodes;
		} catch (PersistenceException e) {
			logger.error("failed to insertColorCode", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findColorCodeName(int tenantId, String colorCodeName) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.COLOR_CODE_NAME, colorCodeName);
		try {
			return (String) sqlSessionTemplate.selectOne(FIND_COLOR_CODE_NAME, param);
		} catch (PersistenceException e) {
			logger.error("failed to insertColorCode", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
