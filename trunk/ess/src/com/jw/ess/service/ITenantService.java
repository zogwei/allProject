package com.jw.ess.service;

import java.util.List;

import com.jw.ess.entity.Tenant;
import com.jw.ess.util.ex.EssException;

/**
 * 租户服务接口
 * @author {j&w}
 */
public interface ITenantService {
	/**
	 * <p>添加租户</p>
	 * @param tenant 租户对象
	 * @throws EssException
	 */
	void addTenant(Tenant tenant) throws EssException;

	/**
	 * <p>获取所有租户</p>
	 * @return 租户列表
	 * @throws EssException
	 */
	List<Tenant> getAllTenants() throws EssException;
	
	/**
	 * <p>修改租户</p>
	 * @return 租户
	 * @throws EssException
	 */
	void modifyTenant(Tenant tenant) throws EssException;

	/**
	 * <p>根据用户名获取所有租户</p>
	 * @return 租户列表
	 * @throws EssException
	 */
	List<Tenant> getTenantsByName(String tenantName) throws EssException;
	/**
	 * <p>根据用用户Id获取所有租户</p>
	 * @return 租户列表
	 * @throws EssException
	 */
	Tenant getTenantsById(int id) throws EssException;
	/**
	 * <p>根据用用户属性获取所有租户</p>
	 * @return 租户列表
	 * @throws EssException
	 */
	String getTenantName(Tenant tenant) throws EssException;
	/**
	 * <p>根据用用户获取租户的信息</p>
	 * @return 租户列表
	 * @throws EssException
	 */
	boolean  checkTenant(Tenant tenant) throws EssException;
}
