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

import com.jw.ess.dao.ICustomerDao;
import com.jw.ess.entity.Customer;
import com.jw.ess.util.MapperConstant;
import com.jw.ess.util.ParameterMapKeys;
import com.jw.ess.util.ex.EssException;
import com.jw.ess.util.ex.MessageCode;

@Repository("customerDao")
public class CustomerDao implements ICustomerDao {

	private static final Log logger = LogFactory.getLog(CustomerDao.class);

	private SqlSessionTemplate sqlSessionTemplate;

	private static final String INSERT_CUSTOMER = MapperConstant.MAPPER_NAMESPACE_CUSTOMER + ".insertCustomer";

	private static final String FIND_CUSTOMER_BY_ID = MapperConstant.MAPPER_NAMESPACE_CUSTOMER + ".findCustomerById";

	private static final String FIND_CUSTOMERS_BY = MapperConstant.MAPPER_NAMESPACE_CUSTOMER + ".findCustomersBy";

	private static final String UPDATE_CUSTOMER = MapperConstant.MAPPER_NAMESPACE_CUSTOMER + ".updateCustomer";

	private static final String FIND_CUSTOMER_NAME = MapperConstant.MAPPER_NAMESPACE_CUSTOMER + ".findCustomerName";

	private static final String FIND_CUSTOMER_NAME_EXCLUDE_SELF = MapperConstant.MAPPER_NAMESPACE_CUSTOMER
			+ ".findCustomerNameExcludeSelf";

	@Resource(name = "sqlSessionTemplate")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public void insertCustomer(Customer customer) throws EssException {
		try {
			sqlSessionTemplate.insert(INSERT_CUSTOMER, customer);
		} catch (PersistenceException e) {
			logger.error("failed to insertCustomer", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public void updateCustomer(Customer customer) throws EssException {
		try {
			sqlSessionTemplate.update(UPDATE_CUSTOMER, customer);
		} catch (PersistenceException e) {
			logger.error("failed to updateCustomer", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public Customer findCustomerById(int customerId) throws EssException {
		try {
			return (Customer) sqlSessionTemplate.selectOne(FIND_CUSTOMER_BY_ID, customerId);
		} catch (PersistenceException e) {
			logger.error("failed to findCustomerById", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public List<Customer> findCustomersBy(int employeeId) throws EssException {
		try {
			@SuppressWarnings("unchecked")
			List<Customer> customers = (List<Customer>) sqlSessionTemplate.selectList(FIND_CUSTOMERS_BY, employeeId);
			return customers;
		} catch (PersistenceException e) {
			logger.error("failed to findCustomersBy", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findCustomerName(int employeeId, String customerName) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		param.put(ParameterMapKeys.CUSTOMER_NAME, customerName);
		try {
			return (String) sqlSessionTemplate.selectOne(FIND_CUSTOMER_NAME, param);
		} catch (PersistenceException e) {
			logger.error("failed to findCustomerName", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

	@Override
	public String findCustomerName(int employeeId, int customerId, String customerName) throws EssException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(ParameterMapKeys.EMPLOYEE_ID, employeeId);
		param.put(ParameterMapKeys.CUSTOMER_ID, customerId);
		param.put(ParameterMapKeys.CUSTOMER_NAME, customerName);

		try {
			return (String) sqlSessionTemplate.selectOne(FIND_CUSTOMER_NAME_EXCLUDE_SELF, param);
		} catch (PersistenceException e) {
			logger.error("failed to findCustomerNameExcludeSelf", e);
			throw new EssException(e, MessageCode.DATABASE_ERROR);
		}
	}

}
