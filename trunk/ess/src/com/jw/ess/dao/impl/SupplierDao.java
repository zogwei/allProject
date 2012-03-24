package com.jw.ess.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.jw.ess.dao.ISupplierDao;
import com.jw.ess.entity.Supplier;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("supplierDao")
public class SupplierDao implements ISupplierDao{
	
	private static final Log logger = LogFactory.getLog(SupplierDao.class);

	private SqlSessionTemplate sqlSessionTemplate;
	
	private static final String INSERT_SUPPLIER = MapperConstant.MAPPER_NAMESPACE_SUPPLIER
	+ ".insertSupplier";
	
	private static final String FIND_SUPPLIER = MapperConstant.MAPPER_NAMESPACE_SUPPLIER
	+ ".findSupplier";
	
	private static final String UPDATE_SUPPLIER = MapperConstant.MAPPER_NAMESPACE_SUPPLIER
	+ ".updateSupplier";
	
	private static final String FIND_SUPPLIER_LIKE_BY = MapperConstant.MAPPER_NAMESPACE_SUPPLIER
	+ ".findSupplierLikeBy";
	
	private static final String FIND_SUPPLIER_BY= MapperConstant.MAPPER_NAMESPACE_SUPPLIER
	+ ".findSupplierBy";
	
	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public void insertSupplier(Supplier supplier) throws EssException {
		try 
		{
			sqlSessionTemplate.insert(INSERT_SUPPLIER, supplier);
		} 
		catch (PersistenceException e) 
		{
			logger.error("failed to insertSupplier", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}
	
	@Override
	public Supplier findSupplier(Supplier supplier) throws EssException {
		try 
		{
			return (Supplier) sqlSessionTemplate.selectOne(FIND_SUPPLIER,supplier);
		} 
		catch (PersistenceException e) 
		{
			logger.error("failed to findSupplier", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateSupplier(Supplier supplier) throws EssException {
		try 
		{
			sqlSessionTemplate.update(UPDATE_SUPPLIER, supplier);
		} 
		catch (PersistenceException e) {
			logger.error("failed to updateSupplier", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> findSupplierLikeBy(Supplier supplier)
			throws EssException {
		try 
		{
			return (List<Supplier>) sqlSessionTemplate.selectList(FIND_SUPPLIER_LIKE_BY, supplier);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findSupplierTotal", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Supplier findSupplierBy(int id) throws EssException {
		try 
		{
			return (Supplier) sqlSessionTemplate.selectOne(FIND_SUPPLIER_BY, id);
		} 
		catch (PersistenceException e) {
			logger.error("failed to findSupplierTotal", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
