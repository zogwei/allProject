package com.jw.ess.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.IMonthlySalesStatsDao;
import com.jw.ess.entity.SalesStats;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;
@Repository("monthlySalesStatsDao")
public class MouthlySalesStatsDao implements IMonthlySalesStatsDao {

	private static final Log logger = LogFactory.getLog(MouthlySalesStatsDao.class);

	private static final String FIND_MONTHLY_STATS_BY = MapperConstant.MAPPER_NAMESPACE_MONTHLY_SALES_STATS
	+ ".findStats";
	
	private static final String INSERT_MONTHLY_STATS = MapperConstant.MAPPER_NAMESPACE_MONTHLY_SALES_STATS
	+ ".insertStats";

	private static final String UPDATE_MONTHLY_STATS =MapperConstant.MAPPER_NAMESPACE_MONTHLY_SALES_STATS
	+ ".updateStats";
	
	private static final String FIND_MONTHLY_SALES =MapperConstant.MAPPER_NAMESPACE_MONTHLY_SALES_STATS
	+ ".findmonthlySales";

	private SqlSessionTemplate sqlSessionTemplate;
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SalesStats> findMonthlyStatsBy(Map<String, Object> param)
			throws EssException {
		try {
			return (List<SalesStats>) sqlSessionTemplate.selectList(FIND_MONTHLY_STATS_BY, param);
		} catch (PersistenceException e) {
			logger.error("failed to findMonthlyStatsBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void insertStats(SalesStats monthlySalesStats)
			throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_MONTHLY_STATS, monthlySalesStats);
		} catch (PersistenceException e) {
			logger.error("failed to insertMonthlyStats", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

	}

	@Override
	public void updateStatsBy(SalesStats monthlySalesStats)
			throws EssException {
		try {
			sqlSessionTemplate.update(UPDATE_MONTHLY_STATS, monthlySalesStats);
		} catch (PersistenceException e) {
			logger.error("failed to updateMonthlyStats", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}

	}

	@Override
	public SalesStats findmonthlySalesby(int employeeId, int salesDate)
			throws EssException {
		try {
			SalesStats stats=new SalesStats();
			stats.setEmployeeId(employeeId);
			stats.setSalesDate(salesDate);
			return (SalesStats) sqlSessionTemplate.selectOne(FIND_MONTHLY_SALES, stats);
		} catch (PersistenceException e) {
			logger.error("failed to monthlySalesby", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
