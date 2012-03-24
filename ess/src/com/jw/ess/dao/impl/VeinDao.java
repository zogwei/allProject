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

import com.jw.ess.dao.IVeinDao;
import com.jw.ess.entity.Vein;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

/**
 * 纹理表数据库操作接口实现类
 * 
 * @author j&w
 * 
 */
@Repository("veinDao")
public class VeinDao implements IVeinDao {
	private static final Log logger = LogFactory.getLog(VeinDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_VEIN = MapperConstant.MAPPER_NAMESPACE_VEIN + ".insertVein";

	private static final String FIND_ALL_VEINS = MapperConstant.MAPPER_NAMESPACE_VEIN + ".findAllVeins";

	private static final String FIND_VEIN_NAME = MapperConstant.MAPPER_NAMESPACE_VEIN + ".findVienName";

	@Override
	public List<Vein> findAllVeins(int tenantId) throws EssException {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<Vein> veins = (List<Vein>) sqlSessionTemplate.selectList(FIND_ALL_VEINS, tenantId);
			return veins;
		} catch (PersistenceException e) {
			logger.error("failed to findVeinName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findVeinName(int tenantId, String veinName) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.TENANT_ID, tenantId);
		param.put(ParameterMapKeys.VEIN_NAME, veinName);
		try {
			return (String) sqlSessionTemplate.selectOne(FIND_VEIN_NAME, param);
		} catch (PersistenceException e) {
			logger.error("failed to findVeinName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertVein(Vein vein) throws EssException {
		try {
			this.sqlSessionTemplate.insert(INSERT_VEIN, vein);
		} catch (PersistenceException e) {
			logger.error("failed to insertVein", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
