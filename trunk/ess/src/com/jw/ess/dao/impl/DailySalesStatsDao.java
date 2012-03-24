package com.jw.ess.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IDailySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("dailySalesStatsDao")
public class DailySalesStatsDao implements IDailySalesStatsDao {

	private static final Log logger = LogFactory.getLog(DailySalesStatsDao.class);

	private static final String FIND_STATSS = MapperConstant.MAPPER_NAMESPACE_DAILY_SALES_STATS
	+ ".findStatss";
	
	private static final String INSERT_STATS = MapperConstant.MAPPER_NAMESPACE_DAILY_SALES_STATS
	+ ".insertStats";

	private static final String UPDATE_STATS =MapperConstant.MAPPER_NAMESPACE_DAILY_SALES_STATS
	+ ".updateStatss";

	private static final String FIND_SALES = MapperConstant.MAPPER_NAMESPACE_DAILY_SALES_STATS
	+ ".findSales";

	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesStats> findStatssby(Map<String,Object> param) throws EssException {
		try {
			return (List<SalesStats>) sqlSessionTemplate.selectList(FIND_STATSS, param);
		} catch (PersistenceException e) {
			logger.error("failed to findCustomersBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertStats(SalesStats salesStats)
			throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_STATS, salesStats);
		} catch (PersistenceException e) {
			logger.error("failed to insertCustomer", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateStatsBy(SalesStats salesStats)
			throws EssException {
		try {
			sqlSessionTemplate.update(UPDATE_STATS, salesStats);
		} catch (PersistenceException e) {
			logger.error("failed to updateStatsBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public SalesStats findSalesby(int employeeId, int salesDate)
			throws EssException {
		try {
			SalesStats stats=new SalesStats();
			stats.setEmployeeId(employeeId);
			stats.setSalesDate(salesDate);
			return (SalesStats) sqlSessionTemplate.selectOne(FIND_SALES, stats);
		} catch (PersistenceException e) {
			logger.error("failed to findSalesby", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
