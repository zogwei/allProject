package com.jw.ess.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jw.ess.dao.ISupplierDao;
import com.jw.ess.entity.Supplier;
import com.jw.ess.service.ISupplierService;
import com.jw.ess.util.DateUtil;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Service("supplierService")
@Transactional(rollbackFor=Exception.class)
public class SupplierService implements ISupplierService{
	
	private static final Log logger = LogFactory.getLog(SupplierService.class);
	
	@Resource(name="supplierDao")
	private ISupplierDao supplierDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void addSupplier(Supplier supplier) throws EssException {
		supplier.setCreatedDate(DateUtil.currentTimeSecs());
		supplier.setIsValid(1);
		//传入tenantId和name 
		if(supplierDao.findSupplier(supplier) !=null){
			String name = supplierDao.findSupplier(supplier).getName();
			logger.error("supplier name " + name + " is already exists");
			throw new EssException("supplier account " + name
					+ " is already exists",
					MessageCode.SUPPLIER_NAME_ALREADY_EXISTS);
			
		}
		supplierDao.insertSupplier(supplier);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=true)
	public List<Supplier> getAllSupplierLike(Supplier supplier) throws EssException {
		return supplierDao.findSupplierLikeBy(supplier);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void modifySupplier(Supplier supplier) throws EssException {
		if(supplierDao.findSupplier(supplier) == null){
			supplierDao.updateSupplier(supplier);
		}else{
			logger.error("supplier name " + supplier.getName()
					+ " is already exists");
			throw new EssException("supplier name " + supplier.getName()
					+ " is already exists",
					MessageCode.SUPPLIER_NAME_ALREADY_EXISTS);
		}
	}

	@Override
	public Supplier getSupplier(int id) throws EssException {
		return supplierDao.findSupplierBy(id);
	}

	@Override
	public Supplier getSupplier(Supplier supplier) throws EssException {
		return supplierDao.findSupplier(supplier);
	}

}
